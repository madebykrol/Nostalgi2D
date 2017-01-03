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
import com.nostalgi.engine.Factories.WorldFactory;
import com.nostalgi.engine.IO.Net.INetworkLayer;
import com.nostalgi.engine.IO.Net.NetworkRole;
import com.nostalgi.engine.IO.Net.PlayerSession;
import com.nostalgi.engine.Utils.Guid;
import com.nostalgi.engine.interfaces.Factories.IWorldFactory;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameEngine;
import com.nostalgi.engine.interfaces.IGameInstance;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.World.ILevel;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.Render.NostalgiCamera;

import java.util.ArrayList;
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

    public static IGameEngine instance = null;

    // Current camera.
    private NostalgiCamera currentCamera;

    // Map renderer
    private NostalgiRenderer mapRenderer;

    // Input router.
    private InputMultiplexer inputProcessor;

    // The network abstraction layer.
    private INetworkLayer networkLayer;

    // The world factory.
    private IWorldFactory worldFactory;

    // Representation of the world
    private IWorld world;

    // debug renderer.
    private Box2DDebugRenderer debugRenderer;

    // Map loader
    private TmxMapLoader mapLoader;

    // Debug
    protected boolean debug;

    private boolean headless;

    private IGameInstance gameInstance;

    private ArrayList<PlayerSession> playerSessions = new ArrayList<PlayerSession>();

    /**
     * Simplest engine constructor, uses default Map loader, world factory and debug renderer.
     * It also sets the engine to render on each frame by setting headless to false.
     *
     * @param camera
     * @param mapRenderer
     * @param gameInstance
     */
    public NostalgiBaseEngine(NostalgiCamera camera, NostalgiRenderer mapRenderer, IGameInstance gameInstance) {
        this(camera, mapRenderer, gameInstance, false);
    }

    /**
     * Use this constructor if you wish to run the engine with default MapLoader, world factory and debug renderer but in headless mode.
     * @param camera
     * @param mapRenderer
     */
    public NostalgiBaseEngine(NostalgiCamera camera, NostalgiRenderer mapRenderer, IGameInstance gameInstance, boolean headless) {
        this(camera, mapRenderer, new TmxMapLoader(), new WorldFactory(), new Box2DDebugRenderer(), gameInstance, headless, true);
    }

    public NostalgiBaseEngine(NostalgiCamera camera, NostalgiRenderer renderer, TmxMapLoader mapLoader, IWorldFactory worldFactory,  Box2DDebugRenderer debugRenderer, IGameInstance gameInstance, boolean headless, boolean debug) {
        this.currentCamera = camera;
        this.mapRenderer = renderer;
        this.mapLoader = mapLoader;
        this.debugRenderer = debugRenderer;
        this.debug = debug;
        this.headless = headless;
        this.gameInstance = gameInstance;
        this.worldFactory = worldFactory;
    }

    @Override
    public void init() {
        // init input
        this.initInput();

        this.getWorld().getGameMode().init();
        NostalgiBaseEngine.instance = this;
    }

    @Override
    public void update() {

        // Time difference since last frame.
        float dTime = Gdx.graphics.getDeltaTime();


        // Check if we are in authoritative mode (Server). (Single player games are always authoritative.)
        if(this.world.getGameMode().getGameState().getNetworkRole() == NetworkRole.ROLE_AUTHORITY) {

            // Tick the gamemode.
            this.world.getGameMode().tick(dTime);

            ArrayList<IController> controllers = world.getControllers();
            controllers.addAll(world.getAIController());
            // Get updates from controller.
            for(IController controller :  controllers) {
                ICharacter character = controller.getCurrentPossessedCharacter();

                // Tick the controller
                controller.tick(dTime);

                // In case something has changed on the character that require a tick on the physics body
                // We do it here.
                // Commonly this is done after a character has changed floor on a level.
                if (character.fixtureNeedsUpdate()) {
                    world.updateBody(character);
                }
            }

            // Tick the world
            this.world.tick();
            // Tick all the actors.
            tickActors(mapRenderer.getCurrentLevel().getActors(), dTime);

            // Replicate actors.
            replicateActors(mapRenderer.getCurrentLevel().getActors());
        }  else {

        }

        // Update camera
        if(this.currentCamera != null) {
            ICharacter currentCharacter = world.getCurrentController().getCurrentPossessedCharacter();

            if(currentCharacter != null) {
                this.getWorld().setCameraPositionSafe(currentCharacter.getWorldPosition());
            }
            this.currentCamera.update();

            // Set view
            this.mapRenderer.setView(this.currentCamera);
        }
    }

    @Override
    public void render() {
        float dTime = Gdx.graphics.getDeltaTime();
        this.mapRenderer.render(dTime);
        // Apply light to the scene.
        this.world.applyLight();

        if(world.getGameMode().getHud() != null) {
            world.getGameMode().getHud().draw(Math.min(dTime, 1 / 30f));
        }

        if(debug)
            debugRenderer.render(world.getPhysicsWorld(), currentCamera.combined);
    }

    @Override
    public void dispose() {
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
        Object Type = mapProperties.containsKey("Type") ? (String)map.getProperties().get("Type") : "com.nostalgi.engine.BaseLevel";

        float gravityX = mapProperties.containsKey("GravityX") ? Float.valueOf((String)mapProperties.get("GravityX")) : 0;
        float gravityY = mapProperties.containsKey("GravityY") ? Float.valueOf((String)mapProperties.get("GravityY")) : 0;

        // Controllers from the old map / game mode
        ArrayList<IController> currentControllers = new ArrayList<IController>();
        try {

            if (this.world != null){
                currentControllers.addAll(this.world.getControllers());
                this.world.dispose();

            }
            // Set the world.
            this.world = worldFactory.create(new World(new Vector2(gravityX,gravityY), true), mapRenderer, currentCamera);

            // if the map has a game mode
            if(GameMode != null) {
                // and it is actually defined
                if(GameMode instanceof String) {
                    // create an instance
                    Class c =  ClassReflection.forName((String)GameMode);
                    Constructor ctor = ClassReflection.getConstructor(c, IWorld.class);
                    IGameMode gameMode = (IGameMode)ctor.newInstance(world);

                    // Everytime we set a new gameMode we pass the gameInstance.
                    gameMode.setGameInstance(this.gameInstance);

                    // set the game mode on the world.
                    this.world.setGameMode(gameMode);
                }
            } else {

            }

            // If the map has a type
            if(Type != null) {
                // And the type is actually defined
                Class  c = ClassReflection.forName((String) Type);

                Constructor ctor = ClassReflection.getConstructor(c, TiledMap.class, IWorld.class);
                ILevel lvl = (ILevel)ctor.newInstance(map, this.world);

                // Add it to the renderer.
                this.mapRenderer.loadLevel(lvl);
                this.world.getNavigationSystem().loadNavMesh(lvl.getNavMesh());

                // set bounds for the world on the edges of the map
                this.world.setWorldBounds(lvl.getCameraBounds());

                // set the camera starting position. @TODO: This might have to be reworked to allow for dynamic cameras
                this.world.setCameraPositionSafe(lvl.getCameraInitLocation());
            }

            // Create players.
            for(PlayerSession playerSession : playerSessions) {
                this.spawnStateControllerAndPawnForPlayer(playerSession);
            }

        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        this.initInput();

        this.getWorld().getGameMode().init();
    }

    @Override
    public void createNewPlayer(String playerName, Guid playerId) {
        PlayerSession ps = new PlayerSession();
        ps.setPlayerId(playerId);

        spawnStateControllerAndPawnForPlayer(ps);
        this.playerSessions.add(ps);
    }

    private void spawnStateControllerAndPawnForPlayer(PlayerSession ps) {
        // When a new player joins the game we need to assign a controller.
        Class controllerClass = this.world.getGameMode().getDefaultControllerClass();
        Class playerStateClass = this.world.getGameMode().getDefaultPlayerStateClass();

        IPlayerState playerState = new BasePlayerState();

        try {
            if (playerStateClass != null) {
                playerState = (IPlayerState) ClassReflection.getConstructor(playerStateClass).newInstance();
            }
        } catch (ReflectionException e) {
            e.printStackTrace();
        }



        if(ps.getCurrentController() != null) {
            playerState.join(ps.getCurrentController().getPlayerState());
        } else {
            playerState.setPlayerName(ps.getPlayerName());
            playerState.setPlayerUniqueId(ps.getPlayerId());
        }

        IController controller = null;
        try {
            // and spawn a default pawn.
            // @TODO If the map is set to always spawn a "SpectatorCharacter" spawn from that class instead.

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

            if(controller != null) {
                controller.setPlayerState(playerState);
                ICharacter playerCharacter = (ICharacter) world.spawnActor(this.world.getGameMode().getDefaultCharacterClass(), controller.getPlayerState().getPlayerName(), true, new Vector2(32, 26));
                // Possess the freshly spawned character.
                controller.possessCharacter(playerCharacter);
            }

        } catch (FailedToSpawnActorException e) {
            e.printStackTrace();
        }

        ps.setCurrentController(controller);

        // Add the controller to the game mode allowing it to pass input along to the engine.
        if(!this.world.getControllers().contains(controller))
            this.world.addController(controller);

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
        if(world.getCurrentController() != null) {
            if (world.getCurrentController().getGestureListener() != null)
                inputProcessor.addProcessor(new GestureDetector(
                        world.getCurrentController().getGestureListener()));

            // Set standard input processor from controller
            if (world.getCurrentController().getInputProcessor() != null)
                inputProcessor.addProcessor(
                        world.getCurrentController().getInputProcessor());

        }
        Gdx.input.setInputProcessor(inputProcessor);
    }
}
