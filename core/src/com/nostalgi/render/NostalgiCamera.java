package com.nostalgi.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nostalgi.engine.LevelCameraBounds;

/**
 * Created by Kristoffer on 2016-06-26.
 */
public class NostalgiCamera extends OrthographicCamera {
    int left, right, bottom, top, unitScale;

    float camViewportHalfWidth = 0;
    float camViewportHalfHeight = 0;


    public NostalgiCamera(float w, float h, LevelCameraBounds bounds, int unitScale) {
        super(w, h);
        this.unitScale = unitScale;
        setToOrtho(false, w / (unitScale), h / (unitScale));
        setWorldBounds(bounds);
    }

    public void setWorldBounds(LevelCameraBounds bounds) {
        this.setWorldBounds(bounds.left, bounds.bottom, bounds.right, bounds.top);
    }

    public void setWorldBounds(int left, int bottom, int width, int height) {

        this.left = left;
        this.bottom = bottom;

        this.top = bottom + height;
        this.right = left + width;

        camViewportHalfWidth = this.viewportWidth  * 0.5f;
        camViewportHalfHeight = this.viewportHeight * 0.5f;
    }

    public void moveToWorldFromScreenLocation(Vector2 screenLocation) {
        moveToWorldFromScreenLocation(screenLocation.x, screenLocation.y);
    }

    public void moveToWorldFromScreenLocation(float x, float y) {
        Vector3 worldPos = this.unproject(new Vector3(x, y, 0));

        setPositionSafe(worldPos.x, worldPos.y);
    }

    public void setPositionSafe(float x, float y) {

        // Clamp x
        float x1 = MathUtils.clamp(x, left + (camViewportHalfWidth*zoom), right - (camViewportHalfWidth*zoom));

        // Clamp y
        float y1 = MathUtils.clamp(y, bottom + (camViewportHalfHeight*zoom), top - (camViewportHalfHeight*zoom));

        // Set these positions
        this.position.set(x1, y1, 0);
        update();
    }
}
