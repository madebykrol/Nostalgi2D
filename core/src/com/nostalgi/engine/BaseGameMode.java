package com.nostalgi.engine;

import com.nostalgi.engine.IO.Net.NetworkRole;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.States.IPlayerState;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class BaseGameMode implements IGameMode {

    private IGameState gameState;
    private ArrayList<IController> playerControllers =  new ArrayList<IController>();
    private IHud hud;
    private final NetworkRole isAuthority;

    public BaseGameMode (IGameState gameState) {
        this.gameState = gameState;
        this.isAuthority = NetworkRole.ROLE_AUTHORITY;
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
    public void tick(float dTime) {
        this.gameState.update(dTime);
    }

    @Override
    public IController getCurrentController() {
        return this.playerControllers.get(0);
    }

    @Override
    public void setCurrentController(IController controller) {
        this.playerControllers.set(0,controller);
    }

    @Override
    public IController getController(int player) {
        return this.playerControllers.get(player);
    }

    @Override
    public void addController(IController controller) {
        this.playerControllers.add(controller);
    }

    @Override
    public ArrayList<IController> getControllers() {
        return this.playerControllers;
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
