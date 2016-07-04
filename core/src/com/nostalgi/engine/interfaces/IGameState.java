package com.nostalgi.engine.interfaces;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface IGameState {

    public ILevel getCurrentLevel();
    public void setCurrentLevel(ILevel level);

    public ICharacter getPlayerCharacter();
    public void setPlayerCharacter(ICharacter character);

    public void setCurrentGameMode(IGameMode mode);
    public IGameMode getCurrentGameMode();
}
