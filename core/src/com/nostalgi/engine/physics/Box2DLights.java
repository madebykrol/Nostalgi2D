package com.nostalgi.engine.physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.ChainLight;
import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by Krille on 06/12/2016.
 */

public class Box2DLights implements ILightingSystem<Light> {

    private RayHandler rayHandler;
    private Color ambientLight;
    private int fboWidth = 0;
    private int fboHeight = 0;

    public Box2DLights(World world, int fboWidth, int fboHeight) {
        rayHandler = new RayHandler(world, fboWidth, fboHeight);

        this.fboWidth = fboWidth;
        this.fboHeight = fboHeight;
    }

    public Box2DLights(World world) {
        rayHandler = new RayHandler(world);
    }

    @Override
    public void resizeFrameBufferObject(int width, int height) {
        if(width != fboWidth || height != fboHeight) {
            this.rayHandler.resizeFBO(width, height);
            this.fboWidth = width;
            this.fboHeight = height;
        }
    }

    @Override
    public Light createPointLight(int rays,  Color color,
                                  float distance, Vector2 position) {
        return new PointLight(rayHandler, rays, color, distance, position.x, position.y);
    }

    @Override
    public Light createConeLight(int rays, Color color, float distance, Vector2 position, float directionDegree, float coneDegree) {
        return new ConeLight(
                rayHandler, rays, color,
                distance, position.x, position.y, directionDegree, coneDegree);
    }

    @Override
    public Light createDirectionalLight(int rays, Color color, float directionDegree) {
        return new DirectionalLight(rayHandler, rays, color, directionDegree);
    }

    public Light createChainLight(int rays, Color color,
                                  float distance, int rayDirection, float[] chain) {
        return new ChainLight(rayHandler, rays, color, distance, rayDirection, chain);
    }

    @Override
    public Light createChainLight(int rays, Color color,
                                  float distance, int rayDirection) {
        return this.createChainLight(rays, color, distance,rayDirection, new float[]{0f});
    }

    @Override
    public void updateAmbientLight(Color ambientLight) {
        if(this.ambientLight != ambientLight) {
            this.ambientLight = ambientLight;
            this.rayHandler.setAmbientLight(this.ambientLight);
        }
    }

    @Override
    public void updateAndRender(OrthographicCamera camera) {
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    @Override
    public void dispose() {
        this.rayHandler.dispose();
    }
}
