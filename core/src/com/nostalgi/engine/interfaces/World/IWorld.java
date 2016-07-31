package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Physics World abstraction.
 *
 * Created by Kristoffer on 2016-07-23.
 */
public interface IWorld<T1, T2> {
    void step(float timeStep, int velocityIterations, int positionIterations);
    T1 createBody(T2 bodyDef);
    ArrayList<IActor> actorsCloseToLocation(Vector2 position, float distance);
}
