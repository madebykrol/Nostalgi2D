package com.nostalgi.engine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nostalgi.engine.interfaces.World.IActor;

import java.util.HashMap;

/**
 * Created by Kristoffer on 2016-07-15.
 */
public abstract class BaseActor implements IActor {

    protected IActor parent;
    protected HashMap<String, IActor> children;
    protected Body boundingBody;

    protected Vector2 position;

    protected Body physicsBody;

    protected Animation currentAnimation;
    protected HashMap<Integer, Animation> animations = new HashMap<Integer, Animation>();
    protected String name = "BaseActor"+this.hashCode();


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
    public void setChildren(IActor[] children) {

    }
    public void setChildren(HashMap<String, IActor> children) {
        this.children = children;
    }


    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }


    @Override
    public void setPhysicsBody(Body body) {
        this.physicsBody = body;
    }

    @Override
    public Body getPhysicsBody() {
        return this.physicsBody;
    }

    @Override
    public void onOverlapBegin(IActor overlapper) {

    }

    @Override
    public void onOverlapEnd(IActor overlapper) {

    }

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
    public void tick() {

    }

}
