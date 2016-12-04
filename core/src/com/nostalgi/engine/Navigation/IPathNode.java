package com.nostalgi.engine.Navigation;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.World.IActor;

import java.util.HashMap;

/**
 * Created by Krille on 01/12/2016.
 */

public interface IPathNode {

    int[] getFloors();
    boolean isOnFloor(int floor);
    Polygon getPolygon();
    Vector2 getPosition();
    int getIndex();

    void addNeighbor(IPathNode node);

    HashMap<Integer, IPathNode> getNeighbors();

    void setCost(float cost);
    float getCost();

    void setParent(IPathNode parent);
    IPathNode getParent();

}
