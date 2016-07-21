package com.nostalgi.engine;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.World.BaseActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.World.IItem;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class BasePlayerCharacter extends BaseActor implements ICharacter {

    protected IController currentController;

    protected float width  = 1f;
    protected float height = 2f;

    protected Vector2 currentVelocity = new Vector2(0.0f, 0.0f);
    protected Direction facing;

    public BasePlayerCharacter() {this(new Vector2());}

    public BasePlayerCharacter(Vector2 position) {
        this.position = position;
    }

    @Override
    public boolean isAnimated() {
        return true;
    }

    @Override
    public Stat getStat(int mod) {
        return null;
    }

    @Override
    public void setStat(int mod, Stat stat) {

    }

    @Override
    public boolean canEverTick() {
        return true;
    }

    @Override
    public void tick(float delta) {
        System.out.println("Tick");
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
    public void setFacingDirection(Direction dir) {
        this.facing = dir;
    }

    @Override
    public Direction getFacingDirection() {
        return this.facing;
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
    public void moveForward(Vector2 velocity) {
        this.currentVelocity = velocity;
    }

    @Override
    public void stop() {
        this.currentVelocity.x = 0;
        this.currentVelocity.y = 0;
    }

    @Override
    public void dispose() {
    }

    @Override
      public String getName() {
        return "BasePlayer";
    }

}
