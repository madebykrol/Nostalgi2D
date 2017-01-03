package com.nostalgi.game.Actors;

import com.nostalgi.engine.World.BaseActor;
import com.nostalgi.engine.interfaces.World.IInteractable;
import com.nostalgi.engine.physics.BoundingVolume;

import java.util.ArrayList;

/**
 * Created by Kristoffer on 2016-07-25.
 */
public class WaterActor extends BaseActor implements IInteractable {

    public WaterActor(ArrayList<BoundingVolume> boundingVolumes) {
        super(boundingVolumes);
    }
}
