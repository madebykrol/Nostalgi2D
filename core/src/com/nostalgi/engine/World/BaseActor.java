package com.nostalgi.engine.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.physics.BoundingVolume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Kristoffer on 2016-07-15.
 */
public abstract class BaseActor implements IActor {

    private int floor = 1;

    private IActor parent;
    private HashMap<String, IActor> children = new HashMap<String, IActor>();

    private Vector2 position;

    private BoundingVolume boundingVolume;

    private Animation currentAnimation;
    private HashMap<Integer, Animation> animations = new HashMap<Integer, Animation>();
    private String name = "Actor"+this.hashCode();

    private World world;

    @Override
    public IActor getParent() {
        return this.parent;
    }
    public void setParent(IActor parent) {
        this.parent = parent;
    }

    public HashMap<String, IActor> getChildren() {
        return this.children;
    }
    public IActor getChild(String name) {
        return this.children.get(name);
    }

    public void addChildren(IActor[] children) {
        for (IActor aChildren : children) {
            this.addChild(aChildren);
        }
    }

    public void addChildren(HashMap<String, IActor> children) {

        for (Object o : children.entrySet()) {
            IActor actor = (IActor) o;
            this.addChild(actor);
        }
    }

    public void addChild(IActor actor) {
        this.children.put(actor.getName(), actor);
        actor.setParent(this);
    }

    @Override
    public Vector2 getWorldPosition() {
        return this.position;
    }

    public Vector2 getPosition() {

        // This needs to be cached for optimization.

        if(this.parent != null) {
            Vector2 worldPosition = new Vector2();
            worldPosition.x = this.position.x + this.parent.getPosition().x;
            worldPosition.y = this.position.y + this.parent.getPosition().y;

            return worldPosition;
        } else {
            return this.position;
        }
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }


    @Override
    public void setBoundingVolume(BoundingVolume volume) {
        this.boundingVolume = volume;
    }

    @Override
    public BoundingVolume getBoundingVolume() {
        return this.boundingVolume;
    }

    @Override
    public void onOverlapBegin(IActor overlapper) {}

    @Override
    public void onOverlapEnd(IActor overlapper) { }

    @Override
    public com.badlogic.gdx.graphics.g2d.Animation getCurrentAnimation() {
        return this.currentAnimation;
    }

    @Override
    public void setCurrentAnimation(com.badlogic.gdx.graphics.g2d.Animation animation) {
        this.currentAnimation = animation;
    }

    @Override
    public void setCurrentAnimation(int state) {
        if(animations.containsKey(state))
            this.currentAnimation = animations.get(state);

    }

    @Override
    public void addAnimation(int state, com.badlogic.gdx.graphics.g2d.Animation animation) {
        this.animations.put(state, animation);
    }

    @Override
    public com.badlogic.gdx.graphics.g2d.Animation getAnimation(int state) {
        if(animations.containsKey(state))
            return animations.get(state);

        return null;
    }

    @Override
    public boolean isAnimated() {
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean canEverTick() {
        return false;
    }

    @Override
    public void tick(float delta) {}

    @Override
    public int getFloorLevel() {
        return this.floor;
    }

    @Override
    public void setFloorLevel(int floor) {
        this.floor = floor;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void draw(Batch batch, float timeElapsed) {

    }
}
