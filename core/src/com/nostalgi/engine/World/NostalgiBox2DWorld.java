package com.nostalgi.engine.World;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.IWorld;

import java.util.ArrayList;

/**
 * Created by Kristoffer on 2016-07-23.
 */
public class NostalgiBox2DWorld implements IWorld<Body, BodyDef> {

    private World world;

    public NostalgiBox2DWorld(Vector2 gravity, boolean doSleep){
        this(new World(gravity, doSleep));
    }

    public NostalgiBox2DWorld(World world) {
        this.world = world;
    }

    @Override
    public void step(float timeStep, int velocityIterations, int positionIterations) {
        world.step(timeStep, velocityIterations, positionIterations);
    }

    @Override
    public Body createBody(BodyDef bodyDef) {
        return world.createBody(bodyDef);
    }

    @Override
    public ArrayList<IActor> actorsCloseToLocation(Vector2 position, float distance) {

        float x1 = (position.x) + (distance);
        float x2 = (position.x) - (distance);

        float y1 = (position.y) + (distance);
        float y2 = (position.y) - (distance);

        final ArrayList<IActor> actors = new ArrayList<IActor>();

        world.QueryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                Object o = fixture.getUserData();
                if(o instanceof IActor) {
                    actors.add((IActor) o);
                }
                return true;
            }
        },
        x2, y2, x1,y1);

        return actors;
    }


}
