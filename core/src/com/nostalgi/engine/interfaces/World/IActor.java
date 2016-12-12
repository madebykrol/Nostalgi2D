package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ksdkrol on 2016-07-05.
 *
 * Actors are the main part of a game.
 */
public interface IActor extends IWorldObject {


    ArrayList<IComponent> getComponents();


    /**
     * Return the parent to this actor.
     * @return
     */
    IActor getParent();

    /**
     * Set the parent of this actor.
     * @param parent
     */
    void setParent(IActor parent);

    /**
     * Get the children of this actor
     * @return
     */
    HashMap<String, IActor> getChildren();

    /**
     * Get a specific child.
     * @param name
     * @return
     */
    IActor getChild(String name);

    /**
     * Add a set of children to this actror
     * @param children
     */
    void addChildren(IActor[] children);

    /**
     * Add a set of children to this actror
     * @param children
     */
    void addChildren(HashMap<String, IActor> children);

    /**
     * Add a child to this actor
     * @param actor
     */
    void addChild(IActor actor);

    /**
     * Returns whether or not this actor can tick.
     * one tick occurs on every frame.
     * @return
     */
    boolean canEverTick();

    /**
     * If this actor is allowed to tick, this method will be called once per frame
     * @param delta
     */
    void tick(float delta);

    /**
     * Get current running animation
     */
    Animation getCurrentAnimation();

    /**
     * Set current running animation
     * @param animation
     */
    void setCurrentAnimation(Animation animation);

    /**
     * Set current running animation by state.
     * @param state
     */
    void setCurrentAnimation(int state);

    /**
     * Add an animation to the set of animations.
     * @param state
     * @param animation
     */
    void addAnimation(int state, Animation animation);

    /**
     * Get the animation for a specific state
     * @param state
     * @return
     */
    Animation getAnimation(int state);

    /**
     *
     * @return
     */
    boolean isAnimated();

    /**
     * Returns whether this actor needs tick on its bounding volume fixtures.
     * This usually occurs when the player changes floor in a dungeon or on the map.
     * @return
     */
    boolean fixtureNeedsUpdate();

    /**
     * Set wheter or not this actors bounding volumes needs tick.
     * @param update
     * @return
     */
    boolean fixtureNeedsUpdate(boolean update);

    /**
     * Called for an actor when a overlap event begins.
     * This occurs when two volumes begin intersecting.
     *
     * The method reports with a Actor, the one that is intersecting, and two fixtures:
     * Instigator is the volume of the actor that is intersecting
     * Target is this actors volume that has been intersected.
     *
     * For example this can be used to determine if for example this actors head has been hit with another actors sword
     * Or the other way around, if you want to figure out if you hit the head of another player with your sword or shield.
     *
     * @param instigator
     * @param instigatorFixture
     * @param targetFixture
     */
    void onOverlapBegin(IActor instigator, Fixture instigatorFixture, Fixture targetFixture);

    /**
     * Called when an overlap event ends.
     * This occurs when two volumes stop intersecting
     *
     * The method reports with the same parameters as its counterpart onOverlapBegin.
     * @param instigator
     * @param instigatorFixture
     * @param targetFixture
     */
    void onOverlapEnd(IActor instigator, Fixture instigatorFixture, Fixture targetFixture);


    /**
     * Called as soon as actor is ready and added to the level.
     * The actor has not yet been rendered at this point, but it's part of the scene and can begin to tick.
     */
    void postSpawn();

    /**
     * Called as soon as the actor has been removed from the scene.
     * The actor will no longer be rendering.
     */
    void onDespawn();

    void destroy();

    void addOnDestroyListener();

    /**
     * Returns true if this actor is replicated between the server and clients
     * @return
     */
    boolean isReplicated();

    /**
     * Return if this actor is supposed to be part of the physics simulation.
     * @return
     */
    boolean physicsSimulated();

    /**
     * Set / Get if this actor is supposed to be part of the physics simulation.
     * @param simulated
     * @return
     */
    boolean physicsSimulated(boolean simulated);

    boolean blockOnCollision(IActor actor, Contact contact);

    /**
     * Apply force to actor with angle and velocity.
     *
     * @param force
     * @param targetPoint
     */
    void applyForce(Vector2 force, Vector2 targetPoint);

    /**
     * Apply radial force to actor.
     * Commonly used to simulate an explosion in the vincinity of an actor.
     * @param origin
     * @param force
     * @param falloffRadius
     */
    void applyForceFromOrigin(Vector2 origin, float force, float falloffRadius);


    /**
     * Gets / Sets if this actor should be created with a physics body when spawned.
     * @return
     */
    boolean shouldCreatePhysicsBody();

    /**
     * Gets / Sets if this actor should be created with a physics body when spawned.
     * @param shouldCreate
     * @return
     */
    boolean shouldCreatePhysicsBody(boolean shouldCreate);

}
