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
import com.nostalgi.engine.Exceptions.FailedToSpawnActorException;
import com.nostalgi.engine.IO.Net.INetworkLayer;
import com.nostalgi.engine.IO.Net.NetworkRole;
import com.nostalgi.engine.World.NostalgiWorld;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameEngine;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.World.ILevel;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.Render.NostalgiCamera;

import java.util.HashMap;
import java.util.Map;

/**
 * Base engine for nostalgi games.
 * This engine is a general purpose implementation of the basic concepts of nostalgi.
 *
 * It is possible to override most parts of the engine with or completely implement the IGameEngine interface
 * on your own.
 * Created by ksdkrol on 2016-07-04.
 */
public class NostalgiBaseEngine implements IGameEngine {

    // Current camera.
    private NostalgiCamera currentCamera;

    // Map renderer
    private NostalgiRenderer mapRenderer;

    // Input router.
    private InputMultiplexer inputProcessor;

    // The network abstraction layer.
    private INetworkLayer networkLayer;

    // Representation of the world
    private IWorld world;

    // debug renderer.
    private Box2DDebugRenderer debugRenderer;

    // Map loader
    private TmxMapLoader mapLoader;

    // Debug
    protected boolean debug;

    private boolean headless;

    /**
     *
     * @param camera
     * @param mapRenderer
     */
    public NostalgiBaseEngine(NostalgiCamera camera, NostalgiRenderer mapRenderer, boolean headless) {
        this(camera, mapRenderer, new TmxMapLoader(), headless, new Box2DDebugRenderer(), false);
    }

    public NostalgiBaseEngine(NostalgiCamera camera, NostalgiRenderer renderer, TmxMapLoader mapLoader, boolean headless, Box2DDebugRenderer debugRenderer, boolean debug) {
        this.currentCamera = camera;
        this.mapRenderer = renderer;
        this.mapLoader = mapLoader;
        this.debugRenderer = debugRenderer;
        this.debug = debug;
        this.headless = headless;
    }

    @Override
    public void init() {
        // init input
        this.initInput();
    }

    @Override
    public void update() {
        // Time difference since last frame.
        float dTime = Gdx.graphics.getDeltaTime();

        // Update game mode.
        ICharacter currentCharacter = world.getGameMode().getCurrentController().getCurrentPossessedCharacter();

        // Check if we are in authoritative mode (Server). (Single player games are always authoritative.)
        if(world.getGameMode().getGameState().getNetworkRole() == NetworkRole.ROLE_AUTHORITY) {

            // Tick the gamemode.
            world.getGameMode().tick(dTime);

            // Get updates from controller.
            for(IController controller :  world.getGameMode().getControllers()) {
                ICharacter character = controller.getCurrentPossessedCharacter();

                // Tick the controller
                controller.update(dTime);

                // In case something has changed on the character that require a update on the physics body
                // We do it here.
                // Commonly this is done after a character has changed floor on a level.
                if (character.fixtureNeedsUpdate()) {
                    world.updateBody(character);
                }
            }
            // Tick the world
            world.tick();
            // Tick all the actors.
            tickActors(mapRenderer.getCurrentLevel().getActors(), dTime);

            // Replicate actors.
            replicateActors(mapRenderer.getCurrentLevel().getActors());
        }  else {

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

        if(debug)
            debugRenderer.render(world.getPhysicsWorld(), currentCamera.combined);
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

        // Load the map.
        TiledMap map = mapLoader.load(level+(!level.contains(".tmx") ? ".tmx" : ""));
        // Setup start level

        // Get all the map properties
        MapProperties mapProperties = map.getProperties();

        // Get game mode
        Object GameMode = mapProperties.containsKey("GameMode") ? map.getProperties().get("GameMode") : null;

        // Get Map type
        Object Type = mapProperties.containsKey("Type") ? map.getProperties().get("Type") : null;
        try {
            // Set the world.
            world = new NostalgiWorld(new World(new Vector2(0,0), true), mapRenderer, currentCamera);

            // If the map has a type
            if(Type != null) {
                // And the type is actually defined
                if(Type instanceof String) {
                    // Create an instance of the map type
                    Class c =  ClassReflection.forName((String)Type);

                    Constructor ctor = ClassReflection.getConstructor(c, TiledMap.class, IWorld.class);
                    ILevel lvl = (ILevel)ctor.newInstance(map, world);

                    // Add it to the renderer.
                    mapRenderer.loadLevel(lvl);

                    // set bounds for the world on the edges of the map
                    world.setWorldBounds(lvl.getCameraBounds());

                    // set the camera starting position. @TODO: This might have to be reworked to allow for dynamic cameras
                    world.setCameraPositionSafe(lvl.getCameraInitLocation());
                }
            }
            // if the map has a game mode
            if(GameMode != null) {
                // and it is actually defined
                if(GameMode instanceof String) {
                    // create an instance
                    Class c =  ClassReflection.forName((String)GameMode);
                    Constructor ctor = ClassReflection.getConstructor(c, IWorld.class);
                    IGameMode gameMode = (IGameMode)ctor.newInstance(world);

                    // set the game mode on the world.
                    world.setGameMode(gameMode);
                }
            }


        } catch (ReflectionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void createNewPlayer(IPlayerState state) {

        // When a new player joins the game we need to assign a controller.
        Class controllerClass = this.world.getGameMode().getDefaultControllerClass();
        Class playerStateClass = this.world.getGameMode().getDefaultPlayerStateClass();

        IController controller = null;
        try {
            // and spawn a default pawn.
            // @TODO If the map is set to always spawn a "SpectorCharacter" spawn from that class instead.

            ICharacter playerCharacter = (ICharacter)world.spawnActor(this.world.getGameMode().getDefaultCharacterClass(), state.getPlayerName(), true, new Vector2(8, 53));
            try {
                controller = (IController) ClassReflection.getConstructor(controllerClass, IWorld.class).newInstance(world);
            } catch (ReflectionException e) {
                if(e.getCause() instanceof NoSuchMethodException) {
                    try {
                        controller = (IController) ClassReflection.newInstance(controllerClass);
                    } catch(ReflectionException e2) {
                        e.printStackTrace();
                    }
                }
            }
            // Possess the freshly spawned character.
            controller.possessCharacter(playerCharacter);
        } catch (FailedToSpawnActorException e) {
            e.printStackTrace();
        }
        // Add the controller to the game mode allowing it to pass input along to the engine.
        this.world.getGameMode().addController(controller);
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

    /**
     * Tick actors.
     * @param actors
     * @param delta
     */
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
        if(world.getGameMode().getCurrentController() != null) {
            if (world.getGameMode().getCurrentController().getGestureListener() != null)
                inputProcessor.addProcessor(new GestureDetector(
                        world.getGameMode().getCurrentController().getGestureListener()));

            // Set standard input processor from controller
            if (world.getGameMode().getCurrentController().getInputProcessor() != null)
                inputProcessor.addProcessor(
                        world.getGameMode().getCurrentController().getInputProcessor());

        }
        Gdx.input.setInputProcessor(inputProcessor);
    }
}
