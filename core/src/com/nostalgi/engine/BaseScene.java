package com.nostalgi.engine;

import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.IScene;

import java.util.HashMap;

/**
 * Created by Kristoffer on 2016-07-15.
 */
public class BaseScene implements IScene {

    HashMap<String, IActor> scene = new HashMap<String, IActor>();

    @Override
    public void addActor(IActor actor) {
        scene.put(actor.getName(), actor);
    }

    @Override
    public IActor getActor(String name) {
        return scene.get(name);
    }
}
