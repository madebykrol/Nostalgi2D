package com.nostalgi.engine.World;

import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.physics.BoundingVolume;
import com.nostalgi.engine.physics.CollisionCategories;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public class TriggerActor extends BaseActor {

    public TriggerActor() {
        BoundingVolume boundingVolume = new BoundingVolume();
        boundingVolume.isStatic(true);
        boundingVolume.setCollisionCategory(CollisionCategories.CATEGORY_TRIGGER);
        boundingVolume.setCollisionMask(CollisionCategories.MASK_TRIGGER);
        boundingVolume.isSensor(true);
        this.setBoundingVolume(boundingVolume);
    }

    @Override
    public void onOverlapBegin(IActor overlapper) {

    }

    @Override
    public void onOverlapEnd(IActor overlapper) {

    }
}
