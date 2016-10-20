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

    protected ArrayList<IActor> actorsCloseToLocation(Vector2 position, float distance) {

        float factor = 2f;
        if(distance <= 0) {
            factor = 0.5f;
        }

        float x1 = (position.x) + (distance / factor);
        float x2 = (position.x) - (distance / factor);

        float y1 = (position.y) + (distance / factor);
        float y2 = (position.y) - (distance / factor);

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


    public void setCamera(NostalgiCamera camera) {
        this.camera = camera;
    }

    public NostalgiCamera getCamera() {
        return this.camera;
    }
}
