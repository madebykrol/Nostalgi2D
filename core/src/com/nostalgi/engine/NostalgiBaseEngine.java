package com.nostalgi.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.States.Wall;
import com.nostalgi.engine.interfaces.IFollowCamera;
import com.nostalgi.engine.interfaces.IGameEngine;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.IGameState;
import com.nostalgi.engine.interfaces.IHud;
import com.nostalgi.engine.interfaces.IWall;
import com.nostalgi.engine.physics.CollisionCategories;
import com.nostalgi.render.NostalgiCamera;

import java.util.ArrayList;

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

    protected ArrayList<Body> walls = new ArrayList<Body>();
    protected Body playerBody;

    protected World world;
    private Box2DDebugRenderer debug;

    public NostalgiBaseEngine(Vector2 gravity) {
        world = new World(gravity, true);
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


        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (walls.contains(contact.getFixtureA().getBody())) {
                    if (playerBody.equals(contact.getFixtureB().getBody())) {
                        System.out.println("COLLISION DETECTED");
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                if (walls.contains(contact.getFixtureA().getBody())) {
                    if (playerBody.equals(contact.getFixtureB().getBody())) {

                    }
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
        this.getGameState().getPlayerCharacter().setPhysicsBody(playerBody);

        // Update terrain / map bounds

        updateTerrainAndMapBounds();

    }

    @Override
    public void update() {

        world.step(1f / 60f, 6, 2);
        // Update NPC / Monster bounds

        playerBody.setLinearVelocity(this.getGameState().getCurrentController().getCurrentPossessedCharacter().getVelocity());
        this.getGameState().getCurrentController().getCurrentPossessedCharacter().setPosition(new Vector2(playerBody.getPosition().x-0.5f, playerBody.getPosition().y-0.5f));

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
        this.mapRenderer.render();

        if(hud != null) {
            hud.draw(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        }

        //debug.render(world, currentCamera.combined);
    }

    @Override
    public void dispose() {
        this.hud.dispose();
        this.world.dispose();
        this.walls.clear();
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
            blockingBounds.filter.maskBits = CollisionCategories.MASK_PLAYER;
            blockingBounds.filter.categoryBits = CollisionCategories.CATEGORY_PLAYER;
            playerBody = world.createBody(playerBodyDef);
            playerBody.createFixture(blockingBounds);
        }
    }

    private void updateTerrainAndMapBounds() {
        // get bounds

        for(Body wall : walls) {
            world.destroyBody(wall);
        }

        walls.clear();

        ArrayList<Polygon> currentLevelBounds = getGameState().getCurrentLevel().getMapBounds();

        // Set def.
        BodyDef shapeDef = new BodyDef();
        // go through our bounding blocks
        for(Polygon shape : currentLevelBounds) {
            float[] vertices = shape.getVertices();

            for(int i = 0; i < vertices.length; i++) {
                vertices[i] /=32f;
            }

            if(vertices.length > 2) {
                addBoundingBox(shapeDef, vertices, new Vector2(shape.getX()/32f,shape.getY()/32f));
            }
        }
    }

    private void addBoundingBox(BodyDef shapeDef, float[] vertices, Vector2 pos) {

        PolygonShape boundShape = new PolygonShape();
        boundShape.set(vertices);
        shapeDef.position.set(pos.x, pos.y);
        shapeDef.type = BodyDef.BodyType.StaticBody;

        Body b = world.createBody(shapeDef);

        FixtureDef blockingBounds = new FixtureDef();

        blockingBounds.density = 100f;
        blockingBounds.friction = 0f;
        blockingBounds.shape = boundShape;
        blockingBounds.filter.categoryBits = CollisionCategories.CATEGORY_WALLS;
        blockingBounds.filter.maskBits = CollisionCategories.MASK_SCENERY;
        b.createFixture(blockingBounds);

        walls.add(b);
    }
}
