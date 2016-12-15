package com.nostalgi.engine;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nostalgi.engine.Utils.NMath;
import com.nostalgi.engine.World.BaseActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.World.IItem;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class BaseCharacter extends BaseActor implements ICharacter {

    private IController currentController;

    private float width  = 1f;
    private float height = 2f;

    private Vector2 currentVelocity = new Vector2(0.0f, 0.0f);
    private int walkingState;

    private boolean isMoving;
    private boolean isJumping;

    private Class aiControllerClass;
    private boolean shouldSpawnWithAiController;
    private boolean shouldBePossessedOnSpawn;

    public BaseCharacter() {

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
        return this.canEverTick;
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
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
    public <T extends IController> void setAIControllerClass(Class<T> defaultClass) {
        this.aiControllerClass = defaultClass;
    }

    @Override
    public Class getAIControllerClass() {
        return this.aiControllerClass;
    }

    @Override
    public boolean shouldSpawnWithAiController() {
        return shouldSpawnWithAiController;
    }

    @Override
    public boolean shouldSpawnWithAiController(boolean should) {
        return this.shouldSpawnWithAiController = should;
    }

    @Override
    public boolean shouldBePossessedOnSpawn() {
        return this.shouldBePossessedOnSpawn;
    }

    @Override
    public boolean shouldBePossessedOnSpawn(boolean should) {
        return this.shouldBePossessedOnSpawn = should;
    }

    @Override
    public Sprite getStaticSprite(float deltaT) {
        return null;
    }


    @Override
    public void lookAt(Vector2 target) {
        double angleBetween = NMath.angleBetween(this.getPhysicsBody().getWorldCenter(), target);

        this.setRotation((float)angleBetween);
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
    public void setFloorLevel(int floor) {
        super.setFloorLevel(floor);
    }

    @Override
    public void moveForward(float velocity) {
        Vector2 direction = new Vector2(
                (float) Math.cos(Math.toRadians(this.getRotation())),
                (float) Math.sin(Math.toRadians(this.getRotation())));

        direction.scl(velocity);

        this.currentVelocity = direction;
        Body body = this.getPhysicsBody();
        body.setLinearVelocity(direction);
        this.isMoving(true);
    }

    @Override
    public void stop() {
        Body body = this.getPhysicsBody();
        body.setLinearVelocity(0, 0);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void draw(Batch batch, float timeElapsed) {
        TextureRegion tr = this.getAnimation(this.getWalkingState()).getKeyFrame(timeElapsed);
        if(tr != null) {
            batch.draw(tr,
                    this.getWorldPosition().x,
                    this.getWorldPosition().y,
                    this.getWidth(),
                    this.getHeight());
        }
    }

    @Override
    public boolean isMoving() {
        return this.isMoving;
    }

    @Override
    public boolean isMoving(boolean moving) {
        return this.isMoving = moving;
    }

    @Override
    public boolean isJumping() {
        return this.isJumping;
    }

    @Override
    public boolean isJumping(boolean jumping) {
        return this.isJumping = jumping;
    }
}
