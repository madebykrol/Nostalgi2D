package com.nostalgi.engine;

import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.IGameState;
import com.nostalgi.engine.interfaces.ILevel;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public class BaseGameState implements IGameState {

    protected ILevel currentLevel;
    protected ICharacter currentPlayerCharacter;
    protected IController currentController;
    protected IGameMode currentGameMode;
    protected float gameTime;

    public BaseGameState(ILevel level, ICharacter playerCharacter) {
        this.currentLevel = level;
        this.currentPlayerCharacter = playerCharacter;
    }

    @Override
    public ILevel getCurrentLevel() {
        return this.currentLevel;
    }

    @Override
    public void setCurrentLevel(ILevel level) {
        this.currentLevel = level;
    }

    @Override
    public ICharacter getPlayerCharacter() {
        return this.currentPlayerCharacter;
    }

    @Override
    public void setPlayerCharacter(ICharacter character) {
        this.currentPlayerCharacter = character;
    }

    @Override
    public void setCurrentGameMode(IGameMode mode) {
        this.currentGameMode = mode;
    }

    @Override
    public IGameMode getCurrentGameMode() {
        return this.currentGameMode;
    }

    @Override
    public void setCurrentController(IController controller) {
        this.currentController = controller;
    }

    @Override
    public IController getCurrentController() {
        return this.currentController;
    }

    @Override
    public void update(float delta) {
        gameTime += delta;
        this.currentController.update(delta);
    }

    @Override
    public float getGameTime() {
        return this.gameTime;
    }

    @Override
    public Vector2 getGravity() {
        return new Vector2(0,0);
    }
}
