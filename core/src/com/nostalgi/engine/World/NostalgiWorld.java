package com.nostalgi.engine.World;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.Exceptions.FailedToSpawnActorException;
import com.nostalgi.engine.LevelCameraBounds;
import com.nostalgi.engine.NostalgiRenderer;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.World.IWall;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.interfaces.World.IWorldObject;
import com.nostalgi.engine.physics.BoundingVolume;
import com.nostalgi.engine.physics.CollisionCategories;
import com.nostalgi.engine.physics.TraceHit;
import java.util.ArrayList;

/**
 * Created by Kristoffer on 2016-07-23.
 */
public class NostalgiWorld implements IWorld {

    private World world;
    private IGameMode gameMode;

    private float timeStep = 1f / 60f;
    private int velocityIterations = 6;
    private int positionIterations = 2;

    private OrthographicCamera camera;

    private NostalgiRenderer renderer;

    private int worldBoundsLeft, worldBoundsRight, worldBoundsBottom, worldBoundsTop;

    private float camViewportHalfWidth = 0;
    private float camViewportHalfHeight = 0;

    public NostalgiWorld(World world, NostalgiRenderer mapRenderer, OrthographicCamera camera) {
        this.world = world;
        this.camera = camera;
        this.renderer = mapRenderer;
        world.setContactListener(initializeCollisionDetectionObservers());
    }


    protected ContactListener initializeCollisionDetectionObservers() {

        return new ContactListener() {
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
        };

    }

    /**
     * @inheritDoc
     */
    @Override
    public IGameMode getGameMode() {
        return this.gameMode;
    }

    @Override
    public void setGameMode(IGameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * @inheritDoc
     */
    @Override
    public IGameState getGameState() {
        return this.gameMode.getGameState();
    }

    /**
     * @inheritDoc
     */
    @Override
    public World getPhysicsWorld() {
       return world;
    }

    /**
     * @inheritDoc
     */
    @Override
    public float getTimeStep() {
        return timeStep;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setTimeStep(float timeStep) {
        this.timeStep = timeStep;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getVelocityIterations() {
        return velocityIterations;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setVelocityIterations(int velocityIterations) {
        this.velocityIterations = velocityIterations;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getPositionIterations() {
        return positionIterations;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPositionIterations(int positionIterations) {
        this.positionIterations = positionIterations;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void tick() {
        world.step(timeStep, velocityIterations, positionIterations);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Body createBody(IActor actor) {
        return createBody(actor, 1);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Body createBody(IActor actor, float unitScale) {
        // Update player bounds

        Body actorBody = actor.getPhysicsBody();
        if(actorBody == null) {
            BodyDef playerBodyDef = new BodyDef();

            playerBodyDef.fixedRotation = true;
            playerBodyDef.position.x = actor.getWorldPosition().x / unitScale;
            playerBodyDef.position.y = actor.getWorldPosition().y / unitScale;

            if (actor.isStatic()) {
                playerBodyDef.type = BodyDef.BodyType.StaticBody;
            } else {
                playerBodyDef.type = BodyDef.BodyType.DynamicBody;
            }

            actorBody = world.createBody(playerBodyDef);
            actorBody.setUserData(actor);
        }
        int bvI = 0;
        for(BoundingVolume bv : actor.getBoundingVolumes()) {

            FixtureDef blockingBounds = new FixtureDef();

            blockingBounds.density = actor.getDensity();
            blockingBounds.friction = actor.getFriction();
            blockingBounds.shape = bv.getShape();
            blockingBounds.filter.categoryBits = bv.getCollisionCategory();
            blockingBounds.isSensor = actor.isSensor();
            if(bvI == 0) {
                blockingBounds.filter.maskBits = (short) (bv.getCollisionMask() | CollisionCategories.floorFromInt(actor.getFloorLevel()));
            } else {
                blockingBounds.filter.maskBits = bv.getCollisionMask();
            }

            actorBody.createFixture(blockingBounds);
            bvI++;
        }
        actor.setPhysicsBody(actorBody);

        return actorBody;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Body createBody(IWall wall) {
        return createBody(wall, 1);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Body createBody(IWall wall, float unitScale) {
        // Set def.
        BodyDef shapeDef = new BodyDef();
        // go through our bounding blocks
        float[] vertices = wall.getVertices();

        short floor = CollisionCategories.CATEGORY_NIL;
        if(wall.isOnFloor(1)) {
            floor = (short)(floor | CollisionCategories.CATEGORY_FLOOR_1);
        } if(wall.isOnFloor(2)) {
            floor = (short)(floor | CollisionCategories.CATEGORY_FLOOR_2);
        } if(wall.isOnFloor(3)) {
            floor = (short)(floor | CollisionCategories.CATEGORY_FLOOR_3);
        } if(wall.isOnFloor(4)) {
            floor = (short)(floor | CollisionCategories.CATEGORY_FLOOR_4);
        }

        for(int i = 0; i < vertices.length; i++) {
            vertices[i] /=unitScale;
        }

        PolygonShape boundShape = new PolygonShape();
        boundShape.set(vertices);
        shapeDef.position.set(wall.getPosition().x, wall.getPosition().y);
        shapeDef.type = BodyDef.BodyType.StaticBody;

        Body b = world.createBody(shapeDef);

        b.setUserData(wall);

        FixtureDef blockingBounds = new FixtureDef();

        blockingBounds.density = 100f;
        blockingBounds.friction = 0f;
        blockingBounds.shape = boundShape;
        blockingBounds.filter.categoryBits = floor;
        blockingBounds.filter.maskBits = CollisionCategories.CATEGORY_PLAYER;
        b.createFixture(blockingBounds);

        return b;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateBody(IActor actor) {
        Body playerBody =  actor.getPhysicsBody();
        if(playerBody == null) {
            createBody(actor);
        } else {

            for (Fixture fix : playerBody.getFixtureList()) {
                playerBody.destroyFixture(fix);
            }
            int bvI = 0;
            for (BoundingVolume bv : actor.getBoundingVolumes()) {
                FixtureDef blockingBounds = new FixtureDef();

                blockingBounds.density = actor.getDensity();
                blockingBounds.friction = actor.getFriction();
                blockingBounds.shape = bv.getShape();
                blockingBounds.filter.categoryBits = bv.getCollisionCategory();
                if(bvI == 0) {
                    blockingBounds.filter.maskBits = (short)(bv.getCollisionMask() | CollisionCategories.floorFromInt(actor.getFloorLevel()));
                } else {
                    blockingBounds.filter.maskBits = bv.getCollisionMask();
                }
                playerBody.createFixture(blockingBounds);
                bvI++;
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateBody(IActor actor, float unitScale) {
        updateBody(actor, 1);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateBody(IWall wall) {
       updateBody(wall, 1);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateBody(IWall wall, float unitScale) {
        Body wallBody = wall.getPhysicsBody();

        if(wallBody == null) {
            createBody(wall, unitScale);
        } else {
            for(Fixture fix : wallBody.getFixtureList()) {
                wallBody.destroyFixture(fix);
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean destroyBody(Body body) {
        return false;
    }


    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<TraceHit> rayTrace(Vector2 origin, float direction, float distance, ArrayList<Class> filter, boolean stopAtWall) {
        Vector2 target = new Vector2(
                (float)Math.cos(direction * Math.PI / 180) * distance,
                (float)Math.sin(direction * Math.PI / 180) * distance
        );
        target.add(origin);

        return rayTrace(origin, target, filter, stopAtWall);
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<TraceHit> rayTrace(Vector2 origin, Vector2 target, final ArrayList<Class> filter, final boolean stopAtWall) {
        final ArrayList<TraceHit>  objects = new ArrayList<TraceHit>();

        world.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                Object uO = fixture.getBody().getUserData();
                if(uO != null && uO instanceof IWorldObject) {
                    if(!(filter.contains(uO.getClass()) || filter.contains(uO.getClass().getSuperclass()))) {
                        TraceHit hit = new TraceHit();
                        hit.object = (IWorldObject) uO;

                        hit.hitNormal = normal;
                        hit.hitNormal.add(point);

                        hit.hitPoint = point;
                        hit.fraction = fraction;

                        hit.fixture = fixture;

                        objects.add(hit);

                        if (stopAtWall && uO instanceof IWall) {
                            return 0;
                        }
                    }
                }
                return 1;
            }
        }, origin, target);

        return objects;
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<IActor> actorsCloseToLocation(Vector2 position, float distance) {
        float factor = 2f;
        if(distance <= 0) {
            factor = 0.5f;
        }

        float x1 = (position.x) + (distance / factor);
        float x2 = (position.x) - (distance / factor);

        float y1 = (position.y) + (distance / factor);
        float y2 = (position.y) - (distance / factor);

        final ArrayList<IActor> actors = new ArrayList<IActor>();

        world.QueryAABB(new QueryCallback() {
              @Override
              public boolean reportFixture(Fixture fixture) {
                  Object o = fixture.getBody().getUserData();
                  if (o instanceof IActor) {
                      actors.add((IActor) o);
                  }
                  return true;
              }
        }, x2, y2, x1, y1);

        return actors;
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends IActor> T spawnActor(Class<T> type, String name, boolean physicsBound, Vector2 spawnPoint)
            throws FailedToSpawnActorException
    {
        return spawnActor(type, name, physicsBound, spawnPoint, null, null);
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends IActor> T spawnActor(Class<T> type, String name, boolean physicsBound, Vector2 spawnPoint, IActor owner, ICharacter instigator)
            throws FailedToSpawnActorException
    {
        IActor a;
        try {
            a = type.getConstructor(IWorld.class).newInstance(this);
        } catch (NoSuchMethodException e) {
            try {
                a = type.newInstance();
            } catch(Exception e1) {
                throw new FailedToSpawnActorException(e1);
            }
        } catch (Exception e2) {
            throw  new FailedToSpawnActorException(e2);
        }

        a.setPosition(spawnPoint);
        a.setName(name);

        renderer.getCurrentLevel().addActor(a);

        if(physicsBound)
            createBody(a);

        return (T)a;
    }

    @Override
    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public OrthographicCamera getCamera() {
        return this.camera;
    }

    @Override
    public Vector2 project(Vector2 vector) {
        Vector3 v = camera.project(new Vector3(vector.x, vector.y, 0f));
        return new Vector2(v.x, v.y);
    }

    @Override
    public Vector2 unproject(Vector2 vector) {
        Vector3 v = camera.unproject(new Vector3(vector.x, vector.y, 0f));
        return new Vector2(v.x, v.y);
    }


    @Override
    public void setWorldBounds(LevelCameraBounds bounds) {
        this.setWorldBounds(bounds.left, bounds.bottom, bounds.right, bounds.top);
    }

    @Override
    public void setWorldBounds(int left, int bottom, int width, int height) {

        this.worldBoundsLeft = left;
        this.worldBoundsBottom = bottom;

        this.worldBoundsTop = bottom + height;
        this.worldBoundsRight = left + width;

        camViewportHalfWidth = this.getCamera().viewportWidth  * 0.5f;
        camViewportHalfHeight = this.getCamera().viewportHeight * 0.5f;
    }

    @Override
    public void setCameraPositionSafe(Vector2 position) {
        setCameraPositionSafe(position.x, position.y);
    }

    @Override
    public void setCameraPositionSafe(float x, float y) {

        // Clamp x
        float x1 = MathUtils.clamp(x,
                worldBoundsLeft + (camViewportHalfWidth*this.camera.zoom),
                worldBoundsRight - (camViewportHalfWidth*this.camera.zoom));

        // Clamp y
        float y1 = MathUtils.clamp(y,
                worldBoundsBottom + (camViewportHalfHeight*this.camera.zoom),
                worldBoundsTop - (camViewportHalfHeight*this.camera.zoom));

        // Set these positions
        this.camera.position.set(x1, y1, 0);
        this.camera.update();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void dispose() {
        world.dispose();
    }
}
