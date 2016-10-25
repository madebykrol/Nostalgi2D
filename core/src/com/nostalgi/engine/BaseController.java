package com.nostalgi.engine;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.Render.NostalgiCamera;
import com.nostalgi.engine.interfaces.World.IWorld;

import java.util.ArrayList;


/**
 * Created by Kristoffer on 2016-07-06.
 * Basic character controller with movement in 8 directions
 * North, East, South and West.
 * NorthEast, SouthEast, SouthWest, NorthWest
 */


public abstract class BaseController implements IController, InputProcessor {

    private ICharacter currentPossessedCharacter;
    private GestureDetector.GestureListener gestureListener;
    private NostalgiCamera camera;

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


    public void setCamera(NostalgiCamera camera) {
        this.camera = camera;
    }

    public NostalgiCamera getCamera() {
        return this.camera;
    }
}
