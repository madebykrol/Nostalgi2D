package com.nostalgi.engine.World;

import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.World.IWorld;

import box2dLight.Light;
import box2dLight.PointLight;

/**
 * Created by ksdkrol on 2016-12-05.
 */

public class PointLightActor extends BaseActor {

    private IWorld world;
    private Light light;

    public PointLightActor(IWorld world) {
        this.world = world;
        this.light = (PointLight)this.world.getLightingSystem().createLightSource(PointLight.class, new Vector2(0f,0f));
        this.canEverTick = true;
    }


    public void tick(float dTime) {
        super.tick(dTime);
        this.light.setPosition(this.getPosition());
    }

}
