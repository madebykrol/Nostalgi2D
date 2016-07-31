package com.nostalgi.engine;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.World.BaseActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.World.IItem;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class BasePlayerCharacter extends BaseActor implements ICharacter {

    private IController currentController;

    private float width  = 1f;
    private float height = 2f;

    private Vector2 currentVelocity = new Vector2(0.0f, 0.0f);
    private float facing;
    private int walkingState;

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
    public void face(float dir) {
        this.facing = dir;
    }

    @Override
    public void face(Vector2 target) {
        this.facing = (float)Math.acos(this.getPosition().dot(target));
    }

    @Override
    public float getFacingDirection() {
        return this.facing;
    }

    @Override
    public void setWalkingState(int state) {
        this.walkingState = state;
    }

    @Override
    public int getWalkingState() {
        return this.walkingState;
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
    public void moveForward(float velocity) {
        Vector2 direction = new Vector2(
                (float) Math.cos(Math.toRadians(this.getFacingDirection())),
                (float) Math.sin(Math.toRadians(this.getFacingDirection())));

        direction.scl(5);

        this.currentVelocity = direction;
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

    @Override
    public void draw(Batch batch, float timeElapsed) {
        TextureRegion tr = this.getAnimation(this.getWalkingState()).getKeyFrame(timeElapsed);
        if(tr != null) {
            batch.draw(tr,
                    this.getPosition().x,
                    this.getPosition().y,
                    this.getWidth(),
                    this.getHeight());
        }
    }

}
