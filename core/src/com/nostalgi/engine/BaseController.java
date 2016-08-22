package com.nostalgi.engine;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.States.AnimationStates;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.Render.NostalgiCamera;

import java.util.ArrayList;


/**
 * Created by Kristoffer on 2016-07-06.
 * Basic character controller with movement in 8 directions
 * North, East, South and West.
 * NorthEast, SouthEast, SouthWest, NorthWest
 */


public class BaseController implements IController, InputProcessor {

    private ICharacter currentPossessedCharacter;
    private GestureDetector.GestureListener gestureListener;
    private NostalgiCamera camera;

    private boolean leftIsPressed = false;
    private boolean rightIsPressed = false;
    private boolean upIsPressed = false;
    private boolean downIsPressed = false;

    private World world;

    public BaseController (NostalgiCamera camera, World world) {
        this.camera = camera;
        this.world = world;
    }

    @Override
    public void possessCharacter(ICharacter character) {
        this.currentPossessedCharacter = character;
        character.setCurrentController(this);
    }

    @Override
    public ICharacter getCurrentPossessedCharacter() {
        return this.currentPossessedCharacter;
    }

    @Override
    public GestureDetector.GestureListener getGestureListener() {
        return gestureListener;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return this;
    }

    @Override
    public void update(float dTime) {

        if (this.currentPossessedCharacter != null) {
            this.currentPossessedCharacter.stop();
            this.currentPossessedCharacter.setWalkingState(AnimationStates.IdleFaceSouthAnimation);

            boolean moving = false;
            float faceDirection = this.currentPossessedCharacter.getFacingDirection();

            if(faceDirection == Direction.SOUTH)
                this.currentPossessedCharacter.setWalkingState(AnimationStates.IdleFaceSouthAnimation);

            if(faceDirection == Direction.EAST)
                this.currentPossessedCharacter.setWalkingState(AnimationStates.IdleFaceEastAnimation);

            if(faceDirection == Direction.WEST)
                this.currentPossessedCharacter.setWalkingState(AnimationStates.IdleFaceWestAnimation);

            if(faceDirection == Direction.NORTH)
                this.currentPossessedCharacter.setWalkingState(AnimationStates.IdleFaceNorthAnimation);

            if(upIsPressed && rightIsPressed) {
                this.currentPossessedCharacter.face(Direction.NORTH_EAST);
            }

            if(downIsPressed && rightIsPressed) {
                this.currentPossessedCharacter.face(Direction.SOUTH_EAST);
            }

            if(upIsPressed && leftIsPressed) {
                this.currentPossessedCharacter.face(Direction.NORTH_WEST);
            }

            if(downIsPressed && rightIsPressed) {
                this.currentPossessedCharacter.face(Direction.SOUTH_WEST);
            }

            if (leftIsPressed) {
                moving = true;
                this.currentPossessedCharacter.setWalkingState(AnimationStates.WalkingWestAnimation);
            }
            if (rightIsPressed) {
                moving = true;
                this.currentPossessedCharacter.setWalkingState(AnimationStates.WalkingEastAnimation);
            }
            if (upIsPressed) {
                moving = true;
                this.currentPossessedCharacter.setWalkingState(AnimationStates.WalkingNorthAnimation);
            }
            if (downIsPressed) {
                moving = true;
                this.currentPossessedCharacter.setWalkingState(AnimationStates.WalkingSouthAnimation);
            }

            //this.currentPossessedCharacter.face(new Vector2(32,32));

            if((moving)) {
                this.currentPossessedCharacter.moveForward(5);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.LEFT) {
            this.leftIsPressed = true;
            this.currentPossessedCharacter.face(Direction.WEST);
        }
        if(keycode == Input.Keys.RIGHT) {
            this.rightIsPressed = true;
            this.currentPossessedCharacter.face(Direction.EAST);
        }
        if(keycode == Input.Keys.UP) {
            this.upIsPressed = true;
            this.currentPossessedCharacter.face(Direction.NORTH);
        }
        if(keycode == Input.Keys.DOWN) {
            this.downIsPressed = true;
            this.currentPossessedCharacter.face(Direction.SOUTH);
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

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldPos = camera.unproject(new Vector3(screenX, screenY, 0));

        Vector2 worldPos2D = new Vector2(worldPos.x, worldPos.y);

        ArrayList<IActor> actors = actorsCloseToLocation(worldPos2D, 1f);
        if(!actors.isEmpty()) {
            IActor topActor = actors.get(0);
            if(topActor == this.currentPossessedCharacter) {
                System.out.println("Clicked player - Open character wheel");
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    protected ArrayList<IActor> actorsCloseToLocation(Vector2 position, float distance) {

        float x1 = (position.x) + (distance/2);
        float x2 = (position.x) - (distance/2);

        float y1 = (position.y) + (distance/2);
        float y2 = (position.y) - (distance/2);

        final ArrayList<IActor> actors = new ArrayList<IActor>();

        world.QueryAABB(new QueryCallback() {
                            @Override
                            public boolean reportFixture(Fixture fixture) {
                                Object o = fixture.getBody().getUserData();
                                if (o instanceof IActor) {
                                    actors.add((IActor) o);
                                }
                                return true;
                            }
                        },
                x2, y2, x1, y1);

        return actors;
    }

}
