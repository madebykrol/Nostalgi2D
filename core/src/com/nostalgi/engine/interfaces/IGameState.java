package com.nostalgi.engine.interfaces;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface IGameState {

    ILevel getCurrentLevel();
    void setCurrentLevel(ILevel level);

    ICharacter getPlayerCharacter();
    void setPlayerCharacter(ICharacter character);

    void setCurrentGameMode(IGameMode mode);
    IGameMode getCurrentGameMode();

    void update(float delta);

    float getGameTime();
}
