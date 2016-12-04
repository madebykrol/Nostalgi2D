package com.nostalgi.engine.Navigation;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.World.IActor;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Krille on 03/12/2016.
 */

public class NostalgiNavigationSystem implements INavigationSystem {

    private INavMesh currentNavMesh;

    public void loadNavMesh(INavMesh mesh) {
        this.currentNavMesh = mesh;
    }

    public IPathNode getNodeCloseToActor(IActor actor) {
        return getNodeCloseToPoint(actor.getWorldPosition());
    }

    public IPathNode getNodeCloseToPoint(Vector2 point) {
        ArrayList<IPathNode> touchedNodes = new ArrayList<IPathNode>();
        for(IPathNode node : this.currentNavMesh.getNodes().values()) {
            // we need to translate the point from unit space to pixel space.
            if(currentNavMesh.pointInTriangle(new Vector2(point.x * currentNavMesh.getUnitScale(), point.y * currentNavMesh.getUnitScale()), node.getPolygon())) {
                return node;
            }
        }
        return null;
    }

    public IPathNode getNextWayPoint(ArrayList<IPathNode> path, Vector2 currentPosition) {
        IPathNode returnNode = null;
        if(!path.isEmpty()) {
            returnNode = path.get(0);
            if(Math.abs((returnNode.getPosition().x - currentPosition.x)) < 0.5f && Math.abs((returnNode.getPosition().y - currentPosition.y)) < 0.5f){
                path.remove(returnNode);
            }
        }
        return returnNode;
    }

    public ArrayList<IPathNode> findPath(Vector2 start, Vector2 finish) {

        this.currentNavMesh.reset();

        ArrayList<IPathNode> path = new ArrayList<IPathNode>();
        float[] verts = new float[]{0f, 0f,0f,0f,0f,0f};


        IPathNode firstWayPoint = getNodeCloseToPoint(start);
        IPathNode goalWayPoint = getNodeCloseToPoint(finish);
        if(firstWayPoint == null || goalWayPoint == null) {
            return path;
        }
        // add end location as a node on the path.
        path.add(new PathNode(finish, new Polygon(verts), new int[]{0}, 0));

        // Find path.
        path.addAll(findPath(firstWayPoint, goalWayPoint));

        path.set(path.size()-1, new PathNode(start, new Polygon(verts), new int[]{0}, path.size()));

        Collections.reverse(path);

        return path;
    }

    public ArrayList<IPathNode> findPath(IActor player, IActor target){
        return findPath(player.getWorldPosition(), target.getWorldPosition());
    }

    @Override
    public void findPathAsync(final Vector2 start, final Vector2 finish, final IPathFoundCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do something important here, asynchronously to the rendering thread
                final ArrayList<IPathNode> result = findPath(start, finish);
                // post a Runnable to the rendering thread that processes the result
                callback.onPathFound(result);
            }
        }).start();
    }

    @Override
    public void findPathAsync(IActor player, IActor target, IPathFoundCallback callback) {
        this.findPathAsync(player.getWorldPosition(), target.getWorldPosition(), callback);
    }


    protected ArrayList<IPathNode> findPath(IPathNode start, IPathNode finish) {


        if ((start == null || finish == null) || start.getPosition().x == finish.getPosition().x && start.getPosition().y == finish.getPosition().y) {
            return new ArrayList<IPathNode>();
        }

        ArrayList<IPathNode> reachable = new ArrayList<IPathNode>();
        ArrayList<IPathNode> explored = new ArrayList<IPathNode>();

        start.setCost(1);
        reachable.add(start);

        while (reachable.size() > 0) {
            IPathNode node = chooseNode(reachable);

            reachable.remove(node);
            explored.add(node);

            ArrayList<IPathNode> newReachable = getUnexploredNeighbors(node, explored);
            for (IPathNode newNode : newReachable) {
                if (!reachable.contains(newNode)) {
                    reachable.add(newNode);
                }

                float tempCost = node.getCost() + calculateCost(node, newNode);
                if (tempCost < newNode.getCost()) {
                    newNode.setParent(node);
                    newNode.setCost(tempCost);
                }
            }

            if (node.getIndex() == finish.getIndex()) {
                return buildPath(finish);
            }
        }

        return new ArrayList<IPathNode>();
    }


    private float calculateCost(IPathNode startNode, IPathNode endNode) {
        return Math.abs(startNode.getPosition().x - endNode.getPosition().x) + Math.abs(startNode.getPosition().y - endNode.getPosition().y);
    }

    private ArrayList<IPathNode> getUnexploredNeighbors(IPathNode node, ArrayList<IPathNode> explored) {
        ArrayList<IPathNode> unexploredNeighbors = new ArrayList<IPathNode>();

        for (IPathNode neighbor : node.getNeighbors().values()) {
            if (!doesListContainNode(neighbor, explored)) {
                unexploredNeighbors.add(neighbor);
            }
        }

        return unexploredNeighbors;
    }

    private boolean doesListContainNode(IPathNode node, ArrayList<IPathNode> listOfNodes) {
        int key = node.getIndex();

        for(int i = 0; i < listOfNodes.size(); i++) {
            if (key == listOfNodes.get(i).getIndex()) {
                return true;
            }
        }

        return false;
    }

    private IPathNode chooseNode(ArrayList<IPathNode> reachable) {
        float minCost = 99999;
        IPathNode bestNode = null;

        for (IPathNode node : reachable) {
            if (node.getCost() < minCost) {
                minCost = node.getCost();
                bestNode = node;
            }
        }

        return bestNode;
    }

    private ArrayList<IPathNode> buildPath(IPathNode goalNode) {
        ArrayList<IPathNode> path = new ArrayList<IPathNode>();
        path.add(goalNode);
        IPathNode parent = goalNode.getParent();

        while (parent != null) {
            path.add(parent);
            parent = parent.getParent();
        }

        return path;
    }
}
