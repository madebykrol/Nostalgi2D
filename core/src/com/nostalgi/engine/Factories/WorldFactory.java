package com.nostalgi.engine.Factories;

import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.Navigation.NostalgiNavigationSystem;
import com.nostalgi.engine.NostalgiRenderer;
import com.nostalgi.engine.Render.NostalgiCamera;
import com.nostalgi.engine.World.NostalgiWorld;
import com.nostalgi.engine.World.Audio.SoundSystem;
import com.nostalgi.engine.TimeManagementSystem;
import com.nostalgi.engine.interfaces.Factories.IWorldFactory;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.physics.Box2DLights;

/**
 * Created by ksdkrol on 2016-12-01.
 */

public class WorldFactory implements IWorldFactory {
    @Override
    public IWorld create(World world, NostalgiRenderer mapRenderer, NostalgiCamera camera) {
        return  new NostalgiWorld(world,
                mapRenderer,camera,
                new NostalgiNavigationSystem(),
                new Box2DLights(world),
                new SoundSystem(),
                new TimeManagementSystem());
    }
}
