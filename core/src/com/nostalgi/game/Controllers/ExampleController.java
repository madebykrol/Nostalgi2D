package com.nostalgi.game.Controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.BaseController;
import com.nostalgi.engine.Direction;
import com.nostalgi.engine.Render.NostalgiCamera;
import com.nostalgi.engine.States.AnimationStates;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.Hud.IHudModule;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.World.IWorld;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-09-14.
 */
public class ExampleController extends BaseController {

    private boolean leftIsPressed = false;
    private boolean rightIsPressed = false;
    private boolean upIsPressed = false;
    private boolean downIsPressed = false;
    private IHud hud;

    public ExampleController(NostalgiCamera camera, IWorld world, IHud hud) {
        super(camera, world);

        this.hud = hud;
    }

    @Override
    public void update(float dTime) {

        ICharacter currentPossessedCharacter = this.getCurrentPossessedCharacter();

        if (currentPossessedCharacter != null) {
            currentPossessedCharacter.stop();

            if(upIsPressed && rightIsPressed) {
                currentPossessedCharacter.face(Direction.NORTH_EAST);
                currentPossessedCharacter.setWalkingState(AnimationStates.WalkingNorthAnimation);
            } else if (upIsPressed && leftIsPressed) {
                currentPossessedCharacter.face(Direction.NORTH_WEST);
                currentPossessedCharacter.setWalkingState(AnimationStates.WalkingNorthAnimation);
            } else if (downIsPressed && rightIsPressed) {
                currentPossessedCharacter.face(Direction.SOUTH_EAST);
                currentPossessedCharacter.setWalkingState(AnimationStates.WalkingSouthAnimation);
            } else if(downIsPressed && leftIsPressed) {
                currentPossessedCharacter.face(Direction.SOUTH_WEST);
                currentPossessedCharacter.setWalkingState(AnimationStates.WalkingSouthAnimation);
            } else if(downIsPressed) {
                currentPossessedCharacter.face(Direction.SOUTH);
                currentPossessedCharacter.setWalkingState(AnimationStates.WalkingSouthAnimation);
            } else if(upIsPressed) {
                currentPossessedCharacter.face(Direction.NORTH);
                currentPossessedCharacter.setWalkingState(AnimationStates.WalkingNorthAnimation);
            } else if(leftIsPressed) {
                currentPossessedCharacter.face(Direction.WEST);
                currentPossessedCharacter.setWalkingState(AnimationStates.WalkingWestAnimation);
            } else if(rightIsPressed) {
                currentPossessedCharacter.face(Direction.EAST);
                currentPossessedCharacter.setWalkingState(AnimationStates.WalkingEastAnimation);
            }

            //this.currentPossessedCharacter.face(new Vector2(32,32));

            if(upIsPressed || downIsPressed || rightIsPressed || leftIsPressed) {
                currentPossessedCharacter.moveForward(5);
            } else {
                currentPossessedCharacter.setWalkingState(AnimationStates.IdleFaceSouthAnimation);
                float faceDirection = currentPossessedCharacter.getFacingDirection();

                if(faceDirection == Direction.SOUTH)
                    currentPossessedCharacter.setWalkingState(AnimationStates.IdleFaceSouthAnimation);

                if(faceDirection == Direction.EAST)
                    currentPossessedCharacter.setWalkingState(AnimationStates.IdleFaceEastAnimation);

                if(faceDirection == Direction.WEST)
                    currentPossessedCharacter.setWalkingState(AnimationStates.IdleFaceWestAnimation);

                if(faceDirection == Direction.NORTH)
                    currentPossessedCharacter.setWalkingState(AnimationStates.IdleFaceNorthAnimation);
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldPos = getCamera().unproject(new Vector3(screenX, screenY, 0));

        Vector2 worldPos2D = new Vector2(worldPos.x, worldPos.y);

        ArrayList<IActor> actors = actorsCloseToLocation(worldPos2D, 0f);
        if(!actors.isEmpty()) {
            IActor topActor = actors.get(0);
            if(topActor == this.getCurrentPossessedCharacter()) {
                System.out.println("Clicked player - Open character wheel");
                IHudModule module = hud.getModule("Debug");
                if(module != null) {
                    module.isVisible(!module.isVisible());
                }
            }
        }

        return false;
    }


    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        ICharacter currentPossessedCharacter = this.getCurrentPossessedCharacter();

        if(keycode == Input.Keys.LEFT) this.leftIsPressed = true;{

            currentPossessedCharacter.face(Direction.WEST);
        }
        if(keycode == Input.Keys.RIGHT) {
            this.rightIsPressed = true;
            currentPossessedCharacter.face(Direction.EAST);
        }
        if(keycode == Input.Keys.UP) {
            this.upIsPressed = true;
            currentPossessedCharacter.face(Direction.NORTH);
        }
        if(keycode == Input.Keys.DOWN) {
            this.downIsPressed = true;
            currentPossessedCharacter.face(Direction.SOUTH);
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT)
            this.leftIsPressed = false;
        if(keycode == Input.Keys.RIGHT)
            this.rightIsPressed = false;
        if(keycode == Input.Keys.UP)
            this.upIsPressed = false;
        if(keycode == Input.Keys.DOWN)
            this.downIsPressed = false;
        return true;
    }
}
