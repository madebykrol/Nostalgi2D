package com.nostalgi.engine.Factories;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.World.Wall;
import com.nostalgi.engine.interfaces.Factories.IWallFactory;
import com.nostalgi.engine.interfaces.World.IWall;
import com.nostalgi.engine.interfaces.World.IWorld;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public class NostalgiWallFactory extends BaseLevelObjectFactory implements IWallFactory {

    private IWorld world;

    public NostalgiWallFactory(IWorld world) {
        this.world = world;
    }

    @Override
    public IWall createWall(MapObject mapObject, Vector2 mapOrigin) {
        return createWall(mapObject, mapOrigin, 32f);
    }

    @Override
    public IWall createWall(MapObject object, Vector2 mapOrigin, float unitScale) {

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
            position = new Vector2(obj.getX()/unitScale+mapOrigin.x, obj.getY()/unitScale+mapOrigin.y);
        } else if( object instanceof PolygonMapObject) {
            PolygonMapObject obj = (PolygonMapObject) object;

            vertices = obj.getPolygon().getVertices();
            position = new Vector2(obj.getPolygon().getX()/unitScale+mapOrigin.x, obj.getPolygon().getY()/unitScale+mapOrigin.y);
        }

        IWall wall = new Wall(floors, position, vertices);

        world.createBody(wall, unitScale);

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



    @Override
    public void dispose() {

    }
}
