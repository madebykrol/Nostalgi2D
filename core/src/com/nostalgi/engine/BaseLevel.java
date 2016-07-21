package com.nostalgi.engine;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.World.RootActor;
import com.nostalgi.engine.interfaces.Factories.IActorFactory;
import com.nostalgi.engine.interfaces.Factories.IWallFactory;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ILevel;
import com.nostalgi.engine.interfaces.World.ISpawner;
import com.nostalgi.engine.interfaces.World.IWall;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public abstract class BaseLevel implements ILevel {

    protected TiledMap map;
    private TiledMapTileLayer mainLayer;
    private final String wallsLayerName;
    private final String mainLayerName;
    private final String actorsLayerName;

    private Vector2 mapPosition;

    protected ArrayList<IWall> walls = new ArrayList<IWall>();

    private IActor mapRoot = new RootActor();

    public BaseLevel(int originX, int originY) {
        this(originX, originY, "Main", "Walls", "Actors");
    }

    public BaseLevel(int originX, int originY, String mainLayer, String wallsLayer, String actorsLayer) {
        this.mapPosition = new Vector2(originX, originY);
        this.mainLayerName = mainLayer;
        this.wallsLayerName = wallsLayer;
        this.actorsLayerName = actorsLayer;
        this.mapRoot.setPosition(this.mapPosition);
    }

    @Override
    public TiledMap getMap() {
        return this.map;
    }

    @Override
    public void setMap(TiledMap map) {
        this.map = map;
    }

    @Override
    public int getWidth() {
        if (this.getMainLayer() != null)
            return this.getMainLayer().getWidth();
        return 0;
    }

    @Override
    public int getHeight() {
        if(this.getMainLayer() != null)
            return this.getMainLayer().getHeight();

        return 0;
    }

    public int getTileSize() {
        if(this.getMainLayer() != null)
            return (int) this.getMainLayer().getTileWidth();

        return 0;
    }

    @Override
    public LevelCameraBounds getCameraBounds() {
        return new LevelCameraBounds((int)(this.getPosition().x*this.getMainLayer().getTileWidth()),(int)(this.getPosition().y*this.getMainLayer().getTileHeight()),getWidth(), getHeight());
    }

    @Override
    public void setCameraBounds(LevelCameraBounds bounds) {

    }

    @Override
    public Vector2 getPosition () {
        return this.mapPosition;
    }

    @Override
    public void setPosition(Vector2 position) {
        this.mapPosition = position;
    }

    public ArrayList<IWall> getWalls() {
        return this.walls;
    }

    public HashMap<String, IActor> getActors() {
        return this.mapRoot.getChildren();
    }

    public void dispose() {
        this.map.dispose();
    }

    public TiledMapTileLayer getMainLayer() {
        if(this.mainLayer == null) {
            this.mainLayer = (TiledMapTileLayer)this.map.getLayers().get(this.mainLayerName);
        }

        return this.mainLayer;
    }

    public void initActors(IActorFactory factory) {
        MapLayer actorsLayer = map.getLayers().get(this.actorsLayerName);
        if(actorsLayer != null) {
            for(MapObject object : actorsLayer.getObjects()) {
               mapRoot.addChild(factory.fromMapObject(object, this.mapRoot));
            }
        }
    }

    public void initWalls(IWallFactory factory) {
        walls = new ArrayList<IWall>();
        MapLayer boundsLayer = map.getLayers().get(this.wallsLayerName);
        if(boundsLayer != null) {
            for (MapObject object :boundsLayer.getObjects()){

                walls.add(factory.fromMapObject(object, mapPosition));
            }
        }
    }
}
