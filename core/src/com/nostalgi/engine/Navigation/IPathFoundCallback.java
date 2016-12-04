package com.nostalgi.engine.Navigation;

import java.util.ArrayList;

/**
 * Created by Krille on 04/12/2016.
 */

public interface IPathFoundCallback {
    void onPathFound(ArrayList<IPathNode> path);
}