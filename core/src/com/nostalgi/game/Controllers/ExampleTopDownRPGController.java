package com.nostalgi.game.Controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.BaseCharacter;
import com.nostalgi.engine.BaseController;
import com.nostalgi.engine.Direction;
import com.nostalgi.engine.Navigation.IPathFoundCallback;
import com.nostalgi.engine.Navigation.IPathNode;
import com.nostalgi.engine.States.AnimationState;
import com.nostalgi.engine.World.Wall;
import com.nostalgi.engine.interfaces.Hud.IHudModule;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.World.IInteractable;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.interfaces.World.IWorldObject;
import com.nostalgi.engine.physics.TraceHit;
import com.nostalgi.game.Hud.ExampleHudModule;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-09-14.
 */
public class ExampleTopDownRPGController extends BaseController implements IPathFoundCallback {

    private boolean leftIsPressed = false;
    private boolean rightIsPressed = false;
    private boolean upIsPressed = false;
    private boolean downIsPressed = false;

    private ArrayList<IPathNode> path;

    public ExampleTopDownRPGController(IWorld world) {
        super(world);
    }

    @Override
    public void tick(float dTime) {

        ICharacter currentPossessedCharacter = this.getCurrentPossessedCharacter();

        if (currentPossessedCharacter != null) {

            if(path != null && !path.isEmpty()) {
                IPathNode nextNode = this.getWorld().getNavigationSystem().getNextWayPoint(path,
                        currentPossessedCharacter.getPhysicsBody().getWorldCenter());
                if(nextNode != null) {
                    currentPossessedCharacter.lookAt(nextNode.getPosition());
                    currentPossessedCharacter.moveForward(5);
                } else {
                    currentPossessedCharacter.stop();
                }
            } else {
                handleMovement(currentPossessedCharacter, dTime);
            }

            handleLookingAtHudChanges(currentPossessedCharacter, dTime);
        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        IWorld world = getWorld();
        Vector2 worldPos2D = world.unproject(new Vector2(screenX, screenY));


        ArrayList<IActor> actors = world.actorsCloseToLocation(worldPos2D, 0f);
        if(!actors.isEmpty()) {
            IActor topActor = actors.get(0);
            if(topActor == this.getCurrentPossessedCharacter()) {
                System.out.println("Clicked player - Open character wheel");
                IHudModule module = world.getGameMode().getHud().getModule("Debug");
                if(module != null) {
                    module.isVisible(!module.isVisible());
                }
            } else {
                this.getCurrentPossessedCharacter().lookAt(topActor.getPhysicsBody().getWorldCenter());
            }
        } else {
            world.getNavigationSystem().findPathAsync(getCurrentPossessedCharacter().getPhysicsBody().getWorldCenter(), worldPos2D, this);
        }

        return false;
    }


    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        ICharacter currentPossessedCharacter = this.getCurrentPossessedCharacter();

        if(keycode == Input.Keys.LEFT) {
            this.leftIsPressed = true;
            currentPossessedCharacter.setRotation(Direction.WEST);
        }
        if(keycode == Input.Keys.RIGHT) {
            this.rightIsPressed = true;
            currentPossessedCharacter.setRotation(Direction.EAST);
        }
        if(keycode == Input.Keys.UP) {
            this.upIsPressed = true;
            currentPossessedCharacter.setRotation(Direction.NORTH);
        }
        if(keycode == Input.Keys.DOWN) {
            this.downIsPressed = true;
            currentPossessedCharacter.setRotation(Direction.SOUTH);
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

    private void handleLookingAtHudChanges(ICharacter currentPossessedCharacter, float dTime) {
        IWorld world = getWorld();
        ArrayList<Class> filters = new ArrayList<Class>();
        filters.add(BaseCharacter.class);
        filters.add(Wall.class);
        ArrayList<TraceHit> seeing = world.rayTrace(currentPossessedCharacter.getWorldPosition(), currentPossessedCharacter.getRotation(), 1.5f, filters, false);
        ExampleHudModule mainHud = (ExampleHudModule) world.getGameMode().getHud().getModule("Main");

        for(TraceHit hit : seeing) {
            IWorldObject actor = hit.object;
            if(actor instanceof IInteractable) {
                if(actor instanceof IActor) {
                    IActor lookingAt =  mainHud.getLookingAt();
                    if(!actor.equals(lookingAt)) {
                        mainHud.isVisible(true);
                        mainHud.setLookingAt((IActor) actor);
                    }
                }
            }
        }

        if(seeing.isEmpty()) {
            mainHud.isVisible(false);
            mainHud.setLookingAt(null);
        }
    }

    private void handleMovement(ICharacter currentPossessedCharacter, float dTime) {
        if(upIsPressed && rightIsPressed) {
            currentPossessedCharacter.setRotation(Direction.NORTH_EAST);
        } else if (upIsPressed && leftIsPressed) {
            currentPossessedCharacter.setRotation(Direction.NORTH_WEST);
        } else if (downIsPressed && rightIsPressed) {
            currentPossessedCharacter.setRotation(Direction.SOUTH_EAST);
        } else if(downIsPressed && leftIsPressed) {
            currentPossessedCharacter.setRotation(Direction.SOUTH_WEST);
        } else if(downIsPressed) {
            currentPossessedCharacter.setRotation(Direction.SOUTH);
        } else if(upIsPressed) {
            currentPossessedCharacter.setRotation(Direction.NORTH);
        } else if(leftIsPressed) {
            currentPossessedCharacter.setRotation(Direction.WEST);
        } else if(rightIsPressed) {
            currentPossessedCharacter.setRotation(Direction.EAST);
        }

        if(upIsPressed || downIsPressed || rightIsPressed || leftIsPressed) {
            currentPossessedCharacter.moveForward(5);
            currentPossessedCharacter.isMoving(true);
        } else {
            currentPossessedCharacter.isMoving(false);
            currentPossessedCharacter.stop();
            currentPossessedCharacter.setWalkingState(AnimationState.IdleFaceSouthAnimation);
        }
    }

    @Override
    public void onPathFound(ArrayList<IPathNode> path) {
        this.path = path;
    }
}
