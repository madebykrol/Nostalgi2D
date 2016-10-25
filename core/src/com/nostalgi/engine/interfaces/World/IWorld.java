package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IGameState;

import java.util.ArrayList;

/**
 * Physics World abstraction.
 *
 * Created by Kristoffer on 2016-07-23.
 */
public interface IWorld{

    /**
     * get the current running game mode
     * @return IGameMode
     */
    IGameMode getGameMode();

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
     * Step the physics world.
     * @param timeStep
     * @param velocityIterations
     * @param positionIterations
     */
    void step(float timeStep, int velocityIterations, int positionIterations);

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
     * Dispose of the current physics world state. Typically called when the game is shutting down.
     */
    void dispose();
}
