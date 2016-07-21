package com.nostalgi.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.Factories.NostalgiActorFactory;
import com.nostalgi.engine.Factories.NostalgiWallFactory;
import com.nostalgi.engine.interfaces.Factories.IActorFactory;
import com.nostalgi.engine.interfaces.Factories.IWallFactory;
import com.nostalgi.engine.interfaces.IFollowCamera;
import com.nostalgi.engine.interfaces.IGameEngine;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.physics.CollisionCategories;
import com.nostalgi.render.NostalgiCamera;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class NostalgiBaseEngine implements IGameEngine {

    protected IGameState state;
    protected IGameMode mode;
    protected IHud hud;

    protected NostalgiCamera currentCamera;
    protected NostalgiRenderer mapRenderer;
    protected InputMultiplexer inputProcessor;

    protected Body playerBody;

    protected World world;

    protected IWallFactory wallFactory;
    protected IActorFactory actorFactory;

    private Box2DDebugRenderer debug;

    public NostalgiBaseEngine(Vector2 gravity) {
        world = new World(gravity, true);
        wallFactory = new NostalgiWallFactory(world, 32f);
        actorFactory = new NostalgiActorFactory(world, 32f);
    }

    public NostalgiBaseEngine(IGameState state, IGameMode mode, NostalgiRenderer mapRenderer) {
        this(state.getGravity());
        this.state = state;
        this.mode = mode;

        this.mapRenderer = mapRenderer;
        this.debug = new Box2DDebugRenderer();
    }

    @Override
    public void init() {
        // init input
        this.initInput();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                try {
                    Fixture a = contact.getFixtureA();
                    Fixture b = contact.getFixtureB();

                    IActor actorA = (IActor) a.getBody().getUserData();
                    IActor actorB = (IActor) b.getBody().getUserData();

                    if (actorA != null && actorB != null) {
                        actorA.onOverlapBegin(actorB);
                        actorB.onOverlapBegin(actorA);
                    }
                } catch (ClassCastException e) {

                }
            }

            @Override
            public void endContact(Contact contact) {
                try {
                    Fixture a = contact.getFixtureA();
                    Fixture b = contact.getFixtureB();

                    IActor actorA = (IActor) a.getBody().getUserData();
                    IActor actorB = (IActor) b.getBody().getUserData();

                    if (actorA != null && actorB != null) {
                        actorA.onOverlapEnd(actorB);
                        actorB.onOverlapEnd(actorA);
                    }
                } catch (ClassCastException e) {

                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        // Update playerbounds
        initPlayerBounds();

        // Update terrain / map bounds
        initMapWalls();

        // Init map objects. like triggers, chests doors.
        initMapActors();
    }

    @Override
    public void update() {

        world.step(1f / 60f, 6, 2);
        // Update NPC / Monster bounds
        if(getGameState().getCurrentController().getCurrentPossessedCharacter().canEverTick()) {
            getGameState().getCurrentController().getCurrentPossessedCharacter().tick(Gdx.graphics.getDeltaTime());
        }

        HashMap<String, IActor> actors =  getGameState().getCurrentLevel().getActors();
        tickActors(actors);

        playerBody.setLinearVelocity(this.getGameState().getCurrentController().getCurrentPossessedCharacter().getVelocity());
        this.getGameState().getCurrentController().getCurrentPossessedCharacter().getWorldPosition().x = playerBody.getPosition().x-0.5f;
        this.getGameState().getCurrentController().getCurrentPossessedCharacter().getWorldPosition().y = playerBody.getPosition().y-0.5f;

        switch(this.getGameState().getCurrentController().getCurrentPossessedCharacter().getFloorLevel()) {
            case 1 :
                changePlayerFixture((short)(CollisionCategories.MASK_PLAYER | CollisionCategories.CATEGORY_FLOOR_1));
                break;
            case 2 :
                changePlayerFixture((short)(CollisionCategories.MASK_PLAYER | CollisionCategories.CATEGORY_FLOOR_2));
                break;
            case 3 :
                changePlayerFixture((short)(CollisionCategories.MASK_PLAYER | CollisionCategories.CATEGORY_FLOOR_3));
                break;
            case 4 :
                changePlayerFixture((short)(CollisionCategories.MASK_PLAYER | CollisionCategories.CATEGORY_FLOOR_4));
                break;
            default :
                changePlayerFixture((short)(CollisionCategories.MASK_PLAYER | CollisionCategories.CATEGORY_FLOOR_1));
                break;
        }
        // Update state.
        this.state.update(Gdx.graphics.getDeltaTime());

        // Update camera
        if(this.currentCamera instanceof IFollowCamera) {
            this.currentCamera.setPositionSafe(this.getGameState()
                    .getCurrentController()
                    .getCurrentPossessedCharacter()
                    .getPosition());
        }
        this.currentCamera.update();

        // Set view
        this.mapRenderer.setView(this.currentCamera);
    }

    @Override
    public void render() {

        this.mapRenderer.setCurrentPlayerCharacter(this.getGameState().getCurrentController().getCurrentPossessedCharacter());
        this.mapRenderer.render(Gdx.graphics.getDeltaTime());

        if(hud != null) {
            hud.draw(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        }

        debug.render(world, currentCamera.combined);
    }

    @Override
    public void dispose() {
        this.hud.dispose();
        this.world.dispose();
    }

    @Override
    public IGameState getGameState() {
        return this.state;
    }

    @Override
    public IGameMode getGameMode() {
        return this.mode;
    }

    @Override
    public IHud getHud() {
        return this.hud;
    }

    @Override
    public void setHud(IHud hud) {
        this.hud = hud;
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

    private void tickActors(HashMap<String, IActor> actors) {
        for(Map.Entry<String, IActor> entry : actors.entrySet()) {
            String name = entry.getKey();
            IActor actor = entry.getValue();

            if(actor.canEverTick()) {
                actor.tick(Gdx.graphics.getDeltaTime());
            }
        }
    }

    private void initPlayerBounds() {
        // Update player bounds

        if(playerBody == null) {
            BodyDef playerBodyDef = new BodyDef();
            PolygonShape shape = new PolygonShape();
            playerBodyDef.fixedRotation = true;
            playerBodyDef.position.x = getGameState().getPlayerCharacter().getPosition().x;
            playerBodyDef.position.y = getGameState().getPlayerCharacter().getPosition().y;

            playerBodyDef.type = BodyDef.BodyType.DynamicBody;

            shape.setAsBox(1*0.5f,1*0.5f);

            FixtureDef blockingBounds = new FixtureDef();

            blockingBounds.density = 1f;
            blockingBounds.friction = 0f;
            blockingBounds.shape = shape;
            blockingBounds.filter.categoryBits = CollisionCategories.CATEGORY_PLAYER;
            blockingBounds.filter.maskBits = CollisionCategories.MASK_PLAYER | CollisionCategories.CATEGORY_FLOOR_1;
            playerBody = world.createBody(playerBodyDef);
            playerBody.setUserData(this.getGameState().getPlayerCharacter());
            playerBody.createFixture(blockingBounds);
        }
    }

    private void changePlayerFixture(short playerMask) {
        BodyDef playerBodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        playerBodyDef.fixedRotation = true;
        playerBodyDef.position.x = getGameState().getPlayerCharacter().getPosition().x;
        playerBodyDef.position.y = getGameState().getPlayerCharacter().getPosition().y;

        Fixture fix = playerBody.getFixtureList().get(0);
        playerBody.destroyFixture(fix);

        playerBodyDef.type = BodyDef.BodyType.DynamicBody;

        shape.setAsBox(1*0.5f,1*0.5f);

        FixtureDef blockingBounds = new FixtureDef();

        blockingBounds.density = 1f;
        blockingBounds.friction = 0f;
        blockingBounds.shape = shape;
        blockingBounds.filter.categoryBits = CollisionCategories.CATEGORY_PLAYER;
        blockingBounds.filter.maskBits = playerMask;
        playerBody.createFixture(blockingBounds);
    }

    private void initInput() {

        // Set input processor to multiplexer
        inputProcessor = new InputMultiplexer();

        // Get and set all input processors.

        // Hud input
        if(this.hud.getInputProcessor() != null)
            inputProcessor.addProcessor(this.hud.getInputProcessor());

        // Set gesture input processor
        if (this.state.getCurrentController().getGestureListener() != null)
            inputProcessor.addProcessor(new GestureDetector(
                    state.getCurrentController().getGestureListener()));

        // Set standard input processor from controller
        if(this.state.getCurrentController().getInputProcessor() != null)
            inputProcessor.addProcessor(
                    state.getCurrentController().getInputProcessor());

        Gdx.input.setInputProcessor(inputProcessor);
    }

    private void initMapActors() {
        this.getGameState().getCurrentLevel().initActors(this.actorFactory);
    }

    private void initMapWalls () {
        this.getGameState().getCurrentLevel().initWalls(this.wallFactory);
    }



}
