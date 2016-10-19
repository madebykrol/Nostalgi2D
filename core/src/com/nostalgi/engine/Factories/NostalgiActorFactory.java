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
import com.nostalgi.engine.interfaces.Factories.IActorFactory;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.physics.BoundingVolume;
import com.nostalgi.engine.physics.CollisionCategories;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public class NostalgiActorFactory extends BaseLevelObjectFactory implements IActorFactory {

    // world
    private World world;

    private static final String DENSITY = "Density";
    private static final String SENSOR = "IsSensor";
    private static final String STATIC = "IsStatic";
    private static final String TYPE = "Type";
    private static final String FLOOR = "Floor";
    private static final String FRICTION = "Friction";

    private static final String COLLISION_CATEGORY = "CollisionCategory";
    private static final String COLLISION_MASK = "CollisionMask";

    public NostalgiActorFactory(World world) {
        this.world = world;
    }

    @Override
    public IActor fromMapObject(MapObject object, IActor parent, float unitScale) {
        String type = getObjectProperty(object, TYPE);
        String floor = getObjectProperty(object, FLOOR);

        String name = object.getName();

        float[] vertices = new float[0];
        Vector2 position = new Vector2(0,0);

        if(object instanceof RectangleMapObject) {
            Rectangle obj = ((RectangleMapObject) object).getRectangle();

            vertices = rectangleToVertices(0, 0, obj.getWidth(), obj.getHeight());
            position = new Vector2(obj.getX(), obj.getY());
        } else if( object instanceof PolygonMapObject) {
            PolygonMapObject obj = (PolygonMapObject) object;

            vertices = obj.getPolygon().getVertices();
            position = new Vector2(obj.getPolygon().getX(), obj.getPolygon().getY());
        }

        IActor actor = this.createActor(type, name, createBoundingVolume(object, vertices, unitScale));

        if(floor != null) {
            actor.setFloorLevel(Integer.parseInt(floor));
        }

        // Set density
        String density = getObjectProperty(object, DENSITY);
        if(density != null) {
            actor.setDensity(Float.parseFloat(density));
        }

        // set friction
        String friction = getObjectProperty(object, FRICTION);
        if(friction != null) {
            actor.setFriction(Float.parseFloat(friction));
        }

        // set is Static
        String isStatic = getObjectProperty(object, STATIC);
        if(isStatic != null) {
            actor.isStatic(Boolean.parseBoolean(isStatic));
        }

        actor.setPosition(position);
        actor.setParent(parent);
        actor.setWorld(world);

        createPhysicsBody(actor, unitScale);

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

    protected IActor createActor(String type, String id, BoundingVolume bv) {
        try {
            Class c = Class.forName(type);

            IActor actor = (IActor)c.newInstance();
            actor.setBoundingVolume(bv);
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

    protected float[] rectangleToVertices(float x, float y, float width,
                                              float height) {
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

    private void createPhysicsBody(IActor actor, float unitScale) {
        BodyDef shapeDef = new BodyDef();
        for(BoundingVolume bv : actor.getBoundingVolumes()) {
            //BoundingVolume bv = actor.getBoundingVolume(0);
            PolygonShape boundShape = bv.getShape();

            shapeDef.position.set((actor.getWorldPosition().x + bv.getRelativePosition().x) / unitScale, (actor.getWorldPosition().y + bv.getRelativePosition().y) / unitScale);
            if (actor.isStatic()) {
                shapeDef.type = BodyDef.BodyType.StaticBody;
            } else {
                shapeDef.type = BodyDef.BodyType.DynamicBody;
            }

            Body b = world.createBody(shapeDef);

            b.setUserData(actor);

            FixtureDef blockingBounds = new FixtureDef();

            blockingBounds.density = actor.getDensity();
            blockingBounds.friction = actor.getFriction();
            blockingBounds.isSensor = bv.isSensor();
            blockingBounds.shape = boundShape;
            blockingBounds.filter.categoryBits = bv.getCollisionCategory();
            blockingBounds.filter.maskBits = bv.getCollisionMask();
            b.createFixture(blockingBounds);
            actor.setPhysicsBody(b);

        }
    }

    @Override
    public void dispose() {

    }
}
