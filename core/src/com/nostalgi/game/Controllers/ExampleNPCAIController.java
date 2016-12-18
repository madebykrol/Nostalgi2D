package com.nostalgi.game.Controllers;

import com.nostalgi.engine.BaseController;
import com.nostalgi.engine.Navigation.IPathFoundCallback;
import com.nostalgi.engine.Navigation.IPathNode;
import com.nostalgi.engine.States.AnimationState;
import com.nostalgi.engine.World.Audio.INoiseListener;
import com.nostalgi.engine.World.Audio.ISound;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.World.IWorld;

import java.util.ArrayList;

/**
 * Created by Krille on 14/12/2016.
 */

public class ExampleNPCAIController extends BaseController implements INoiseListener, IPathFoundCallback {

    ArrayList<IPathNode> path;

    float lastCheck = 0;

    public ExampleNPCAIController(IWorld world) {
        super(world);
        this.getWorld().getSoundSystem().setNoiseListener(this);
    }

    @Override
    public void possessCharacter(ICharacter character) {
        super.possessCharacter(character);

        System.out.println("Woopah We have possessed a character.");

        // locatePlayer();
    }

    @Override
    public void tick(float dTime) {
        lastCheck += dTime;

        if(lastCheck >= 2) {
            //locatePlayer();
            lastCheck = 0;
        }
        ICharacter currentPossessedCharacter = this.getCurrentPossessedCharacter();
        if(path != null && !path.isEmpty()) {
            IPathNode nextNode = this.getWorld().getNavigationSystem().getNextWayPoint(path,
                    currentPossessedCharacter.getPhysicsBody().getWorldCenter());
            if(nextNode != null) {
                currentPossessedCharacter.lookAt(nextNode.getPosition());
                currentPossessedCharacter.moveForward(4);
            } else {
                currentPossessedCharacter.stop();
            }
        } else {
            if(this.getCurrentPossessedCharacter().isMoving()) {
                currentPossessedCharacter.stop();
                currentPossessedCharacter.setWalkingState(AnimationState.IdleFaceSouthAnimation);
            }
        }

    }

    @Override
    public void reportNoise(ISound sound) {
        // React to sound
    }

    @Override
    public void onPathFound(ArrayList<IPathNode> path) {
        this.path = path;
    }

    private void locatePlayer() {
        this.getWorld().getNavigationSystem().findPathAsync(this.getCurrentPossessedCharacter(),
                this.getWorld().getGameMode().getCurrentController().getCurrentPossessedCharacter(),
                this.getWorld(),
                this);
    }
}
