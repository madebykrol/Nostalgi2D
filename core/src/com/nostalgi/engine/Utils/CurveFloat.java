package com.nostalgi.engine.Utils;

/**
 * Created by Krille on 08/12/2016.
 */
public class CurveFloat {

    private float start;
    private float stop;

    private float progress;

    private float[][] curveGraph;

    private boolean polynomal;

    public CurveFloat(float start, float stop) {
        this.start = start;
        this.stop = stop;
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
        return NMath.norm(this.f(progress), 0, 1);
    }

    /**
     * Curve function that calculates a y value from a given X on a curve.
     * eg y = f(x) = sin(x) * x^2 +1
     * @param x
     * @return
     */
    public float f(float x){
        return ((x*1) - (2*(float)Math.pow(x, 2)) - 0.25f * (float)Math.pow(x, 4) + 0.28f*(float)Math.pow(x, 3)
                + 1.45f * x);
    }

    public void reset() {
        progress = 0;
    }
}
