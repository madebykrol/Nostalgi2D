package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;
import com.nostalgi.engine.LevelCameraBounds;

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

    IWall[] getWalls();

    IDoor[] getDoors();

    int getWidth();
    int getHeight();

    int getTileSize();

    TiledMapTileLayer getMainLayer();

    ArrayList<Polygon> getMapBounds();
}
