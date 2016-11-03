package com.nostalgi.engine.Factories;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.Annotations.NostalgiField;
import com.nostalgi.engine.interfaces.Factories.IActorFactory;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.physics.BoundingVolume;
import com.nostalgi.engine.physics.CollisionCategories;

import java.lang.reflect.Field;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public class NostalgiActorFactory extends BaseLevelObjectFactory implements IActorFactory {

    // world
    private IWorld world;

    private static final String DENSITY = "Density";
    private static final String SENSOR = "IsSensor";
    private static final String STATIC = "IsStatic";
    private static final String TYPE = "Type";
    private static final String FLOOR = "Floor";
    private static final String FRICTION = "Friction";

    private static final String COLLISION_CATEGORY = "CollisionCategory";
    private static final String COLLISION_MASK = "CollisionMask";

    public NostalgiActorFactory(IWorld world) {
        this.world = world;
    }

    @Override
    public IActor fromMapObject(MapObject object, IActor parent, float unitScale) {
        // Get the type, which is a class!
        // This will be used to create an instance
        String type = getObjectProperty(object, TYPE);

        String name = object.getName();



        IActor actor = this.createActor(type, name, object, unitScale);

        actor.setParent(parent);
        //actor.setWorld(world);

        world.createBody(actor, unitScale);

        return actor;
    }

    @Override
    public IActor fromMapObject(MapObject object, IActor parent) {
        return fromMapObject(object, parent, 32f);
    }

    @Override
    public void destroyActor(IActor actor) {
        world.destroyBody(actor.getPhysicsBody());
    }

    protected IActor createActor(String type, String id, MapObject object, float unitScale) {
        try {
            Class c = Class.forName(type);

            IActor actor = (IActor)c.newInstance();


            for(Field field : c.getSuperclass().getDeclaredFields()) {
                field.setAccessible(true);
                NostalgiField annotation = field.getDeclaredAnnotation(NostalgiField.class);
                if(annotation != null) {
                    String fieldName = annotation.fieldName();
                    if(fieldName.isEmpty()) {
                        fieldName = field.getName();
                    }

                    String propertyValue = getObjectProperty(object, fieldName);
                    if(propertyValue != null && !propertyValue.isEmpty()) {
                        if (field.getType() == int.class) {
                            field.set(actor, Integer.parseInt(propertyValue));
                        } else if (field.getType() == float.class) {
                            field.set(actor, Float.parseFloat(propertyValue));
                        } else if (field.getType() == boolean.class) {
                            field.set(actor, Boolean.parseBoolean(propertyValue));
                        } else if (field.getType() == String.class) {
                            field.set(actor, propertyValue);
                        }
                    }
                }
            }

            float[] vertices = new float[0];
            Vector2 position = new Vector2(0,0);

            if(object instanceof RectangleMapObject) {
                Rectangle obj = ((RectangleMapObject) object).getRectangle();

                vertices = rectangleToVertices(0, 0, obj.getWidth(), obj.getHeight());
                position = new Vector2(obj.getX(), obj.getY());
            } else if(object instanceof PolygonMapObject) {
                PolygonMapObject obj = (PolygonMapObject) object;

                vertices = obj.getPolygon().getVertices();
                position = new Vector2(obj.getPolygon().getX(), obj.getPolygon().getY());
            }


            actor.setPosition(position);

            actor.setBoundingVolume(createBoundingVolume(object, vertices, unitScale));
            actor.setName(id);

            return actor;
        } catch(ClassNotFoundException e) {
            return null;
        } catch(IllegalAccessException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
    }


    protected String getObjectProperty(MapObject object, String prop) {
        Object p = object.getProperties().get(prop);

        if(p != null) {
            return (String)p;
        }
        return null;
    }

    protected float[] rectangleToVertices(float x, float y, float width, float height) {
        float[] result = new float[8];
        result[0] = x;
        result[1] = y;

        result[2] = x + width;
        result[3] = y;

        result[4] = x + width;
        result[5] = y + height;
        result[6] = x;
        result[7] = y + height;

        return result;
    }

    protected BoundingVolume createBoundingVolume(MapObject object, float[] vertices, float unitScale) {
        BoundingVolume bv = new BoundingVolume();
        PolygonShape boundShape = new PolygonShape();

        for(int i = 0; i < vertices.length; i++) {
            vertices[i] /= unitScale;
        }

        boundShape.set(vertices);
        bv.setShape(boundShape);

        // set is Sensor
        String isSensor = getObjectProperty(object, SENSOR);
        if(isSensor != null) {
            bv.isSensor(Boolean.parseBoolean(isSensor));
        }

        // set collision category
        String collisionCategory = getObjectProperty(object, COLLISION_CATEGORY);
        short category;
        if (collisionCategory != null) {
            try {
                category  = Short.parseShort(collisionCategory);
            } catch (NumberFormatException e) {
                category = CollisionCategories.categoryFromString(collisionCategory);
            }
            bv.setCollisionCategory(category);
        }

        // set Collision mask
        String collisionMask = getObjectProperty(object, COLLISION_MASK);
        short mask;
        if (collisionMask != null) {
            try {
                mask = Short.parseShort(collisionMask);
            } catch (NumberFormatException e) {
                mask = CollisionCategories.maskFromString(collisionMask);
            }
            bv.setCollisionMask(mask);
        }

        return bv;
    }

    @Override
    public void dispose() {

    }
}
