package com.nostalgi.engine.physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import box2dLight.Light;

/**
 * Created by ksdkrol on 2016-12-06.
 */

public interface ILightingSystem <T> extends Disposable {

    void resizeFrameBufferObject(int width, int height);

    T createPointLight(int rays,  Color color,
                       float distance, Vector2 position);
    T createConeLight(int rays, Color color,
                      float distance, Vector2 position, float directionDegree,
                      float coneDegree);
    T createDirectionalLight(int rays, Color color,
                             float directionDegree);
    T createChainLight(int rays, Color color,
                       float distance, int rayDirection);

    T createChainLight(int rays, Color color,
                           float distance, int rayDirection, float[] chain);

    void updateAmbientLight(Color ambientLight);

    void updateAndRender(OrthographicCamera camera);
}
