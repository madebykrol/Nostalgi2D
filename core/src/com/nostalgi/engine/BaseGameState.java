package com.nostalgi.engine;

import com.nostalgi.engine.Annotations.Replicated;
import com.nostalgi.engine.IO.Net.NetworkRole;
import com.nostalgi.engine.States.GameState;
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
    private ArrayList<IPlayerState> players = new ArrayList<IPlayerState>();

    @Replicated
    private GameState gameState = GameState.STOPPED;

    NetworkRole networkRole;

    public BaseGameState() {
        networkRole = NetworkRole.ROLE_AUTHORITY;
    }

    @Override
    public void setState(GameState state) {
        gameState = state;
    }

    @Override
    public GameState getState() {
        return this.gameState;
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
    public void addPlayerState(IPlayerState playerState) {
        this.players.add(playerState);
    }

    @Override
    public IPlayerState getPlayerState(int player) {
        return null;
    }

    @Override
    public void update(float delta) {
        if(gameState == GameState.RUNNING)
            gameTime += delta;
    }

    @Override
    public float getGameTime() {
        return this.gameTime;
    }

    @Override
    public NetworkRole getNetworkRole() {
        return this.networkRole;
    }
}
