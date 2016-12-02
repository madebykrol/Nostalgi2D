package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.nostalgi.engine.LevelCameraBounds;
import com.nostalgi.engine.Navigation.INavMesh;
import com.nostalgi.engine.interfaces.IGameMode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface ILevel extends Disposable {

    void initMap();

    Vector2 getPosition();
    void setPosition(Vector2 position);

    LevelCameraBounds getCameraBounds();
    void setCameraBounds(LevelCameraBounds bounds);

    Vector2 getCameraInitLocation();

    TiledMap getMap();
    void setMap(TiledMap map);

    int getWidth();
    int getHeight();

    int getTileSize();

    String getGroundLayerName();

    TiledMapTileLayer getMainLayer();

    ArrayList<IWall> getWalls();
    HashMap<String, IActor> getActors();
    void addActor(IActor actor);

    void initWalls();
    void initActors();

    INavMesh getNavMesh();

    void onLoad();
}
