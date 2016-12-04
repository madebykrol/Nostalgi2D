package com.nostalgi.engine.Navigation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.IActor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Krille on 01/12/2016.
 */

public interface INavMesh {

    /**
     * Generate the navigation mesh with a given unitScale.
     * The unitScale will scale the vertices when the center of each polygon is calculated.
     */
    void generate();

    void addNode(IPathNode node);
    IPathNode getNode(int key);

    HashMap<Integer, IPathNode> getNodes();

    int getUnitScale();

    void drawNodes(OrthographicCamera camera);
    void drawNeighbors(OrthographicCamera camera);
    boolean pointInTriangle(Vector2 currentPoint, Polygon triangle);
    void reset();
 }
