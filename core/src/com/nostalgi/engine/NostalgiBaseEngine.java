package com.nostalgi.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.nostalgi.engine.IO.Net.INetworkLayer;
import com.nostalgi.engine.IO.Net.NetworkRole;
import com.nostalgi.engine.World.NostalgiWorld;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameEngine;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.World.ILevel;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.Render.NostalgiCamera;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class NostalgiBaseEngine implements IGameEngine {

    private NostalgiCamera currentCamera;
    private NostalgiRenderer mapRenderer;
    private InputMultiplexer inputProcessor;

    private INetworkLayer networkLayer;

    private IWorld world;

    private Box2DDebugRenderer debug;

    private TmxMapLoader mapLoader;

    public NostalgiBaseEngine(NostalgiCamera camera, NostalgiRenderer mapRenderer) {
        this.currentCamera = camera;
        this.mapRenderer = mapRenderer;
        this.debug = new Box2DDebugRenderer();
        this.mapLoader = new TmxMapLoader();
    }

    @Override
    public void init() {
        // init input
        this.initInput();
    }

    @Override
    public void update() {
        float dTime = Gdx.graphics.getDeltaTime();

        // Update game mode.
        ICharacter currentCharacter = world.getGameMode().getCurrentController().getCurrentPossessedCharacter();

        if(world.getGameMode().getGameState().getNetworkRole() == NetworkRole.ROLE_AUTHORITY) {

            world.getGameMode().tick(dTime);
            for(IController controller :  world.getGameMode().getControllers()) {
                ICharacter character = controller.getCurrentPossessedCharacter();

                Body playerBody = character.getPhysicsBody();

                controller.update(dTime);

                playerBody.setLinearVelocity(character .getVelocity());

                if (character.fixtureNeedsUpdate()) {
                    world.updateBody(character);
                }
            }
            world.tick();

            tickActors(mapRenderer.getCurrentLevel().getActors(), dTime);

            replicateActors(mapRenderer.getCurrentLevel().getActors());
        }
        else {
            Body playerBody = currentCharacter.getPhysicsBody();
            // put input into simulation
            world.getGameMode().getCurrentController().update(dTime);

            playerBody.setLinearVelocity(currentCharacter.getVelocity());

            tickActors(mapRenderer.getCurrentLevel().getActors(), dTime);
            // Send input to server.

            // Run simulation
            world.tick();
        }

        // Update camera
        if(this.currentCamera != null && this.currentCamera.followPlayerCharacter()) {
            this.getWorld().setCameraPositionSafe(currentCharacter.getWorldPosition());
        }

        this.currentCamera.update();

        this.getWorld().getGameMode().tick(dTime);

        // Set view
        this.mapRenderer.setView(this.currentCamera);
    }

    @Override
    public void render() {
        this.mapRenderer.render(Gdx.graphics.getDeltaTime());

        if(world.getGameMode().getHud() != null) {
            world.getGameMode().getHud().draw(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        }

        debug.render(world.getPhysicsWorld(), currentCamera.combined);
    }

    @Override
    public void dispose() {
        world.getGameMode().dispose();
        this.world.dispose();
    }

    @Override
    public IWorld getWorld() {
        return this.world;
    }

    @Override
    public void setCurrentCamera(NostalgiCamera camera) {
        this.currentCamera = camera;
    }

    @Override
    public NostalgiCamera getCurrentCamera() {
        return this.currentCamera;
    }

    @Override
    public void setMapRenderer(NostalgiRenderer renderer) {
        this.mapRenderer = renderer;
    }

    @Override
    public NostalgiRenderer getMapRenderer() {
        return this.mapRenderer;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return inputProcessor;
    }

    @Override
    public void loadLevel(String level) {
        TiledMap map = mapLoader.load(level+(!level.contains(".tmx") ? ".tmx" : ""));
        // Setup start level
        MapProperties mapProperties = map.getProperties();
        Object GameMode = mapProperties.containsKey("GameMode") ? map.getProperties().get("GameMode") : null;
        Object Type = mapProperties.containsKey("Type") ? map.getProperties().get("Type") : null;
        try {

            world = new NostalgiWorld(new World(new Vector2(0,0), true), mapRenderer, currentCamera);

            if(Type != null) {
                if(Type instanceof String) {
                    Class c =  ClassReflection.forName((String)Type);

                    Constructor ctor = ClassReflection.getConstructor(c, TiledMap.class, IWorld.class);
                    ILevel lvl = (ILevel)ctor.newInstance(map, world);
                    mapRenderer.loadLevel(lvl);
                    world.setWorldBounds(lvl.getCameraBounds());
                    world.setCameraPositionSafe(lvl.getCameraInitLocation());
                }
            }

            if(GameMode != null) {
                if(GameMode instanceof String) {
                    Class c =  ClassReflection.forName((String)GameMode);
                    Constructor ctor = ClassReflection.getConstructor(c, IWorld.class);
                    IGameMode gameMode = (IGameMode)ctor.newInstance(world);

                    world.setGameMode(gameMode);
                }
            }


        } catch (ReflectionException e) {
            e.printStackTrace();
        }

    }

    private void replicateActors(HashMap<String, IActor> actors) {
        for(IActor actor : actors.values()) {
            if(actor.isReplicated()) {
                if(actor.getChildren() != null) {
                    replicateActors(actor.getChildren());
                }
            }
        }
    }

    private void tickActors(HashMap<String, IActor> actors, float delta) {
        for(Map.Entry<String, IActor> entry : actors.entrySet()) {
            IActor actor = entry.getValue();
            if(actor.canEverTick()) {
                actor.tick(delta);
                if(actor.physicsSimulated()) {
                    Body playerBody = actor.getPhysicsBody();

                    Vector2 playerPos = actor.getWorldPosition();
                    // Center it to character to physics body!

                    playerPos.x = playerBody.getPosition().x - 0.5f;
                    playerPos.y = playerBody.getPosition().y - 0.5f;
                    actor.setPosition(playerPos);
                }
                if(actor.getChildren() != null)
                    this.tickActors(actor.getChildren(), delta);
            }
        }

    }

    private void initInput() {

        // Set input processor to multiplexer
        inputProcessor = new InputMultiplexer();

        // Get and set all input processors.

        // Hud input
        if(world.getGameMode().getHud().getInputProcessor() != null)
            inputProcessor.addProcessor(world.getGameMode().getHud().getInputProcessor());

        // Set gesture input processor
        if (world.getGameMode().getCurrentController().getGestureListener() != null)
            inputProcessor.addProcessor(new GestureDetector(
                    world.getGameMode().getCurrentController().getGestureListener()));

        // Set standard input processor from controller
        if(world.getGameMode().getCurrentController().getInputProcessor() != null)
            inputProcessor.addProcessor(
                    world.getGameMode().getCurrentController().getInputProcessor());

        Gdx.input.setInputProcessor(inputProcessor);
    }
}
