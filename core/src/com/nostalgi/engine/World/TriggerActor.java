package com.nostalgi.engine.World;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.physics.BoundingVolume;
import com.nostalgi.engine.physics.CollisionCategories;

import java.util.ArrayList;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public class TriggerActor extends BaseActor {

    public TriggerActor(ArrayList<BoundingVolume> boundingVolumes) {
        super(boundingVolumes);
        BoundingVolume boundingVolume = new BoundingVolume();
        this.isStatic(true);
        boundingVolume.setCollisionCategory(CollisionCategories.CATEGORY_TRIGGER);
        boundingVolume.setCollisionMask(CollisionCategories.MASK_TRIGGER);
        boundingVolume.isSensor(true);
        this.setBoundingVolume(boundingVolume);
    }

    @Override
    public void onOverlapBegin(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture) {

    }

    @Override
    public void onOverlapEnd(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture) {

    }
}
