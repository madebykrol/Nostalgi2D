package com.nostalgi.engine.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.Annotations.NostalgiField;
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

import java.lang.reflect.Field;
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


    private static final String SENSOR = "IsSensor";
    private static final String TYPE = "Type";

    private static final String COLLISION_CATEGORY = "CollisionCategory";
    private static final String COLLISION_MASK = "CollisionMask";


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
                        actorA.onOverlapBegin(actorB, b, a);
                        actorB.onOverlapBegin(actorA, a, b);
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
                        actorA.onOverlapEnd(actorB, b, a);
                        actorB.onOverlapEnd(actorA, a, b);
                    }
                } catch (ClassCastException e) {

                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                try {
                    IActor a = (IActor) contact.getFixtureA().getBody().getUserData();
                    IActor b = (IActor) contact.getFixtureB().getBody().getUserData();
                    contact.setEnabled(a.blockOnCollision(b, contact) && b.blockOnCollision(a, contact));
                } catch (ClassCastException e) {

                }
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
            blockingBounds.isSensor = bv.isSensor();

            if(bvI == 0) {
                blockingBounds.filter.maskBits = (short) (bv.getCollisionMask() | CollisionCategories.floorFromInt(actor.getFloorLevel()));
            } else {
                blockingBounds.filter.maskBits = bv.getCollisionMask();
            }

            Fixture  f = actorBody.createFixture(blockingBounds);
            f.setUserData(bv);
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
        updateBody(actor, 1);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateBody(IActor actor, float unitScale) {

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
                Fixture f = playerBody.createFixture(blockingBounds);
                f.setUserData(bv);
                bvI++;
            }
        }
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
    public <T extends IActor> T spawnActor(Class<T> type, String name, boolean physicsBound, Vector2 spawnPoint, IActor parent, ICharacter instigator)
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

        if(parent == null) {
            renderer.getCurrentLevel().addActor(a);
        } else {
            parent.addChild(a);
        }

        if(physicsBound) {
            createBody(a);
        }

        a.createPhysicsBody();
        a.onSpawn();
        return (T)a;
    }

    @Override
    public <T extends IActor> T spawnActor(Class<T> type, MapObject mapObject, IActor parent, float unitScale) {
        try {
            IActor actor = type.newInstance();

            // Set base class fields
            setFields(actor, mapObject, type.getSuperclass().getDeclaredFields());

            // Set class fields.
            setFields(actor,mapObject, type.getDeclaredFields());

            float[] vertices;
            Vector2 position = new Vector2(0,0);
            BoundingVolume bv =  new BoundingVolume();
            if(mapObject instanceof RectangleMapObject) {
                Rectangle obj = ((RectangleMapObject) mapObject).getRectangle();

                vertices = rectangleToVertices(0, 0, obj.getWidth(), obj.getHeight());
                bv = createBoundingVolume(mapObject, vertices, unitScale);
                position = new Vector2(obj.getX()/unitScale, obj.getY()/unitScale);
            } else if(mapObject instanceof PolygonMapObject) {
                PolygonMapObject obj = (PolygonMapObject) mapObject;
                vertices = obj.getPolygon().getVertices();
                position = new Vector2(obj.getPolygon().getX()/unitScale, obj.getPolygon().getY()/unitScale);
                bv = createBoundingVolume(mapObject, vertices, unitScale);
            } else if(mapObject instanceof CircleMapObject) {

                CircleMapObject obj = (CircleMapObject)mapObject;
                position = new Vector2(obj.getCircle().x/unitScale, obj.getCircle().y/unitScale);
                CircleShape circle = new CircleShape();
                circle.setPosition(position);
                circle.setRadius(obj.getCircle().radius);

                bv = createBoundingVolume(obj, circle);
            } else  if (mapObject instanceof EllipseMapObject) {
                EllipseMapObject obj = (EllipseMapObject)mapObject;
                position = new Vector2(obj.getEllipse().x/unitScale, obj.getEllipse().y/unitScale);
                CircleShape circle = new CircleShape();

                float radius = obj.getEllipse().width  / 2;

                circle.setPosition(new Vector2((obj.getEllipse().x + radius) / unitScale, (obj.getEllipse().y + radius) / unitScale));
                circle.setRadius(radius/unitScale);
                bv = createBoundingVolume(obj, circle);
            }

            bv.setVolumeId("base");

            actor.setPosition(position);
            actor.setBoundingVolume(bv);

            actor.setName(mapObject.getName());

            actor.createPhysicsBody();
            createBody(actor);
            return (T)actor;
        } catch(IllegalAccessException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
    }

    @Override
    public IWall createWall(MapObject object, Vector2 mapOrigin, float unitScale) {
        String f = getObjectProperty(object, "Floor");
        int[] floors = new int[]{1};
        if(f != null) {
            String[] sFloors = f.split(",");
            floors = new int[sFloors.length];
            for(int i = 0; i < sFloors.length; i++) {
                floors[i] = Integer.parseInt(sFloors[i]);
            }
        }

        float[] vertices = new float[0];
        Vector2 position = new Vector2(0,0);

        if(object instanceof RectangleMapObject) {
            Rectangle obj = ((RectangleMapObject) object).getRectangle();

            vertices = rectangleToVertices(0, 0, obj.getWidth(), obj.getHeight());
            position = new Vector2(obj.getX()/unitScale+mapOrigin.x, obj.getY()/unitScale+mapOrigin.y);
        } else if( object instanceof PolygonMapObject) {
            PolygonMapObject obj = (PolygonMapObject) object;

            vertices = obj.getPolygon().getVertices();
            position = new Vector2(obj.getPolygon().getX()/unitScale+mapOrigin.x, obj.getPolygon().getY()/unitScale+mapOrigin.y);
        }

        IWall wall = new Wall(floors, position, vertices);

        createBody(wall, unitScale);

        return wall;
    }

    @Override
    public IWall createWall(MapObject object, Vector2 mapOrigin) {
        return createWall(object, mapOrigin, 32f);
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

    protected String getObjectProperty(MapObject object, String prop) {
        Object p = object.getProperties().get(prop);

        if(p != null) {
            return (String)p;
        }
        return null;
    }

    protected float[] rectangleToVertices(float x, float y, float width,
                                          float height) {
        float[] result = new float[8];
        result[0] = x;
        result[1] = y;

        result[2] = x + width;
        result[3] = y;

        result[4] = x + width;
        result[5] = y + height;
        result[6] = x;
        result[7] = y + height;

        return result;
    }

    protected void setFields(IActor actor, MapObject object, Field[] fields) throws IllegalAccessException {
        for(Field field : fields) {
            field.setAccessible(true);
            NostalgiField annotation = field.getAnnotation(NostalgiField.class);
            if (annotation != null && annotation.fromEditor()) {
                String fieldName = annotation.fieldName();
                if (fieldName.isEmpty()) {
                    fieldName = field.getName();
                }

                String propertyValue = getObjectProperty(object, fieldName);
                if (propertyValue != null && !propertyValue.isEmpty()) {
                    if (field.getType() == int.class) {
                        field.set(actor, Integer.parseInt(propertyValue));
                    } else if (field.getType() == float.class) {
                        field.set(actor, Float.parseFloat(propertyValue));
                    } else if (field.getType() == boolean.class) {
                        field.set(actor, Boolean.parseBoolean(propertyValue));
                    } else if (field.getType() == String.class) {
                        field.set(actor, propertyValue);
                    }
                }
            }
            field.setAccessible(false);
        }
    }

    protected BoundingVolume createBoundingVolume(MapObject object, Shape boundShape) {
        BoundingVolume bv = new BoundingVolume();
        bv.setShape(boundShape);

        // set is Sensor
        String isSensor = getObjectProperty(object, SENSOR);
        if(isSensor != null) {
            bv.isSensor(Boolean.parseBoolean(isSensor));
        }

        // set collision category
        String collisionCategory = getObjectProperty(object, COLLISION_CATEGORY);
        short category;
        if (collisionCategory != null) {
            try {
                category  = Short.parseShort(collisionCategory);
            } catch (NumberFormatException e) {
                category = CollisionCategories.categoryFromString(collisionCategory);
            }
            bv.setCollisionCategory(category);
        }

        // set Collision mask
        String collisionMask = getObjectProperty(object, COLLISION_MASK);
        short mask;
        if (collisionMask != null) {
            try {
                mask = Short.parseShort(collisionMask);
            } catch (NumberFormatException e) {
                mask = CollisionCategories.maskFromString(collisionMask);
            }
            bv.setCollisionMask(mask);
        }

        return bv;
    }

    protected BoundingVolume createBoundingVolume(MapObject object, float[] vertices, float unitScale) {

        PolygonShape boundShape = new PolygonShape();

        for(int i = 0; i < vertices.length; i++) {
            vertices[i] /= unitScale;
        }

        boundShape.set(vertices);

        return createBoundingVolume(object, boundShape);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void dispose() {
        world.dispose();
    }
}
