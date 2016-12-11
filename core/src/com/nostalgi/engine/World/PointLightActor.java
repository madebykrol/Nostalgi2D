package com.nostalgi.engine.World;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.Utils.CurveFloat;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.physics.ILight;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;

/**
 * Created by ksdkrol on 2016-12-05.
 */

public class PointLightActor extends BaseActor implements ILight{

    private IWorld world;
    private Light light;
    private CurveFloat sinCurve;
    public PointLightActor(IWorld world) {
        this.world = world;
        this.canEverTick = true;

        this.sinCurve = new CurveFloat(0, 1.01f);
    }

    @Override
    public void postSpawn() {
        this.light = (PointLight)this.world.getLightingSystem().createPointLight(
                120,
                Color.ORANGE,
                15f,
                this.getParent().getPosition());
    }


    public void tick(float dTime) {
        super.tick(dTime);
        this.light.setPosition(this.getParent().getWorldPosition());
        this.light.setDirection(this.getParent().getRotation());
        this.light.setStaticLight(false);
        Color c = this.light.getColor();

        c.a = sinCurve.curve(dTime);

        this.light.setColor(c);
    }

    @Override
    public Color getColor() {
        return this.light.getColor();
    }

    @Override
    public void setColor(Color color) {
        this.light.setColor(color);
    }

    @Override
    public boolean isEnabled(boolean enabled) {
        this.light.setActive(enabled);
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.light.isActive();
    }
}