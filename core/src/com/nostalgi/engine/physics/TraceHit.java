package com.nostalgi.engine.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.nostalgi.engine.interfaces.World.IWorldObject;

/**
 * Created by ksdkrol on 2016-11-01.
 */

public class TraceHit {
    /**
     * Object we have hit.
     */
    public IWorldObject object;

    /**
     * Hit point
     */
    public Vector2 hitPoint;

    /**
     * Absolut hit normal (normal + hit)
     */
    public Vector2 hitNormal;

    /**
     * Fraction of the length of the ray that the hit occured.
     */
    public float fraction;

    /**
     * What fixture we hit, head, arm, body etc.
     */
    public Fixture fixture;
}
