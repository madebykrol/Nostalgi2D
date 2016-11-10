package com.nostalgi.engine.interfaces.States;

import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.IO.Net.NetworkRole;
import com.nostalgi.engine.interfaces.World.ILevel;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface IGameState {

    ILevel getCurrentLevel();
    void setCurrentLevel(ILevel level);

    ArrayList<IPlayerState> getPlayers();
    void addPlayerState(IPlayerState playerState);
    IPlayerState getPlayerState(int player);

    void update(float delta);

    float getGameTime();


    NetworkRole getNetworkRole();
}
