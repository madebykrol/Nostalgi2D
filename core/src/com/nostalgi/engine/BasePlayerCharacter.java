package com.nostalgi.engine;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameState;
import com.nostalgi.engine.interfaces.IItem;

import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.Map;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class BasePlayerCharacter implements ICharacter {

    protected Animation currentAnimation;
    protected IController currentController;
    protected IGameState gameState;

    protected Vector2 position;
    protected Map<Integer, Animation> animations;

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
    public void dispose() {

    }
}
