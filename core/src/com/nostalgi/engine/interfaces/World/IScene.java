package com.nostalgi.engine.interfaces.World;

/**
 * Created by Kristoffer on 2016-07-15.
 */
public interface IScene {
    void addActor(IActor actor);
    IActor getActor(String name);
}
