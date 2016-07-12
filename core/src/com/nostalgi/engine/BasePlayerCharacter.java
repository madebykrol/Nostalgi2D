package com.nostalgi.engine;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nostalgi.engine.States.AnimationStates;
import com.nostalgi.engine.interfaces.Factories.IAnimationFactory;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.World.IItem;

import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class BasePlayerCharacter implements ICharacter {

    protected Animation currentAnimation;
    protected IController currentController;

    protected Vector2 position;
    protected HashMap<Integer, Animation> animations = new HashMap<Integer, Animation>();

    protected float width  = 1f;
    protected float height = 2f;
    protected Vector2 forwardVector = new Vector2(0,1);
    protected IAnimationFactory animationFactory;

    public BasePlayerCharacter() {this(new Vector2());}

    public BasePlayerCharacter(Vector2 position) {
        this.position = position;

        this.animationFactory = new NostalgiAnimationFactory();

        animations.put(AnimationStates.WalkingEastAnimation,
                animationFactory.createAnimation("Spritesheet/char_walk_east.png",
                        32, 64, 1, 2, 1f / 6f,
                        Animation.PlayMode.LOOP));

        animations.put(AnimationStates.WalkingWestAnimation,
                animationFactory.createAnimation("Spritesheet/char_walk_west.png",
                        32, 64, 1, 2, 1f / 6f,
                        Animation.PlayMode.LOOP));

        animations.put(AnimationStates.WalkingNorthAnimation,
                animationFactory.createAnimation("Spritesheet/char_walk_north.png",
                        32, 64, 1, 5, 1f / 6f,
                        Animation.PlayMode.LOOP));

        animations.put(AnimationStates.WalkingSouthAnimation,
                animationFactory.createAnimation("Spritesheet/char_walk_south.png",
                        32, 64, 1, 5, 1f / 6f,
                        Animation.PlayMode.LOOP));

        animations.put(AnimationStates.IdleAnimation,
                animationFactory.createAnimation("Spritesheet/char_idle.png",
                        32, 64, 1, 1, 1f / 6f,
                        Animation.PlayMode.LOOP));


    }
    protected Body physicsBody;

    protected Vector2 currentVelocity = new Vector2(0.0f, 0.0f);

    @Override
    public Animation getCurrentAnimation() {
        return this.currentAnimation;
    }

    @Override
    public void setCurrentAnimation(Animation animation) {
        this.currentAnimation = animation;
    }

    @Override
    public void setCurrentAnimation(int state) {
        if(animations.containsKey(state))
            this.currentAnimation = animations.get(state);

    }

    @Override
    public void addAnimation(int state, Animation animation) {
        this.animations.put(state, animation);
    }

    @Override
    public Animation getAnimation(int state) {
        if(animations.containsKey(state))
            return animations.get(state);

        return null;
    }

    @Override
    public boolean isAnimated() {
        return true;
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
    public Stat getStat(int mod) {
        return null;
    }

    @Override
    public void setStat(int mod, Stat stat) {

    }

    @Override
    public IItem getEquipmentItem(int slot) {
        return null;
    }

    @Override
    public IItem[] getEquipmentItems() {
        return new IItem[0];
    }

    @Override
    public IItem getInventoryItem(int slot) {
        return null;
    }

    @Override
    public IItem[] getInventoryItems() {
        return new IItem[0];
    }

    @Override
    public void setCurrentController(IController controller) {
        this.currentController = controller;
    }

    @Override
    public IController getCurrentController() {
        return this.currentController;
    }

    @Override
    public Sprite getStaticSprite(float deltaT) {
        return null;
    }

    @Override
    public void moveForward(float velocity) {

    }

    @Override
    public void moveForward(float velocity, Vector2 direction) {

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
    public void setDimensions(float width, float height) {
        setHeight(height);
        setWidth(width);
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public Vector2 getVelocity() {
        return this.currentVelocity;
    }

    @Override
    public void setVelocity(Vector2 velocity) {
        this.currentVelocity = velocity;
    }

    @Override
    public void dispose() {
        this.animationFactory.dispose();
    }
}
