package com.nostalgi.engine;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.nostalgi.engine.Exceptions.FailedToSpawnActorException;
import com.nostalgi.engine.Navigation.INavMesh;
import com.nostalgi.engine.Navigation.NavigationMesh;
import com.nostalgi.engine.World.RootActor;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ILevel;
import com.nostalgi.engine.interfaces.World.IWall;
import com.nostalgi.engine.interfaces.World.IWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public class BaseLevel implements ILevel {

    private TiledMap map;
    private TiledMapTileLayer mainLayer;
    private final String wallsLayerName;
    private final String actorsLayerName;
    private final String groundLayerName;

    private Vector2 mapPosition;

    private ArrayList<IWall> walls = new ArrayList<IWall>();

    private IActor mapRoot = new RootActor();

    private IWorld world;
    private INavMesh navMesh;

    public BaseLevel(
                     TiledMap map,
                     IWorld world) {
        this(map, world,"Walls", "Actors", "Ground");
    }

    public BaseLevel(TiledMap map,
                     IWorld world,
                     String wallsLayer,
                     String actorsLayer,
                     String groundLayer) {
        this.map = map;
        this.mapPosition = new Vector2(0,0);
        this.wallsLayerName = wallsLayer;
        this.actorsLayerName = actorsLayer;
        this.groundLayerName = groundLayer;

        this.mapRoot.setPosition(new Vector2(0,0));

        this.world = world;
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
    public Vector2 getCameraInitLocation() {
        return new Vector2(0,0);
    }

    @Override
    public void initMap() {

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
            actor.destroy();
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
                String type = (String)object.getProperties().get("Type");
                try {
                    mapRoot.addChild(world.spawnActor(ClassReflection.forName(type), object, this.mapRoot, getMainLayer().getTileHeight()));
                } catch (FailedToSpawnActorException e) {
                    e.printStackTrace();
                } catch(ReflectionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initWalls() {
        walls = new ArrayList<IWall>();
        MapLayer boundsLayer = map.getLayers().get(this.wallsLayerName);
        if(boundsLayer != null) {
            for (MapObject object :boundsLayer.getObjects()){
                walls.add(world.createWall(object, mapPosition, getMainLayer().getTileWidth()));
            }
        }
    }

    @Override
    public String getGroundLayerName () {
        return this.groundLayerName;
    }

    @Override
    public void onLoad() {
        this.initMap();
        this.initActors();
        this.initWalls();

        if(map.getLayers().get("NavMesh") != null) {
            this.navMesh = new NavigationMesh(map.getLayers().get("NavMesh"), this.getTileSize());
            this.navMesh.generate();
        }
    }

    public INavMesh getNavMesh() {
        return this.navMesh;
    }

    public IWorld getWorld() {
        return world;
    }

    public IActor getMapRoot () {
        return this.mapRoot;
    }
}

