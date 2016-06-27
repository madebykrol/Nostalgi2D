package com.pixelwarriors.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Created by Kristoffer on 2016-06-26.
 */
public class PixelWarriorCamera extends OrthographicCamera {
    int left, right, bottom, top, unitScale;

    float camViewportHalfWidth = 0;
    float camViewportHalfHeight = 0;


    public PixelWarriorCamera(float w, float h) {
        super(w, h);
    }

    public void setWorldBounds(int left, int bottom, int width, int height, int unitScale) {

        this.left = left;
        this.bottom = bottom;

        this.top = bottom + height;
        this.right = left + width;

        camViewportHalfWidth = this.viewportWidth  * 0.5f;
        camViewportHalfHeight = this.viewportHeight * 0.5f;

    }

    public void setPositionSafe(float x, float y) {
        float x1 = MathUtils.clamp(x, left + camViewportHalfWidth, right - camViewportHalfWidth);
        float y1 = MathUtils.clamp(y, bottom + camViewportHalfHeight, top - camViewportHalfHeight);

        this.position.set(x1, y1, 0);
        update();
    }
}
