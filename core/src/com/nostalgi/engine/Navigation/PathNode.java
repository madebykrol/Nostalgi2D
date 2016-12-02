package com.nostalgi.engine.Navigation;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ksdkrol on 2016-12-01.
 */

public class PathNode implements IPathNode {

    private Vector2 position;
    private Polygon triangle;
    private int index;
    private int floor;

    public PathNode(Vector2 position, Polygon triangle, int floor, int index) {
        this.position = position;
        this.triangle = triangle;
        this.index = index;
        this.floor = floor;
    }

    @Override
    public int getFloor() {
        return this.floor;
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

}
