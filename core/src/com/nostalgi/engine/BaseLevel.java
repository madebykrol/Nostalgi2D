package com.nostalgi.engine;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nostalgi.engine.Factories.NostalgiActorFactory;
import com.nostalgi.engine.interfaces.Factories.IActorFactory;
import com.nostalgi.engine.interfaces.Factories.IWallFactory;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ILevel;
import com.nostalgi.engine.interfaces.World.ISpawner;
import com.nostalgi.engine.interfaces.World.IWall;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public abstract class BaseLevel implements ILevel {

    protected TiledMap map;
    private TiledMapTileLayer mainLayer;
    private int originX;
    private int originY;
    private final String wallsLayerName;
    private final String mainLayerName;
    private final String actorsLayerName;

    private ArrayList<IWall> mapBounds;
    private ArrayList<IActor> actors;

    public BaseLevel(int originX, int originY) {
        this(originX, originY, "Main", "Walls", "Actors");
    }

    public BaseLevel(int originX, int originY, String mainLayer, String wallsLayer, String actorsLayer) {
        this.originX = originX;
        this.originY = originY;
        this.mainLayerName = mainLayer;
        this.wallsLayerName = wallsLayer;
        this.actorsLayerName = actorsLayer;
    }

    @Override
    public ISpawner[] getNpcSpawns() {
        return new ISpawner[0];
    }

    @Override
    public ISpawner[] getMonsterSpawns() {
        return new ISpawner[0];
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
        return new LevelCameraBounds(originX,originY,getWidth(), getHeight());
    }

    @Override
    public void setCameraBounds(LevelCameraBounds bounds) {

    }

    public ArrayList<IWall> getWalls(IWallFactory factory) {
        if(this.mapBounds == null)
            initMapWalls(factory);

        return this.mapBounds;
    }

    public ArrayList<IActor> getActors(IActorFactory factory) {
        if(this.actors == null)
            initMapActors(factory);
        return this.actors;
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

    protected void initMapActors(IActorFactory factory) {
        actors = new ArrayList<IActor>();
        MapLayer actorsLayer = map.getLayers().get(this.actorsLayerName);
        if(actorsLayer != null) {
            for(MapObject object : actorsLayer.getObjects()) {
               actors.add(factory.fromMapObject(object));
            }
        }
    }

    protected void initMapWalls(IWallFactory factory) {
        mapBounds = new ArrayList<IWall>();
        MapLayer boundsLayer = map.getLayers().get(this.wallsLayerName);
        if(boundsLayer != null) {
            for (MapObject object :boundsLayer.getObjects()){

                mapBounds.add(factory.fromMapObject(object));
            }
        }
    }
}
