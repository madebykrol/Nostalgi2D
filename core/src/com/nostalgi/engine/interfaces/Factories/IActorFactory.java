package com.nostalgi.engine.interfaces.Factories;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.utils.Disposable;
import com.nostalgi.engine.interfaces.World.IActor;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public interface IActorFactory extends Disposable {
    IActor fromMapObject(MapObject mapObject, IActor parent, float unitScale);
    IActor fromMapObject(MapObject mapObject, IActor parent);
    void destroyActor(IActor actor);
}
