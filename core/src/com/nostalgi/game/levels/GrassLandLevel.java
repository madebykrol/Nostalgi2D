package com.nostalgi.game.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.BaseLevel;
import com.nostalgi.engine.interfaces.Factories.IActorFactory;
import com.nostalgi.engine.interfaces.Factories.IWallFactory;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public class GrassLandLevel extends BaseLevel {

    public GrassLandLevel(TiledMap map, IActorFactory actorFactory, IWallFactory wallFactory) {
        super(map, wallFactory, actorFactory);

    }

    @Override
    public void initMap() {

    }

    @Override
    public Vector2 getCameraInitLocation() {
        return new Vector2(8, 52);
    }
}
