package com.nostalgi.engine.interfaces.physics;

import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Kristoffer on 2016-07-18.
 */
public class BoundingVolume {
    private boolean isStatic = false;
    private PolygonShape shape;

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
}
