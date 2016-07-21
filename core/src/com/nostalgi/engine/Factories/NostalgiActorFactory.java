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
import com.nostalgi.engine.interfaces.physics.BoundingVolume;
import com.nostalgi.engine.physics.CollisionCategories;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public class NostalgiActorFactory implements IActorFactory {

    private World world;
    private float unitScale;

    private static final String DENSITY = "Density";
    private static final String SENSOR = "IsSensor";
    private static final String STATIC = "IsStatic";
    private static final String TYPE = "Type";
    private static final String FLOOR = "Floor";
    private static final String FRICTION = "Friction";

    private static final String COLLISION_CATEGORY = "CollisionCategory";
    private static final String COLLISION_MASK = "CollisionMask";

    public NostalgiActorFactory(World world, float unitScale) {
        this.world = world;
        this.unitScale = unitScale;
    }

    @Override
    public IActor fromMapObject(MapObject object, IActor parent) {
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

        IActor actor = this.createActor(type, name, createBoundingVolume(object, vertices));

        if(floor != null) {
            actor.setFloorLevel(Integer.parseInt(floor));
        }
        actor.setPosition(position);
        actor.setParent(parent);

        createPhysicsBody(actor);


        return actor;
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

    protected BoundingVolume createBoundingVolume(MapObject object, float[] vertices) {
        BoundingVolume bv = new BoundingVolume();
        PolygonShape boundShape = new PolygonShape();

        for(int i = 0; i < vertices.length; i++) {
            vertices[i] /= unitScale;
        }

        boundShape.set(vertices);
        bv.setShape(boundShape);

        // Set density
        String density = getObjectProperty(object, DENSITY);
        if(density != null) {
            bv.setDensity(Float.parseFloat(density));
        }

        String friction = getObjectProperty(object, FRICTION);
        if(friction != null) {
            bv.setFriction(Float.parseFloat(friction));
        }

        // set is Sensor
        String isSensor = getObjectProperty(object, SENSOR);
        if(isSensor != null) {
            bv.isSensor(Boolean.parseBoolean(isSensor));
        }

        // set is Static
        String isStatic = getObjectProperty(object, STATIC);
        if(isStatic != null) {
            bv.isStatic(Boolean.parseBoolean(isStatic));
        }

        short category = 0;
        short mask = 0;

        String collisionCategory = getObjectProperty(object, COLLISION_CATEGORY);
        if(collisionCategory != null) {
            if(collisionCategory.equals("Trigger")) {
                category = CollisionCategories.CATEGORY_TRIGGER;
            }
        }

        String collisionMask = getObjectProperty(object, COLLISION_MASK);
        if(collisionMask != null) {

        }

        bv.setCollisionCategory(CollisionCategories.CATEGORY_TRIGGER);
        bv.setCollisionMask(CollisionCategories.MASK_TRIGGER);

        return bv;
    }

    private Body createPhysicsBody(IActor actor) {
        BodyDef shapeDef = new BodyDef();
        BoundingVolume bv = actor.getBoundingVolume();
        PolygonShape boundShape = bv.getShape();

        shapeDef.position.set(actor.getPosition().x/unitScale, actor.getPosition().y/unitScale);
        if(bv.isStatic()) {
            shapeDef.type = BodyDef.BodyType.StaticBody;
        } else {
            shapeDef.type = BodyDef.BodyType.DynamicBody;
        }

        Body b = world.createBody(shapeDef);

        b.setUserData(actor);

        FixtureDef blockingBounds = new FixtureDef();

        blockingBounds.density = bv.getDensity();
        blockingBounds.friction = bv.getFriction();
        blockingBounds.isSensor = bv.isSensor();
        blockingBounds.shape = boundShape;
        blockingBounds.filter.categoryBits = bv.getCollisionCategory();
        blockingBounds.filter.maskBits = bv.getCollisionMask();
        b.createFixture(blockingBounds);
        bv.setPhysicsBody(b);
        return b;
    }
}
