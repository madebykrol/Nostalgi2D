package com.nostalgi.engine.Utils;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Krille on 11/12/2016.
 */

public class PolygonUtils {

    /**
     * Get verts from polygon without translating them from pixel to unit space.
     * @param ps
     * @return
     */
    public static float[] getVertsFromPolygon(PolygonShape ps) {
        return getVertsFromPolygon(ps, 1);
    }

    /**
     * Get verts from polygon and translate them from pixel to unit space.
     * @param ps
     * @param unitScale
     * @return
     */
    public static float[] getVertsFromPolygon(PolygonShape ps, float unitScale) {
        float[] vertices = new float[ps.getVertexCount()*2];
        for(int i = 0; i < ps.getVertexCount(); i++) {
            Vector2 v = new Vector2();
            ps.getVertex(i, v);
            vertices[i++] = v.x / unitScale;
            vertices[i] = v.y /  unitScale;
        }

        return vertices;
    }

}
