package com.nostalgi.engine.Utils;

/**
 * Created by Krille on 08/12/2016.
 */

public class CurveFloat {

    private float start;
    private float stop;

    private float progress;

    public CurveFloat(float start, float stop) {
        this.start = start;
        this.stop = stop;
    }

    public CurveFloat(ICurve curve) {

    }

    /**
     * Returns the scalar value between 0, 1 for this curve on the time line.
     * Curves have time on the x-axis, and scalar value on the y-axis.
     *
     * For each timestep (x) a new scalar (y) is returned.
     *
     * @param deltaTime
     * @return
     */
    public final float curve(float deltaTime) {
        if(this.stop <= progress) {
            reset();
            return 0f;
        }
        progress += deltaTime;
        return this.f(progress);
    }

    public float f(float x){
        return (float)Math.sin((double)x);
    }

    public void reset() {
        progress = 0;
    }
}
