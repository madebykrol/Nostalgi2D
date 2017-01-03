package com.nostalgi.engine.Utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by ksdkrol on 2016-12-01.
 */

public class NMath {

    /**
     *
     * @param pos1
     * @param pos2
     * @return
     */
    public static float angleBetween(Vector2 pos1, Vector2 pos2) {
        float dy = pos2.y - pos1.y;
        float dx = pos2.x - pos1.x;

        return (float)Math.atan2(dy, dx) * MathUtils.radiansToDegrees;
    }

    /**
     * Normalize a value to scale between min and max
     * (value - min) / (max - min)
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static float norm(float value, float min, float max) {
        return (value - min) / (max - min);
    }

    public static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_EVEN);
        return bd.floatValue();
    }

}
