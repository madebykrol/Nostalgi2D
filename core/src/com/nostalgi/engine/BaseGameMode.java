package com.nostalgi.engine;

import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.States.IPlayerState;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class BaseGameMode implements IGameMode {

    private IGameState gameState;
    private IPlayerState playerState;
    private IController playerController;
    private IHud hud;

    public BaseGameMode (IGameState gameState, IPlayerState playerState, IController playerController, IHud hud) {
        this.gameState = gameState;
        this.playerState = playerState;
        this.playerController = playerController;
        this.hud = hud;
    }

    @Override
    public IGameState getGameState() {
        return gameState;
    }

    @Override
    public void setGameState(IGameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public IPlayerState getPlayerState() {
        return this.playerState;
    }

    @Override
    public void setPlayerState(IPlayerState playerState) {
        this.playerState = playerState;
    }

    @Override
    public void update(float dTime) {

    }

    @Override
    public IController getCurrentController() {
        return this.playerController;
    }

    @Override
    public void setCurrentController(IController controller) {
        this.playerController = controller;
    }

    @Override
    public IHud getHud() {
        return hud;
    }

    @Override
    public void setHud(IHud hud) {
        this.hud = hud;
    }

    @Override
    public void dispose() {

    }
}
