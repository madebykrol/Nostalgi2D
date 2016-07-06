package com.nostalgi.engine;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
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

    public BaseController (NostalgiCamera camera) {
        this.camera = camera;
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
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT)
            camera.setPositionSafe(camera.position.x - 1, camera.position.y);
        if(keycode == Input.Keys.RIGHT)
            camera.setPositionSafe(camera.position.x + 1, camera.position.y);
        if(keycode == Input.Keys.UP)
            camera.setPositionSafe(camera.position.x, camera.position.y + 1);
        if(keycode == Input.Keys.DOWN)
            camera.setPositionSafe(camera.position.x,camera.position.y-1);

        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        camera.moveToWorldFromScreenLocation(screenX, screenY);
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
