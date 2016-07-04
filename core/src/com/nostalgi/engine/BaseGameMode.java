package com.nostalgi.engine;

import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.IGameState;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class BaseGameMode implements IGameMode {

    protected IGameState gameState;

    public BaseGameMode (IGameState gameState) {
        this.gameState = gameState;
        this.gameState.setCurrentGameMode(this);
    }

    @Override
    public IGameState getGameState() {
        return gameState;
    }

    @Override
    public void setGameState(IGameState gameState) {
        this.gameState = gameState;
    }
}
