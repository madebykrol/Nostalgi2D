package com.nostalgi.engine.interfaces;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface IGameMode {

    public com.nostalgi.engine.interfaces.States.IGameState getGameState();
    public void setGameState(com.nostalgi.engine.interfaces.States.IGameState gameState);


}
