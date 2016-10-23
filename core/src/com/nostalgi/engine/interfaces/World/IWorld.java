package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IGameState;

import java.util.ArrayList;

/**
 * Physics World abstraction.
 *
 * Created by Kristoffer on 2016-07-23.
 */
public interface IWorld{

    IGameMode getGameMode();
    IGameState getGameState();

    World getPhysicsWorld();
    void step(float timeStep, int velocityIterations, int positionIterations);

    Body createBody(IActor actor);
    Body createBody(IActor actor, float unitScale);

    Body createBody(IWall wall);
    Body createBody(IWall wall, float unitScale);

    void updateBody(IActor actor);
    void updateBody(IActor actor, float unitScale);

    void updateBody(IWall wall);
    void updateBody(IWall actor, float unitScale);


    boolean destroyBody(Body body);

    ArrayList<IActor> actorsCloseToLocation(Vector2 position, float distance);

    void dispose();
}
