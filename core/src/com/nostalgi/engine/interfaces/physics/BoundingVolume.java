package com.nostalgi.engine.interfaces.physics;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Kristoffer on 2016-07-18.
 */
public class BoundingVolume {

    private boolean isStatic = false;
    private boolean isSensor = false;
    private PolygonShape shape = new PolygonShape();
    private short collisionMask = -1;
    private short collisionCategory = 0;
    private float density = 100f;
    private float friction = 1f;
    private boolean autoRotate = false;
    private Vector2 relativePosition = new Vector2();


    public short getCollisionCategory() {
        return collisionCategory;
    }

    public void setCollisionCategory(short collisionCategory) {
        this.collisionCategory = collisionCategory;
    }

    public short getCollisionMask() {
        return collisionMask;
    }

    public void setCollisionMask(short collisionMask) {
        this.collisionMask = collisionMask;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public void isStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public PolygonShape getShape() {
        return this.shape;
    }

    public void setShape(PolygonShape shape) {
        this.shape = shape;
    }

    public boolean isSensor() {
        return isSensor;
    }

    public void isSensor(boolean isSensor) {
        this.isSensor = isSensor;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public boolean autoRotate() {
        return autoRotate;
    }

    public void autoRotate(boolean autoRotate) {
        this.autoRotate = autoRotate;
    }

    public void setRelativePosition(Vector2 relativePosition) { this.relativePosition = relativePosition; }

    public Vector2 getRelativePosition() { return this.relativePosition; }
}
