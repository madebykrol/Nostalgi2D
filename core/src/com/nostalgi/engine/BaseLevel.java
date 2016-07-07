package com.nostalgi.engine;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nostalgi.engine.interfaces.IDoor;
import com.nostalgi.engine.interfaces.ILevel;
import com.nostalgi.engine.interfaces.ISpawner;
import com.nostalgi.engine.interfaces.IWall;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public abstract class BaseLevel implements ILevel {

    protected TiledMap map;
    private TiledMapTileLayer mainLayer;
    private int originX;
    private int originY;
    private final String boundsLayer;

    private ArrayList<Polygon> mapBounds;

    public BaseLevel(int originX, int originY) {
        this(originX, originY, "Bounds");
    }

    public BaseLevel(int originX, int originY, String boundsLayer) {
        this.originX = originX;
        this.originY = originY;
        this.boundsLayer = boundsLayer;
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
        this.setMainLayer();
        this.setMapBounds();
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
        if (this.mainLayer != null)
            return mainLayer.getWidth();
        return 0;
    }

    @Override
    public int getHeight() {
        if(this.mainLayer != null)
            return mainLayer.getHeight();

        return 0;
    }

    public int getTileSize() {
        if(this.mainLayer != null)
            return (int) mainLayer.getTileWidth();

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
        return this.mapBounds;
    }

    public void dispose() {
        this.map.dispose();
    }

    protected void setMainLayer() {
        if(this.map != null) {
            this.mainLayer = (TiledMapTileLayer)this.map.getLayers().get(0);
        }
    }

    protected void setMapBounds() {
        mapBounds = new ArrayList<Polygon>();
        MapLayer boundsLayer = map.getLayers().get(this.boundsLayer);
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
                System.out.println("Polygon yay: " + object.toString());
            }
        }
    }
}
