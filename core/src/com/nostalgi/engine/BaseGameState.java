package com.nostalgi.engine;

import com.nostalgi.engine.interfaces.ICharacter;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.IGameState;
import com.nostalgi.engine.interfaces.ILevel;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public class BaseGameState implements IGameState {

    protected ILevel currentLevel;
    protected ICharacter currentPlayerCharacter;
    protected IGameMode currentGameMode;

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
}
