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
import com.nostalgi.engine.Wall;
import com.nostalgi.engine.interfaces.Factories.IWallFactory;
import com.nostalgi.engine.interfaces.World.IWall;
import com.nostalgi.engine.physics.CollisionCategories;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public class NostalgiWallFactory extends BaseLevelObjectFactory implements IWallFactory {

    private World world;

    public NostalgiWallFactory(World world) {
        this.world = world;
    }

    @Override
    public IWall fromMapObject(MapObject mapObject, Vector2 mapOrigin) {
        return fromMapObject(mapObject, mapOrigin, 32f);
    }

    @Override
    public IWall fromMapObject(MapObject object, Vector2 mapOrigin, float unitScale) {

        String f = getObjectProperty(object, "Floor");
        int[] floors = new int[]{1};
        if(f != null) {
            String[] sFloors = f.split(",");
            floors = new int[sFloors.length];
            for(int i = 0; i < sFloors.length; i++) {
                floors[i] = Integer.parseInt(sFloors[i]);
            }
        }

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

        IWall wall = createWall(floors, position, vertices);

        createPhysicsBody(wall, mapOrigin, unitScale);

        return wall;
    }

    @Override
    public IWall createWall(int[] floors, Vector2 position, float[] vertices) {
        return new Wall(floors, position, vertices);
    }

    @Override
    public void destroyWall(IWall wall) {
        world.destroyBody(wall.getPhysicsBody());
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

    private void createPhysicsBody (IWall wall, Vector2 mapOrigin, float unitScale) {
        // Set def.
        BodyDef shapeDef = new BodyDef();
        // go through our bounding blocks
        float[] vertices = wall.getVertices();

        short floor = CollisionCategories.CATEGORY_NIL;
        if(wall.isOnFloor(1)) {
            floor = (short)(floor | CollisionCategories.CATEGORY_FLOOR_1);
        } if(wall.isOnFloor(2)) {
            floor = (short)(floor | CollisionCategories.CATEGORY_FLOOR_2);
        } if(wall.isOnFloor(3)) {
            floor = (short)(floor | CollisionCategories.CATEGORY_FLOOR_3);
        } if(wall.isOnFloor(4)) {
            floor = (short)(floor | CollisionCategories.CATEGORY_FLOOR_4);
        }

        for(int i = 0; i < vertices.length; i++) {
            vertices[i] /=unitScale;
        }

        if(vertices.length > 2) {
            wall.setPhysicsBody(addBoundingBox(shapeDef,
                    vertices,
                    new Vector2(wall.getX()/unitScale+mapOrigin.x,wall.getY()/unitScale+mapOrigin.y),
                    floor,
                    wall));
        }
    }

    private Body addBoundingBox(BodyDef shapeDef, float[] vertices, Vector2 pos, short floor, IWall wall) {
        PolygonShape boundShape = new PolygonShape();
        boundShape.set(vertices);
        shapeDef.position.set(pos.x, pos.y);
        shapeDef.type = BodyDef.BodyType.StaticBody;

        Body b = world.createBody(shapeDef);

        b.setUserData(wall);

        FixtureDef blockingBounds = new FixtureDef();

        blockingBounds.density = 100f;
        blockingBounds.friction = 0f;
        blockingBounds.shape = boundShape;
        blockingBounds.filter.categoryBits = floor;
        blockingBounds.filter.maskBits = CollisionCategories.CATEGORY_PLAYER;
        b.createFixture(blockingBounds);

        return b;
    }

    @Override
    public void dispose() {

    }
}
