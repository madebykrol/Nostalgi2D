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
import com.nostalgi.engine.interfaces.World.IWall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public abstract class BaseLevel implements ILevel {

    private TiledMap map;
    private TiledMapTileLayer mainLayer;
    private final String wallsLayerName;
    private final String actorsLayerName;
    private final String groundLayerName;

    private Vector2 mapPosition;

    private ArrayList<IWall> walls = new ArrayList<IWall>();

    private IActor mapRoot = new RootActor();

    private IWallFactory wallFactory;
    private IActorFactory actorFactory;

    public BaseLevel(Vector2 origin,
                     IWallFactory wallFactory,
                     IActorFactory actorFactory) {
        this(origin, wallFactory, actorFactory, "Walls", "Actors", "Ground");
    }

    public BaseLevel(Vector2 origin,
                     IWallFactory wallFactory,
                     IActorFactory actorFactory,
                     String wallsLayer,
                     String actorsLayer,
                     String groundLayer) {
        this.mapPosition = origin;
        this.wallsLayerName = wallsLayer;
        this.actorsLayerName = actorsLayer;
        this.groundLayerName = groundLayer;

        this.mapRoot.setPosition(origin);

        this.wallFactory = wallFactory;
        this.actorFactory = actorFactory;
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
        return new LevelCameraBounds(
                (int)(this.getPosition().x*this.getMainLayer().getTileWidth()),
                (int)(this.getPosition().y*this.getMainLayer().getTileHeight()),
                getWidth(),
                getHeight());
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

    @Override
    public void addActor(IActor actor) {
        this.mapRoot.addChild(actor);
        actor.setParent(this.mapRoot);
    }

    public void dispose() {
        this.map.dispose();
        for(Map.Entry<String, IActor> entry : getActors().entrySet()) {
            IActor actor = entry.getValue();

            this.actorFactory.destroyActor(actor);
        }
    }

    public TiledMapTileLayer getMainLayer() {
        if(this.mainLayer == null)
            this.mainLayer = (TiledMapTileLayer)this.map.getLayers().get(0);

        return this.mainLayer;
    }

    public void initActors() {
        MapLayer actorsLayer = map.getLayers().get(this.actorsLayerName);
        if(actorsLayer != null) {
            for(MapObject object : actorsLayer.getObjects()) {
               mapRoot.addChild(actorFactory.fromMapObject(object, this.mapRoot, getMainLayer().getTileWidth()));
            }
        }
        return;
    }

    @Override
    public void initWalls() {
        walls = new ArrayList<IWall>();
        MapLayer boundsLayer = map.getLayers().get(this.wallsLayerName);
        if(boundsLayer != null) {
            for (MapObject object :boundsLayer.getObjects()){
                walls.add(wallFactory.fromMapObject(object, mapPosition, getMainLayer().getTileWidth()));
            }
        }
    }

    @Override
    public String getGroundLayerName () {
        return this.groundLayerName;
    }

}
