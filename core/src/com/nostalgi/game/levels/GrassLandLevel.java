package com.nostalgi.game.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.BaseLevel;
import com.nostalgi.engine.interfaces.Factories.IActorFactory;
import com.nostalgi.engine.interfaces.Factories.IWallFactory;
import com.nostalgi.engine.interfaces.World.IWorld;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public class GrassLandLevel extends BaseLevel {

    public GrassLandLevel(TiledMap map, IWorld world, IActorFactory actorFactory) {
        super(map, world, actorFactory);

    }

    @Override
    public void initMap() {

    }

    @Override
    public Vector2 getCameraInitLocation() {
        return new Vector2(8, 52);
    }
}
