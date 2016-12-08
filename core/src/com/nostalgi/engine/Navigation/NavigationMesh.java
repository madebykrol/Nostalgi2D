package com.nostalgi.engine.Navigation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.GeometryUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.World.IActor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by ksdkrol on 2016-12-01.
 */

public class NavigationMesh implements INavMesh{

    private HashMap<Integer, IPathNode> nodes;
    private MapLayer meshLayer;
    private int unitScale;
    private int floor;

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
                int[] floors = new int[4];
                if(poly.getProperties().containsKey("Floor")) {
                    String floorString = (String)poly.getProperties().get("Floor");
                    String[] floorStrings = floorString.split(":");

                    for(int i = 0; i < floorStrings.length; i++) {
                        floors[i] = Integer.parseInt(floorStrings[i]);
                    }
                } else {
                    floors[0] = 1;
                }

                Polygon polygon = polygonMapObject.getPolygon();
                float[] vertices = polygon.getTransformedVertices();

                Vector2 center  = GeometryUtils.triangleCentroid(vertices[0] / unitScale, vertices[1] / unitScale, vertices[2] / unitScale, vertices[3] / unitScale, vertices[4] / unitScale, vertices[5] / unitScale, new Vector2(polygon.getX(), polygon.getY()));

                center = new Vector2(center.x, center.y);
                IPathNode node = new PathNode(center, polygon, floors, nodeIndex++);
                addNode(node);
            }
        }

        for(IPathNode currentNode : getNodes().values()) {
            float[] v = currentNode.getPolygon().getTransformedVertices();
            for(IPathNode neighborNode : getNodes().values()) {
                if(neighborNode != currentNode && nodeIsOnSameFloor(currentNode, neighborNode)) {
                    Polygon poly = neighborNode.getPolygon();

                    int numSharedVertices = 0;
                    int x = 0;
                    int y = 1;

                    for (int i = 0; i < (v.length / 2); i++) {
                        if (pointInTriangle(new Vector2(v[x], v[y]), poly)) numSharedVertices++;
                        x += 2;
                        y += 2;
                    }

                    if (numSharedVertices >= 1) {
                        currentNode.addNeighbor(neighborNode);
                    }
                }
            }
        }

        System.out.println("Done building nav mesh");
    }

    @Override
    public void addNode(IPathNode node) {
       nodes.put(node.getIndex(), node);
    }

    @Override
    public IPathNode getNode(int key) {
        return null;
    }

    @Override
    public HashMap<Integer, IPathNode> getNodes() {
        return this.nodes;
    }

    @Override
    public int getUnitScale() {
        return this.unitScale;
    }


    public void drawNodes(OrthographicCamera camera) {
        SpriteBatch batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        BitmapFont font = new BitmapFont();

        ShapeRenderer circleRenderer = new ShapeRenderer();
        circleRenderer.setProjectionMatrix(camera.combined);
        circleRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Set<Integer> keys = nodes.keySet();
        for (Integer key : keys) {
            IPathNode node = nodes.get(key);
            if(node.isOnFloor(2)) {
                circleRenderer.setColor(Color.BLUE);
            } else {
                circleRenderer.setColor(Color.RED);
            }

            circleRenderer.circle(node.getPosition().x, node.getPosition().y, 0.5f);

            batch.begin();
            //font.draw(batch, String.valueOf(node.getIndex()), node.getPosition().x, node.getPosition().y);
            batch.end();
        }

        circleRenderer.end();
        circleRenderer.dispose();

        batch.dispose();
    }

    public void drawNeighbors(OrthographicCamera camera) {
        ShapeRenderer lineRenderer = new ShapeRenderer();
        lineRenderer.setProjectionMatrix(camera.combined);
        lineRenderer.begin(ShapeRenderer.ShapeType.Line);

        Set<Integer> keys = nodes.keySet();
        for (Integer key : keys) {
            IPathNode node = nodes.get(key);

            Set<Integer> neighborKeys = node.getNeighbors().keySet();
            for (Integer nKey : neighborKeys) {
                IPathNode neighborNode = node.getNeighbors().get(nKey);
                if(node.isOnFloor(2)) {
                    lineRenderer.setColor(Color.BLUE);
                } else {
                    lineRenderer.setColor(Color.RED);
                }
                lineRenderer.line(node.getPosition().x, node.getPosition().y, neighborNode.getPosition().x, neighborNode.getPosition().y);
            }
        }

        lineRenderer.end();
        lineRenderer.dispose();
    }

    private boolean nodeIsOnSameFloor(IPathNode node1, IPathNode node2) {
        int[] floors = node1.getFloors();
        for(int i = 0; i < floors.length; i++) {
            if(floors[i] == 0)
                continue;
            if(node2.isOnFloor(floors[i]))
                return true;
        }

        return false;
    }

    public boolean pointInTriangle(Vector2 currentPoint, Polygon triangle) {
        float[] tVerts = triangle.getTransformedVertices();

        Vector2 p1 = new Vector2((int)tVerts[0], (int)tVerts[1]);
        Vector2 p2 = new Vector2((int)tVerts[2], (int)tVerts[3]);
        Vector2 p3 = new Vector2((int)tVerts[4], (int)tVerts[5]);

        float alpha = ((p2.y - p3.y) * (currentPoint.x - p3.x) + (p3.x - p2.x) * (currentPoint.y - p3.y)) /
                ((p2.y - p3.y) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.y - p3.y));
        float beta = ((p3.y - p1.y) * (currentPoint.x - p3.x) + (p1.x - p3.x) * (currentPoint.y - p3.y)) /
                ((p2.y - p3.y) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.y - p3.y));
        float gamma = 1.0f - alpha - beta;

        if ((alpha > 0 && beta > 0 && gamma > 0) ? true : false) {
            return true;
        }
        else if (isPointBetweenTwoOtherPoints(currentPoint, p1, p2) ||
                isPointBetweenTwoOtherPoints(currentPoint, p1, p3) ||
                isPointBetweenTwoOtherPoints(currentPoint, p2, p3)) {
            return true;
        }

        return false;
    }


    @Override
    public void reset() {
        for (IPathNode entry : nodes.values()) {
            entry.setParent(null);
            entry.setCost(9999);
        }
    }

    private boolean isPointBetweenTwoOtherPoints(Vector2 currentPoint, Vector2 p1, Vector2 p2) {
        float dxc = currentPoint.x - p1.x;
        float dyc = currentPoint.y - p1.y;

        float dx1 = p1.x - p2.x;
        float dy1 = p1.y - p2.y;

        float cross = dxc * dy1 - dyc * dx1;

        if (cross != 0) {
            return false;
        }
        else { //checked if it was on vector, now need to check if it's inbetween the two points.
            if (Math.abs(dx1) >= Math.abs(dy1))
                return dx1 < 0 ?
                        p1.x <= currentPoint.x && currentPoint.x <= p2.x :
                        p2.x <= currentPoint.x && currentPoint.x <= p1.x;
            else
                return dy1 < 0 ?
                        p1.y <= currentPoint.y && currentPoint.y <= p2.y :
                        p2.y <= currentPoint.y && currentPoint.y <= p1.y;
        }
    }


}
