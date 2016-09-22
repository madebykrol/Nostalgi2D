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
import com.nostalgi.engine.interfaces.IGameEngine;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.physics.BoundingVolume;
import com.nostalgi.engine.physics.CollisionCategories;
import com.nostalgi.engine.Render.NostalgiCamera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class NostalgiBaseEngine implements IGameEngine {
    private IGameMode mode;

    private NostalgiCamera currentCamera;
    private NostalgiRenderer mapRenderer;
    private InputMultiplexer inputProcessor;

    private Body playerBody;

    private World world;

    private Box2DDebugRenderer debug;

    public NostalgiBaseEngine(World world, NostalgiCamera camera, NostalgiRenderer mapRenderer, IGameMode mode) {
        this.world = world;
        this.currentCamera = camera;
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
        initPlayerBounds(this.getGameMode().getCurrentController().getCurrentPossessedCharacter());

        // Update terrain / map bounds
        initMapWalls();

        // Init map objects. like triggers, chests doors.
        initMapActors();
    }

    @Override
    public void update() {
        float dTime = Gdx.graphics.getDeltaTime();

        // Update game mode.
        this.getGameMode().update(dTime);

        // Update NPC / Monster bounds
        if(this.getGameMode().getCurrentController().getCurrentPossessedCharacter().canEverTick()) {
            this.getGameMode().getCurrentController().getCurrentPossessedCharacter().tick(Gdx.graphics.getDeltaTime());
        }

        this.getGameMode().getCurrentController().update(dTime);

        HashMap<String, IActor> actors =  this.getGameMode().getGameState().getCurrentLevel().getActors();
        tickActors(actors, dTime);

        Body playerBody = this.getGameMode().getCurrentController().getCurrentPossessedCharacter().getPhysicsBody();
        playerBody.setLinearVelocity(this.getGameMode().getCurrentController().getCurrentPossessedCharacter().getVelocity());

        Vector2 playerPos = this.getGameMode().getCurrentController().getCurrentPossessedCharacter().getWorldPosition();
        playerPos.x = playerBody.getPosition().x - 0.5f;
        playerPos.y = playerBody.getPosition().y - 0.5f;

        switch(getGameMode().getCurrentController().getCurrentPossessedCharacter().getFloorLevel()) {
            case 1 :
                changePlayerFixture(getGameMode().getCurrentController().getCurrentPossessedCharacter(),
                        (short)(CollisionCategories.MASK_PLAYER | CollisionCategories.CATEGORY_FLOOR_1));
                break;
            case 2 :
                changePlayerFixture(getGameMode().getCurrentController().getCurrentPossessedCharacter(),
                        (short)(CollisionCategories.MASK_PLAYER | CollisionCategories.CATEGORY_FLOOR_2));
                break;
            case 3 :
                changePlayerFixture(getGameMode().getCurrentController().getCurrentPossessedCharacter(),
                        (short)(CollisionCategories.MASK_PLAYER | CollisionCategories.CATEGORY_FLOOR_3));
                break;
            case 4 :
                changePlayerFixture(getGameMode().getCurrentController().getCurrentPossessedCharacter(),
                        (short)(CollisionCategories.MASK_PLAYER | CollisionCategories.CATEGORY_FLOOR_4));
                break;
            default :
                changePlayerFixture(getGameMode().getCurrentController().getCurrentPossessedCharacter(),
                        (short)(CollisionCategories.MASK_PLAYER | CollisionCategories.CATEGORY_FLOOR_1));
                break;
        }

        // Update camera
        if(this.currentCamera != null) {
            this.currentCamera.setPositionSafe(getGameMode().getCurrentController()
                    .getCurrentPossessedCharacter()
                    .getWorldPosition());
        }

        world.step(1f / 60f, 6, 2);

        this.currentCamera.update();

        // Set view
        this.mapRenderer.setView(this.currentCamera);
    }

    @Override
    public void render() {

        this.mapRenderer.setCurrentPlayerCharacter(getGameMode().getCurrentController().getCurrentPossessedCharacter());
        this.mapRenderer.render(Gdx.graphics.getDeltaTime());

        if(this.getGameMode().getHud() != null) {
            this.getGameMode().getHud().draw(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        }

        debug.render(world, currentCamera.combined);
    }

    @Override
    public void dispose() {
        this.getGameMode().dispose();
        this.world.dispose();
    }

    @Override
    public IGameMode getGameMode() {
        return this.mode;
    }

    @Override
    public World getWorld() {
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

    private void tickActors(HashMap<String, IActor> actors, float delta) {
        for(Map.Entry<String, IActor> entry : actors.entrySet()) {
            IActor actor = entry.getValue();
            if(actor.canEverTick()) {
                actor.tick(delta);
                if(actor.getChildren() != null)
                    this.tickActors(actor.getChildren(), delta);
            }
        }

    }

    private void initPlayerBounds(IActor currentPlayer) {
        // Update player bounds

        Body playerBody = currentPlayer.getPhysicsBody();
        for(BoundingVolume bv : currentPlayer.getBoundingVolumes()) {

            if(playerBody != null) {
                BodyDef playerBodyDef = new BodyDef();

                playerBodyDef.fixedRotation = true;
                playerBodyDef.position.x = getGameMode().getCurrentController()
                        .getCurrentPossessedCharacter().getWorldPosition().x + bv.getRelativePosition().x;
                playerBodyDef.position.y = getGameMode().getCurrentController()
                        .getCurrentPossessedCharacter().getWorldPosition().y + bv.getRelativePosition().y;

                playerBody = world.createBody(playerBodyDef);
                playerBody.setUserData(getGameMode().getCurrentController().getCurrentPossessedCharacter());


                if (bv.isStatic()) {
                    playerBodyDef.type = BodyDef.BodyType.StaticBody;
                } else {
                    playerBodyDef.type = BodyDef.BodyType.DynamicBody;
                }
            }

            FixtureDef blockingBounds = new FixtureDef();

            blockingBounds.density = bv.getDensity();
            blockingBounds.friction = bv.getFriction();
            blockingBounds.shape = bv.getShape();
            blockingBounds.filter.categoryBits = bv.getCollisionCategory();
            blockingBounds.filter.maskBits = bv.getCollisionMask();

            playerBody.createFixture(blockingBounds);
        }
        currentPlayer.setPhysicsBody(playerBody);
    }

    private void changePlayerFixture(IActor currentPlayer, short playerMask) {
        PolygonShape shape = new PolygonShape();

        Fixture fix = playerBody.getFixtureList().get(0);
        playerBody.destroyFixture(fix);

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
        if(this.getGameMode().getHud().getInputProcessor() != null)
            inputProcessor.addProcessor(this.getGameMode().getHud().getInputProcessor());

        // Set gesture input processor
        if (getGameMode().getCurrentController().getGestureListener() != null)
            inputProcessor.addProcessor(new GestureDetector(
                    getGameMode().getCurrentController().getGestureListener()));

        // Set standard input processor from controller
        if(getGameMode().getCurrentController().getInputProcessor() != null)
            inputProcessor.addProcessor(
                    getGameMode().getCurrentController().getInputProcessor());

        Gdx.input.setInputProcessor(inputProcessor);
    }

    private void initMapActors() {
        this.getGameMode().getGameState().getCurrentLevel().initActors();
    }

    private void initMapWalls () {
        this.getGameMode().getGameState().getCurrentLevel().initWalls();
    }
}
