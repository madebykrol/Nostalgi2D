package com.nostalgi.engine.physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

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
    public <T extends Light> T createLightSource(Class<T> type, Vector2 position) {
        PointLight light = new PointLight(
                rayHandler, 40, null, 5, position.x, position.y);

        return (T)light;
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
