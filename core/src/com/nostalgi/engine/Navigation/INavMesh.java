package com.nostalgi.engine.Navigation;

import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.IActor;

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

    IPathNode getNodeCloseToActor(IActor actor);

    IPathNode getNodeCloseToPoint(Vector2 point);

    HashMap<Integer, IPathNode> getNodes();
 }
