package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.Exceptions.FailedToSpawnActorException;
import com.nostalgi.engine.LevelCameraBounds;
import com.nostalgi.engine.Navigation.INavigationSystem;
import com.nostalgi.engine.World.Audio.ISoundSystem;
import com.nostalgi.engine.ITimeManagementSystem;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.physics.ILightingSystem;
import com.nostalgi.engine.physics.TraceHit;

import java.util.ArrayList;

/**
 * World abstraction.
 *
 * Created by Kristoffer on 2016-07-23.
 */
public interface IWorld {

    OrthographicCamera getCurrentCamera();
    void setCurrentCamera(OrthographicCamera camera);

    IController getCurrentController();
    void setCurrentController(IController controller);

    IController getController(int player);
    void addController(IController controller);
    void addAIController(IController controller);

    ArrayList<IController> getControllers();
    ArrayList<IController> getAIController();

    INavigationSystem getNavigationSystem();

    /**
     * get the current running game mode
     * @return IGameMode
     */
    IGameMode getGameMode();

    /**
     * Set game mode.
     * @param gameMode
     */
    void setGameMode(IGameMode gameMode);

    /**
     * Get the current gamestate
     * @return IGameState
     */
    IGameState getGameState();

    /**
     * Get current physics world.
     * @return World
     */
    World getPhysicsWorld();

    /**
     * get timestep
     * @return
     */
    float getTimeStep();

    /**
     * Set timestep
     * @param timeStep
     */
    void setTimeStep(float timeStep);

    /**
     * Get velocity iterations
     * @return
     */
    int getVelocityIterations();

    /**
     * set velocity iterations
     * @param velocityIterations
     */

    void setVelocityIterations(int velocityIterations);

    /**
     * get position iterations
     * @return
     */
    int getPositionIterations();

    /**
     * set position iterations
     * @param positionIterations
     */
    void setPositionIterations(int positionIterations);

    /**
     * Tick the physics world.
     */
    void tick();

    /**
     * Create a physics body for a IActor with a fixed unit scale of 1
     * @param actor
     * @return
     */
    Body createBody(IActor actor);

    /**
     * Create a physics body for a IActor with a provided unit scale.
     * @param actor
     * @param unitScale
     * @return
     */
    Body createBody(IActor actor, float unitScale);

    /**
     * Create a physics body for a IWall with a fixed unit scale of 1
     * @param wall
     * @return
     */
    Body createBody(IWall wall);

    /**
     * Create a physics body of a IWall with a provided unit scale.
     * @param wall
     * @param unitScale
     * @return
     */
    Body createBody(IWall wall, float unitScale);

    /**
     * Update the physics body for an actor by removing any fixtures on the body and recreating them
     * This is used when for instance a actors collision categories and masks has changed.
     * Usually this is a effect by switching floors, but can also be used for other game mechanics.
     *
     * This method sets a default unit scale of 1
     * @param actor
     */
    void updateBody(IActor actor);

    /**
     * Update the physics body for an actor by removing any fixtures on the body and recreating them
     * This is used when for instance a actors collision categories and masks has changed.
     * Usually this is a effect by switching floors, but can also be used for other game mechanics.
     *
     * @param actor
     * @param unitScale
     */
    void updateBody(IActor actor, float unitScale);

    /**
     * Update the physics body for a wall by removing any fixtures on the body and recreating them
     * This is used when a wall changes is physical state. Usually this is used when for example
     * a hidden path is revealed by blowing a hole in a wall, the sprite will change and the wall
     * will no longer block a player.
     *
     * this method sets a default unit scale of 1
     * @param wall
     */
    void updateBody(IWall wall);

    /**
     * Update the physics body for a wall by removing any fixtures on the body and recreating them
     * This is used when a wall changes is physical state. Usually this is used when for example
     * a hidden path is revealed by blowing a hole in a wall, the sprite will change and the wall
     * will no longer block a player.
     *
     * @param wall
     * @param unitScale
     */
    void updateBody(IWall wall, float unitScale);

    /**
     * Destroying a physics body.
     * This is usually done after a actor / wall has been destroyed commonly caused by a despawn
     * after for example a death event.
     *
     * @param body
     * @return
     */
    boolean destroyBody(Body body);

    /**
     * Send a ray from the origin travelling in the given direction in degrees for the given distance
     * returning a set of TraceHits
     *
     * The filter is a excluding filter, which means that if the trace hits a object of or direct descendant of class in filter it will be ignored
     *
     * if stopAtWalls is true, the trace will stop at the first wall it hits, returning that wall as the last hit in the stack
     * @param origin
     * @param direction
     * @param distance
     * @param filter
     * @param stopAtWall
     * @return
     */
    ArrayList<TraceHit> rayTrace(Vector2 origin, float direction, float distance, ArrayList<Class> filter, boolean stopAtWall);

    /**
     * Send a ray from the origin travelling to the given target
     * returning a set of TraceHits
     *
     * The filter is a excluding filter, which means that if the trace hits a object of or direct descendant of class in filter it will be ignored
     *
     * if stopAtWalls is true, the trace will stop at the first wall it hits, returning that wall as the last hit in the stack
     * @param origin
     * @param target
     * @param filter
     * @param stopAtWall
     * @return
     */
    ArrayList<TraceHit> rayTrace(Vector2 origin, Vector2 target, ArrayList<Class> filter, boolean stopAtWall);

    /**
     * Perform a AABB collision query on a world position with the center of the querying square is given
     * by the position parameter.
     * and the length of the edges is given by the distance.
     *
     * Intersecting actors are returned as a ArrayList, Walls are ignored.
     * @param position
     * @param distance
     * @return
     */
    ArrayList<IActor> actorsCloseToLocation(Vector2 position, float distance);

    /**
     * Spawn a new actor into the world.
     * Actors have to implement at least the IActor interface to be considered for spawning
     * @param name
     * @param physicsBound
     * @param spawnPoint
     * @param <T>
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @return
     */
    <T extends IActor> T  spawnActor(Class<T> type, String name, boolean physicsBound, Vector2 spawnPoint) throws FailedToSpawnActorException;
    <T extends IActor> T  spawnActor(Class<T> type, String name, boolean physicsBound, Vector2 spawnPoint, IActor parent, ICharacter instigator) throws FailedToSpawnActorException;

    <T extends IActor> T  spawnActor(Class<T> type, MapObject mapObject, IActor parent, float unitScale) throws FailedToSpawnActorException;

    IWall createWall(MapObject object, Vector2 mapOrigin, float unitScale);
    IWall createWall(MapObject object, Vector2 mapOrigin);

    /**
     * Set the current projecting camera.
     * @param camera
     */
    void setCamera(OrthographicCamera camera);


    /**
     * Get the current projecting camera.
     * @return
     */
    OrthographicCamera getCamera();

    Vector2 project(Vector2 vector);
    Vector2 unproject(Vector2 vector);

    /**
     * Set the world bounds. this will prevent the camera from panning out of the world.
     * @param bounds
     */
    void setWorldBounds(LevelCameraBounds bounds);

    /**
     * Set the world bounds. this will prevent the camera from panning out of the world.
     * @param left
     * @param bottom
     * @param width
     * @param height
     */
    void setWorldBounds(int left, int bottom, int width, int height);

    /**
     * Move the camera within the level bounds.
     * @param position
     */
    void setCameraPositionSafe(Vector2 position);

    /**
     * Move the camera within the level bounds.
     * @param x
     * @param y
     */
    void setCameraPositionSafe(float x, float y);

    /**
     * Apply lights to the scene.
     */
    void applyLight();

    /**
     *
     * @return
     */
    ILightingSystem getLightingSystem();

    /**
     *
     * @param lightingSystem
     */
    void setLightingSystem(ILightingSystem lightingSystem);

    void setSoundSystem(ISoundSystem soundSystem);

    ISoundSystem getSoundSystem();

    ITimeManagementSystem getTimeManagementSystem();

    /**
     * Dispose of the current physics world state. Typically called when the game is shutting down.
     */
    void dispose();
}
