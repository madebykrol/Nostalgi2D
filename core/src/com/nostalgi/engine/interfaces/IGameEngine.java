package com.nostalgi.engine.interfaces;

/**
 * Created by Kristoffer on 2016-06-29.
 */
public interface IGameEngine {

    public void init();
    public void update();
    public IGameState getGameState();
    public IGameMode getGameMode();

    public IHud getHud();
    public void setHud(IHud hud);

}
