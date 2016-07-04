package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface ILevel {
    public ISpawner[] getNpcSpawns();
    public ISpawner[] getMonsterSpawns();

    public Vector2 getCameraInitLocation();

    public TiledMap getMap();
    public void setMap(TiledMap map);

    public IWall[] getWalls();

    public IDoor[] getDoors();

    public int getWidth();
    public int getHeight();

    public int getTileSize();


}
