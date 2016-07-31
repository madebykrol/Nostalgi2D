package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.utils.Disposable;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.States.IPlayerState;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface IGameMode extends Disposable{

    IGameState getGameState();
    void setGameState(IGameState gameState);

    IPlayerState getPlayerState();
    void setPlayerState(IPlayerState playerState);

    IController getCurrentController();
    void setCurrentController(IController controller);

    IHud getHud();
    void setHud(IHud hud);

    void update(float dTime);

}
