package com.nostalgi.engine.World;

import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.physics.BoundingVolume;

import java.util.ArrayList;

/**
 * Created by Kristoffer on 2016-07-19.
 */
public class RootActor extends BaseActor {

    private float unitScale;

    public RootActor(Vector2 pos, float unitScale) {
        super(new ArrayList<BoundingVolume>());
        setPosition(pos);
        this.unitScale = unitScale;
    }

    public RootActor() {
        this(new Vector2(0,0), 32f);
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(super.getPosition().x * unitScale, super.getPosition().y * unitScale);
    }
}
