package com.nostalgi.game.Controllers;

import com.nostalgi.engine.BaseController;
import com.nostalgi.engine.World.Audio.INoiseListener;
import com.nostalgi.engine.World.Audio.ISound;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.World.IWorld;

/**
 * Created by Krille on 14/12/2016.
 */

public class ExampleNPCAIController extends BaseController implements INoiseListener {
    public ExampleNPCAIController(IWorld world) {
        super(world);
        this.getWorld().getSoundSystem().setNoiseListener(this);
    }

    @Override
    public void possessCharacter(ICharacter character) {
        super.possessCharacter(character);

        System.out.println("Woopah We have possessed a character.");
    }

    @Override
    public void tick(float dTime) {

    }

    @Override
    public void reportNoise(ISound sound) {
        // React to sound
    }
}
