package com.nostalgi.engine.Navigation;

import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.IWorld;

import java.util.ArrayList;

/**
 * Created by Krille on 03/12/2016.
 */

public interface INavigationSystem {

    IPathNode getNodeCloseToActor(IActor actor);

    IPathNode getNodeCloseToPoint(Vector2 point);

    void loadNavMesh(INavMesh navMesh);

    ArrayList<IPathNode> findPath(Vector2 start, Vector2 finish, IWorld world);

    ArrayList<IPathNode> findPath(IActor player, IActor target, IWorld world);

    void findPathAsync(Vector2 start, Vector2 finish, IWorld world, IPathFoundCallback callback);

    void findPathAsync(IActor player, IActor target, IWorld world, IPathFoundCallback callback);

    IPathNode getNextWayPoint(ArrayList<IPathNode> path, Vector2 currentPosition);

}

