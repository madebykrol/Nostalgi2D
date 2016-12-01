package com.nostalgi.engine.interfaces.Factories;

import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.NostalgiRenderer;
import com.nostalgi.engine.Render.NostalgiCamera;
import com.nostalgi.engine.interfaces.World.IWorld;

/**
 * Created by ksdkrol on 2016-12-01.
 */

public interface IWorldFactory {

    IWorld create(World world, NostalgiRenderer mapRenderer, NostalgiCamera camera);

}
