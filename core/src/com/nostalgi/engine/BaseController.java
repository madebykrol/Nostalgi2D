package com.nostalgi.engine;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.render.NostalgiCamera;


/**
 * Created by Kristoffer on 2016-07-06.
 */
public class BaseController implements IController, InputProcessor {

    protected ICharacter currentPossessedCharacter;
    protected GestureDetector.GestureListener gestureListener;
    protected NostalgiCamera camera;

    protected boolean leftIsPressed = false;
    protected boolean rightIsPressed = false;
    protected boolean upIsPressed = false;
    protected boolean downIsPressed = false;

    protected Vector2 lastPosition;

    public BaseController () {

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

        this.currentPossessedCharacter.getVelocity().x = 0;
        this.currentPossessedCharacter.getVelocity().y = 0;

        if (this.currentPossessedCharacter != null) {
            if (leftIsPressed) {
                this.currentPossessedCharacter.getVelocity().x -= 5f;
            }
            if (rightIsPressed) {
                this.currentPossessedCharacter.getVelocity().x = 5f;
            }
            if (upIsPressed) {
                this.currentPossessedCharacter.getVelocity().y = 5f;
            }
            if (downIsPressed) {
                this.currentPossessedCharacter.getVelocity().y -= 5f;
            }
        }
    }


    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.LEFT)
           this.leftIsPressed = true;
        if(keycode == Input.Keys.RIGHT)
           this.rightIsPressed = true;
        if(keycode == Input.Keys.UP)
            this.upIsPressed = true;
        if(keycode == Input.Keys.DOWN)
            this.downIsPressed = true;

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
        //camera.moveToWorldFromScreenLocation(screenX, screenY);
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

}
