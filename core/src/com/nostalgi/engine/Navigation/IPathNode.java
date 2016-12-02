package com.nostalgi.engine.Navigation;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.World.IActor;

/**
 * Created by Krille on 01/12/2016.
 */

public interface IPathNode {

    int getFloor();
    Polygon getPolygon();
    Vector2 getPosition();
    int getIndex();


}
