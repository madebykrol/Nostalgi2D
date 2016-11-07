package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.utils.Disposable;
import com.nostalgi.engine.IO.Net.NetworkRole;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.States.IPlayerState;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface IGameMode extends Disposable{

    IGameState getGameState();
    void setGameState(IGameState gameState);

    IController getCurrentController();
    void setCurrentController(IController controller);

    IController getController(int player);
    void addController(IController controller);

    ArrayList<IController> getControllers();



    IHud getHud();
    void setHud(IHud hud);

    void tick(float dTime);

}
