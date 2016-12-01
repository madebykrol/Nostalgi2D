package com.nostalgi.engine.interfaces.World;

import com.nostalgi.engine.physics.BoundingVolume;

/**
 * Created by Krille on 26/11/2016.
 */

public interface IComponent {
    BoundingVolume getBoundingVolume();
    void setBoundingVolume(BoundingVolume bounds);

    void addChildComponent(IComponent component);
    IComponent getChild(int index);
    IComponent[] getChildren();
}
