package com.nostalgi.engine;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.interfaces.World.IWall;

/**
 * Created by Kristoffer on 2016-07-10.
 */
public class NostalgiPhysicsBody extends Body {
    /**
     * Constructs a new body with the given address
     *
     * @param world the world
     * @param addr  the address
     */
    protected NostalgiPhysicsBody(World world, long addr) {
        super(world, addr);
    }
}
