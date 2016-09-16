package com.nostalgi.engine.interfaces.Factories;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.nostalgi.engine.interfaces.World.IWall;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public interface IWallFactory extends Disposable {
    IWall fromMapObject(MapObject mapObject, Vector2 mapOrigin);
    IWall fromMapObject(MapObject mapObject, Vector2 mapOrigin, float unitScale);
    IWall createWall(int[] floors, Vector2 position, float[] vertices);

    void destroyWall(IWall wall);
}
