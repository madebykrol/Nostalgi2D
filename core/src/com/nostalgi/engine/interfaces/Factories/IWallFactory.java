package com.nostalgi.engine.interfaces.Factories;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.World.IWall;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public interface IWallFactory {
    IWall fromMapObject(MapObject mapObject);
    IWall createWall(int[] floors, Vector2 position, float[] vertices);
}
