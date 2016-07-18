package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.nostalgi.engine.LevelCameraBounds;
import com.nostalgi.engine.interfaces.Factories.IActorFactory;
import com.nostalgi.engine.interfaces.Factories.IWallFactory;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface ILevel extends Disposable {

    void initMap();

    LevelCameraBounds getCameraBounds();
    void setCameraBounds(LevelCameraBounds bounds);

    ISpawner[] getNpcSpawns();
    ISpawner[] getMonsterSpawns();

    Vector2 getCameraInitLocation();

    TiledMap getMap();
    void setMap(TiledMap map);

    int getWidth();
    int getHeight();

    int getTileSize();

    TiledMapTileLayer getMainLayer();

    ArrayList<IWall> getWalls(IWallFactory factory);
    ArrayList<IActor> getActors(IActorFactory factory);
}
