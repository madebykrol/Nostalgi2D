package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.physics.BoundingVolume;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ksdkrol on 2016-07-05.
 *
 * Actors are the main part of a game.
 */
public interface IActor extends IWorldObject {

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
     * Returns whether this actor needs update on its bounding volume fixtures.
     * This usually occurs when the player changes floor in a dungeon or on the map.
     * @return
     */
    boolean fixtureNeedsUpdate();

    /**
     * Set wheter or not this actors bounding volumes needs update.
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
     * Last chance for actor to add bounding volumes before its being spawned to the world.
     * This method is called right before createBody
     *
     */
    void createPhysicsBody();
    void postDespawned();

    void destroy();

    void addOnDestroyListener();

    boolean isReplicated();

    boolean physicsSimulated();
    boolean physicsSimulated(boolean simulated);
}
