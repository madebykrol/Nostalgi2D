package com.nostalgi.engine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nostalgi.engine.Factories.NostalgiAnimationFactory;
import com.nostalgi.engine.States.AnimationState;
import com.nostalgi.engine.World.BaseActor;
import com.nostalgi.engine.interfaces.Factories.IAnimationFactory;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.World.IItem;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.physics.BoundingVolume;
import com.nostalgi.engine.physics.CollisionCategories;

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

    private boolean isMoving;
    private boolean isJumping;

    public BasePlayerCharacter (IWorld world) {
        this.isStatic(false);
        BoundingVolume boundingVolume = new BoundingVolume();
        boundingVolume.setCollisionCategory(CollisionCategories.CATEGORY_PLAYER);
        boundingVolume.setCollisionMask(CollisionCategories.MASK_PLAYER);
        boundingVolume.isSensor(false);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.25f, new Vector2(0,-0.25f), 0);
        boundingVolume.setShape(shape);
        boundingVolume.setVolumeId("feet");

        this.setBoundingVolume(boundingVolume);

        BoundingVolume boundingVolume2 = new BoundingVolume();
        boundingVolume2.isSensor(false);
        boundingVolume2.setVolumeId("head");

        PolygonShape shape2 = new PolygonShape();
        shape2.setAsBox(0.5f, 0.75f, new Vector2(0,0.75f), 0);
        boundingVolume2.setShape(shape2);

        this.setBoundingVolume(boundingVolume2);

        this.setMass(0.25f);

        physicsSimulated(true);

        IAnimationFactory animationFactory = new NostalgiAnimationFactory();

        this.addAnimation(AnimationState.WalkingEastAnimation,
                animationFactory.createAnimation("Spritesheet/char_walk_east.png",
                        32, 64, 1, 2, 1f / 6f,
                        Animation.PlayMode.LOOP));

        this.addAnimation(AnimationState.WalkingWestAnimation,
                animationFactory.createAnimation("Spritesheet/char_walk_west.png",
                        32, 64, 1, 2, 1f / 6f,
                        Animation.PlayMode.LOOP));

        this.addAnimation(AnimationState.WalkingNorthAnimation,
                animationFactory.createAnimation("Spritesheet/char_walk_north.png",
                        32, 64, 1, 5, 1f / 6f,
                        Animation.PlayMode.LOOP));

        this.addAnimation(AnimationState.WalkingSouthAnimation,
                animationFactory.createAnimation("Spritesheet/char_walk_south.png",
                        32, 64, 1, 5, 1f / 6f,
                        Animation.PlayMode.LOOP));

        this.addAnimation(AnimationState.IdleFaceSouthAnimation,
                animationFactory.createAnimation("Spritesheet/char_idle.png",
                        32, 64, 1, 1, 1f / 6f,
                        Animation.PlayMode.LOOP));

        this.addAnimation(AnimationState.IdleFaceNorthAnimation,
                animationFactory.createAnimation("Spritesheet/char_idle_north.png",
                        32, 64, 1, 1, 1f / 6f,
                        Animation.PlayMode.LOOP));

        this.addAnimation(AnimationState.IdleFaceEastAnimation,
                animationFactory.createAnimation("Spritesheet/char_idle_east.png",
                        32, 64, 1, 1, 1f / 6f,
                        Animation.PlayMode.LOOP));

        this.addAnimation(AnimationState.IdleFaceWestAnimation,
                animationFactory.createAnimation("Spritesheet/char_idle_west.png",
                        32, 64, 1, 1, 1f / 6f,
                        Animation.PlayMode.LOOP));
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

        updateAnimation();

        if(!isMoving()) {
            float facingAngle = getFacingDirection();
            if (facingAngle >= Direction.NORTH_EAST && facingAngle <= Direction.NORTH_WEST) {
                setWalkingState(AnimationState.IdleFaceNorthAnimation);
            }

            if (facingAngle >= Direction.NORTH_WEST && facingAngle <= Direction.SOUTH_WEST) {
                setWalkingState(AnimationState.IdleFaceWestAnimation);
            }

            if (facingAngle >= Direction.SOUTH_WEST && facingAngle <= Direction.SOUTH_EAST) {
                setWalkingState(AnimationState.IdleFaceNorthAnimation);
            }
        }

    }

    protected void updateAnimation() {

        if(this.getFacingDirection() == Direction.SOUTH
                || this.getFacingDirection() == Direction.SOUTH_EAST
                || this.getFacingDirection() == Direction.SOUTH_WEST) {
            if(isMoving()) {
                setWalkingState(AnimationState.WalkingSouthAnimation);
            }
        }

        if(this.getFacingDirection() == Direction.EAST) {
            if(isMoving()) {
                setWalkingState(AnimationState.WalkingEastAnimation);
            }
        }

        if(this.getFacingDirection() == Direction.WEST) {
            if(isMoving()) {
                setWalkingState(AnimationState.WalkingWestAnimation);
            }
        }

        if(this.getFacingDirection() == Direction.NORTH
                || this.getFacingDirection() == Direction.NORTH_EAST
                || this.getFacingDirection() == Direction.NORTH_WEST) {
            if(isMoving()) {
                setWalkingState(AnimationState.WalkingNorthAnimation);
            }
        }
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
        float dot = this.getPhysicsBody().getWorldCenter().dot(target);
        float det = this.getPhysicsBody().getWorldCenter().x * target.y - this.getPhysicsBody().getWorldCenter().y * target.x;

        float angleBetween = MathUtils.atan2(det, dot);

        System.out.println((angleBetween * (float)(180f/Math.PI)));
        this.face((angleBetween * (float)(180f/Math.PI)));
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
    public void setFloorLevel(int floor) {
        super.setFloorLevel(floor);
    }

    @Override
    public void moveForward(float velocity) {
        Vector2 direction = new Vector2(
                (float) Math.cos(Math.toRadians(this.getFacingDirection())),
                (float) Math.sin(Math.toRadians(this.getFacingDirection())));

        direction.scl(velocity);

        this.currentVelocity = direction;
        Body body = this.getPhysicsBody();
        body.setLinearVelocity(direction);
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
