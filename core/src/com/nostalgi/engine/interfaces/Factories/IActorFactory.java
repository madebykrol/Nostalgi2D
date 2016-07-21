package com.nostalgi.engine.interfaces.Factories;

import com.badlogic.gdx.maps.MapObject;
import com.nostalgi.engine.interfaces.World.IActor;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public interface IActorFactory {
    IActor fromMapObject(MapObject mapObject, IActor parent);
}
