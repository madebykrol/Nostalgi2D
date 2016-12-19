package com.nostalgi.engine;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.Navigation.IPathNode;
import com.nostalgi.engine.Utils.Guid;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.World.IWorld;

import java.util.ArrayList;


/**
 * Created by Kristoffer on 2016-07-06.
 * Basic character controller with movement in 8 directions
 * North, East, South and West.
 * NorthEast, SouthEast, SouthWest, NorthWest
 */


public abstract class BaseController implements IController, InputProcessor, GestureDetector.GestureListener {

    private ICharacter character;
    private GestureDetector.GestureListener gestureListener;
    private IWorld world;
    private Guid id;
    private IPlayerState playerState;



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
        return this;
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
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
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

    public void setPlayerState(IPlayerState playerState) {
        this.playerState = playerState;
    }

    public IPlayerState getPlayerState() {
        return this.playerState;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
