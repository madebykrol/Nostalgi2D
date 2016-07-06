package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.nostalgi.engine.LevelBounds;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface ILevel extends Disposable {

    public LevelBounds getBounds();
    public void setBounds(LevelBounds bounds);

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
