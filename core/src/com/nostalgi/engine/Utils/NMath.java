package com.nostalgi.engine.Utils;

/**
 * Created by ksdkrol on 2016-12-01.
 */

public class NMath {

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
}
