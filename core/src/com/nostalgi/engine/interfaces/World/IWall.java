package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface IWall {
    Body getBody();
    void setBody(Body body);
}
