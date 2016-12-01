package com.nostalgi.engine.Navigation;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.GeometryUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ksdkrol on 2016-12-01.
 */

public class NavigationMesh {

    public void buildMesh(MapLayer meshLayer) {
        MapObjects polys = meshLayer.getObjects();

        for(MapObject poly : polys) {
            if(poly instanceof PolygonMapObject) {
                PolygonMapObject polygonMapObject = (PolygonMapObject)poly;

                Polygon polygon = polygonMapObject.getPolygon();
                float[] verts = polygon.getTransformedVertices();

                Vector2 center  = GeometryUtils.triangleCentroid(verts[0], verts[1], verts[2], verts[3], verts[4], verts[5], new Vector2(polygon.getX(), polygon.getY()));

                center = new Vector2(center.x / 32f, center.y / 32f);
                System.out.println(center);
            }
        }
    }

}
