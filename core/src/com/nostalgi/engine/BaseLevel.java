package com.nostalgi.engine;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.nostalgi.engine.interfaces.IDoor;
import com.nostalgi.engine.interfaces.ILevel;
import com.nostalgi.engine.interfaces.ISpawner;
import com.nostalgi.engine.interfaces.IWall;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public abstract class BaseLevel implements ILevel {

    protected TiledMap map;
    private TiledMapTileLayer mainLayer;

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

    protected void setMainLayer() {
        if(this.map != null) {
            this.mainLayer = (TiledMapTileLayer)this.map.getLayers().get(0);
        }
    }
}
