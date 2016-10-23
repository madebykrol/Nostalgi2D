package com.nostalgi.engine;

import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.Annotations.Replicated;
import com.nostalgi.engine.IO.Net.NetworkRole;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.ILevel;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public class BaseGameState implements IGameState {

    @Replicated
    private ILevel currentLevel;

    @Replicated
    private float gameTime;

    @Replicated
    private ArrayList<IPlayerState> players;

    NetworkRole networkRole;

    public BaseGameState() {
        networkRole = NetworkRole.ROLE_AUTHORITY;
    }

    @Override
    public ILevel getCurrentLevel() {
        return this.currentLevel;
    }

    @Override
    public void setCurrentLevel(ILevel level) {
        this.currentLevel = level;
    }

    @Override
    public ArrayList<IPlayerState> getPlayers() {
        return players;
    }

    @Override
    public void update(float delta) {
        gameTime += delta;
    }

    @Override
    public float getGameTime() {
        return this.gameTime;
    }

    @Override
    public Vector2 getGravity() {
        return new Vector2(0,0);
    }

    @Override
    public NetworkRole getNetworkRole() {
        return this.networkRole;
    }
}
