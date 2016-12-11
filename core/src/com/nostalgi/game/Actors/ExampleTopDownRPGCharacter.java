package com.nostalgi.game.Actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nostalgi.engine.BasePlayerCharacter;
import com.nostalgi.engine.Direction;
import com.nostalgi.engine.Factories.NostalgiAnimationFactory;
import com.nostalgi.engine.Stat;
import com.nostalgi.engine.States.AnimationState;
import com.nostalgi.engine.World.BaseActor;
import com.nostalgi.engine.World.ISound;
import com.nostalgi.engine.World.Sound;
import com.nostalgi.engine.interfaces.Factories.IAnimationFactory;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.World.IItem;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.physics.BoundingVolume;
import com.nostalgi.engine.physics.CollisionCategories;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class ExampleTopDownRPGCharacter extends BasePlayerCharacter implements ICharacter {

    private IWorld world;

    private ISound walkingInGrass;
    ISound.ISoundReference walkingSoundReference = null;

    public ExampleTopDownRPGCharacter(IWorld world) {
        this.isStatic(false);
        this.world = world;
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

        // Load the sound.
        this.walkingInGrass = this.world.getSoundSystem().createSound("walking-in-grass",
                "sound/OOT_Steps_Grass1.wav");

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

        this.canEverTick = true;
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);
        updateAnimation();


        if(!isMoving()) {

            float facingAngle = getRotation();
            if (facingAngle >= Direction.NORTH_EAST && facingAngle <= Direction.NORTH_WEST) {
                setWalkingState(AnimationState.IdleFaceNorthAnimation);
            }

            if (facingAngle >= Direction.NORTH_WEST && facingAngle <= Direction.SOUTH_WEST) {
                setWalkingState(AnimationState.IdleFaceWestAnimation);
            }

            if (facingAngle >= Direction.NORTH_EAST && facingAngle <= Direction.SOUTH_EAST) {
                setWalkingState(AnimationState.IdleFaceEastAnimation);
            }

            if (facingAngle >= Direction.SOUTH_WEST && facingAngle <= Direction.SOUTH_EAST) {
                setWalkingState(AnimationState.IdleFaceSouthAnimation);
            }

            if(walkingSoundReference != null) {
                walkingSoundReference.pause();
            }

        } else {
            if(walkingSoundReference == null) {
                walkingSoundReference = this.walkingInGrass.play(1f, 0.75f, 0f);
                walkingSoundReference.setLooping(true);
            } else {
                walkingSoundReference.resume();
            }
        }

    }

    protected void updateAnimation() {
        if (isMoving()) {
            float facingDirection = Math.abs(this.getRotation());
            if (facingDirection >= Direction.SOUTH_WEST && facingDirection <= Direction.SOUTH_EAST) {
                setWalkingState(AnimationState.WalkingSouthAnimation);
            }

            if (facingDirection >= Direction.NORTH_WEST && facingDirection <= Direction.SOUTH_WEST){
                setWalkingState(AnimationState.WalkingWestAnimation);
            }

            if (facingDirection >= Direction.NORTH_EAST && facingDirection <= Direction.SOUTH_EAST){
                setWalkingState(AnimationState.WalkingEastAnimation);
            }

            if (facingDirection >= Direction.NORTH_EAST && facingDirection <= Direction.NORTH_WEST) {
                setWalkingState(AnimationState.WalkingNorthAnimation);
            }
        }
    }

    @Override
    public void dispose() {
        this.walkingInGrass.dispose();
    }

}
