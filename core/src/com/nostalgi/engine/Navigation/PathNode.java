package com.nostalgi.engine.Navigation;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

/**
 * Created by ksdkrol on 2016-12-01.
 */

public class PathNode implements IPathNode {

    private Vector2 position;
    private Polygon triangle;
    private int index;
    private int[] floors;

    private float cost = 9999;
    private IPathNode parent;

    private HashMap<Integer, IPathNode> neighbors;

    public PathNode(Vector2 position, Polygon triangle, int[] floors, int index) {
        this.position = position;
        this.triangle = triangle;
        this.index = index;
        this.floors = floors;
        this.neighbors = new HashMap<Integer, IPathNode>();
    }

    public int[] getFloors() {
        return this.floors;
    }

    @Override
    public boolean isOnFloor(int floor) {
        for(int i = 0; i < this.floors.length; i++) {
            if(this.floors[i] == 0)
                continue;
            if(this.floors[i] == floor)
                return true;
        }

        return false;
    }

    @Override
    public Polygon getPolygon() {
        return this.triangle;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public void addNeighbor(IPathNode node) {
        neighbors.put(node.getIndex(), node);
    }

    @Override
    public HashMap<Integer, IPathNode> getNeighbors() {
        return this.neighbors;
    }

    @Override
    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public float getCost() {
        return this.cost;
    }

    @Override
    public void setParent(IPathNode parent) {
        this.parent = parent;
    }

    @Override
    public IPathNode getParent() {
        return this.parent;
    }

    @Override
    public IPathNode clone() {
        PathNode cpy = new PathNode(this.position.cpy(), new Polygon(triangle.getVertices()), this.floors.clone(), this.index);
        
        // Don't copy neighbors or parent - these will be set up separately in NavigationMesh.cloneMesh()
        // This ensures proper thread isolation as each cloned node starts with fresh state
        cpy.cost = 9999; // Reset cost for pathfinding
        cpy.parent = null; // Reset parent for pathfinding

        return cpy;
    }

}
