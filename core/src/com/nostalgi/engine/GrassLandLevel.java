package com.nostalgi.engine;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public class GrassLandLevel extends BaseLevel {

    public GrassLandLevel(TmxMapLoader mapLoader) {
        super(0,0);
        this.setMap(mapLoader.load("grasslands.tmx"));
    }

    @Override
    public void initMap() {

    }

    @Override
    public Vector2 getCameraInitLocation() {
        return new Vector2(0, 58);
    }
}
