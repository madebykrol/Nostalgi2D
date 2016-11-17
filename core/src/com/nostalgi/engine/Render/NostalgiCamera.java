package com.nostalgi.engine.Render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.nostalgi.engine.interfaces.IFollowCamera;

/**
 * Created by Kristoffer on 2016-06-26.
 */
public class NostalgiCamera extends OrthographicCamera implements IFollowCamera {
    private int unitScale;

    public NostalgiCamera(float w, float h, int unitScale) {
        super(w, h);
        this.unitScale = unitScale;
        setToOrtho(false, w / (unitScale), h / (unitScale));
    }


    @Override
    public boolean followPlayerCharacter() {
        return true;
    }
}
