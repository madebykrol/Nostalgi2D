package com.nostalgi.engine;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.nostalgi.engine.interfaces.World.IDoor;
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
    private final String boundsLayerName;
    private final String mainLayerName;
    private boolean mapInitialized = false;

    private ArrayList<Polygon> mapBounds;

    public BaseLevel(int originX, int originY) {
        this(originX, originY, "Main", "Bounds");
    }

    public BaseLevel(int originX, int originY, String mainLayer, String boundsLayer) {
        this.originX = originX;
        this.originY = originY;
        this.mainLayerName = mainLayer;
        this.boundsLayerName = boundsLayer;
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
    public IWall[] getWalls() {
        return new IWall[0];
    }

    @Override
    public IDoor[] getDoors() {
        return new IDoor[0];
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

    public ArrayList<Polygon> getMapBounds() {
        if(this.mapBounds == null)
            initMapBounds();

        return this.mapBounds;
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

    protected void initMapBounds() {
        mapBounds = new ArrayList<Polygon>();
        MapLayer boundsLayer = map.getLayers().get(this.boundsLayerName);
        if(boundsLayer != null) {
            for (MapObject object :boundsLayer.getObjects()){

                Polygon pShape = new Polygon();

                if(object instanceof RectangleMapObject) {
                    RectangleMapObject obj = (RectangleMapObject)object;

                    // Derp
                } else if( object instanceof PolygonMapObject) {
                    PolygonMapObject obj = (PolygonMapObject) object;

                    pShape = obj.getPolygon();
                    // Herp
                }

                mapBounds.add(pShape);
            }
        }
    }
}
