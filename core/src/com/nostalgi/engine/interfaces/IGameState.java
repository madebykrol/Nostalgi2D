package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.math.Vector2;

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

    void setCurrentController(IController controller);
    IController getCurrentController();

    void update(float delta);

    float getGameTime();

    Vector2 getGravity();
}
