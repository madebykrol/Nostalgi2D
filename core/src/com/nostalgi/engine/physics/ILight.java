package com.nostalgi.engine.physics;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Krille on 07/12/2016.
 */

public interface ILight {

    Color getColor();
    void setColor(Color color);

    boolean isEnabled(boolean enabled);
    boolean isEnabled();


}
