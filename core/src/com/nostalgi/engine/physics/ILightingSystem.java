package com.nostalgi.engine.physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import box2dLight.Light;

/**
 * Created by ksdkrol on 2016-12-06.
 */

public interface ILightingSystem <T1> extends Disposable {
    void resizeFrameBufferObject(int width, int height);

    <T extends T1> T createLightSource(Class<T> type, Vector2 position);

    void updateAmbientLight(Color ambientLight);

    void updateAndRender(OrthographicCamera camera);
}
