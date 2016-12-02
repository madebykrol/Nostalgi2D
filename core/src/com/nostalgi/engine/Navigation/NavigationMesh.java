package com.nostalgi.engine.Navigation;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.GeometryUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.World.IActor;

import java.util.HashMap;

/**
 * Created by ksdkrol on 2016-12-01.
 */

public class NavigationMesh implements INavMesh{

    private HashMap<Integer, IPathNode> nodes;
    private MapLayer meshLayer;
    private int unitScale;

    public NavigationMesh(MapLayer meshLayer, int unitScale) {
       this.meshLayer = meshLayer;
        this.unitScale = unitScale;
    }

    public void generate() {

        nodes = new HashMap<Integer, IPathNode>();
        MapObjects polys = meshLayer.getObjects();

        int nodeIndex = 1;
        for(MapObject poly : polys) {
            if(poly instanceof PolygonMapObject) {
                PolygonMapObject polygonMapObject = (PolygonMapObject)poly;

                Polygon polygon = polygonMapObject.getPolygon();
                float[] verts = polygon.getTransformedVertices();

                Vector2 center  = GeometryUtils.triangleCentroid(verts[0] / unitScale, verts[1] / unitScale, verts[2] / unitScale, verts[3] / unitScale, verts[4] / unitScale, verts[5] / unitScale, new Vector2(polygon.getX(), polygon.getY()));

                center = new Vector2(center.x, center.y);
                IPathNode node = new PathNode(center, polygon, nodeIndex++);
                addNode(node);
            }
        }
    }

    @Override
    public void addNode(IPathNode node) {
       nodes.put(node.getIndex(), node);
    }

    @Override
    public IPathNode getNode(int key) {
        return null;
    }

    public IPathNode getNodeCloseToActor(IActor actor) {
       return getNodeCloseToPoint(actor.getWorldPosition());
    }

    public IPathNode getNodeCloseToPoint(Vector2 point) {
        for(IPathNode node : this.nodes.values()) {

        }

        return null;
    }

    @Override
    public HashMap<Integer, IPathNode> getNodes() {
        return this.nodes;
    }

    private boolean pointInTriangle(Vector2 point, Polygon triangle) {
        return true;
    }


}
