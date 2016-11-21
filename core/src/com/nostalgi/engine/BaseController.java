package com.nostalgi.engine;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.nostalgi.engine.Utils.Guid;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.World.IWorld;


/**
 * Created by Kristoffer on 2016-07-06.
 * Basic character controller with movement in 8 directions
 * North, East, South and West.
 * NorthEast, SouthEast, SouthWest, NorthWest
 */


public abstract class BaseController implements IController, InputProcessor {

    private ICharacter character;
    private GestureDetector.GestureListener gestureListener;
    private IWorld world;
    private Guid id;



    public BaseController (IWorld world) {
        this.world = world;
    }

    @Override
    public void possessCharacter(ICharacter character) {
        this.character = character;
        character.setCurrentController(this);
    }

    @Override
    public ICharacter getCurrentPossessedCharacter() {
        return this.character;
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
    public void setControllerId(Guid uuid) {
        this.id = uuid;
    }

    @Override
    public Guid getControllerId() {
        return this.id;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
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

    public IWorld getWorld() {
        return this.world;
    }


}
