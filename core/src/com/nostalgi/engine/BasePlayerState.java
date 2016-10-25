package com.nostalgi.engine;

import com.nostalgi.engine.interfaces.States.IPlayerState;

import java.util.UUID;

/**
 * Created by Kristoffer on 2016-07-26.
 */
public class BasePlayerState implements IPlayerState {



    @Override
    public void setPlayerName(String name) {

    }

    @Override
    public String getPlayerName() {
        return null;
    }

    @Override
    public void setPlayerUniqueId(UUID id) {

    }

    @Override
    public UUID getPlayerUniqueId() {
        return null;
    }

    @Override
    public void OnReconnected() {

    }

    @Override
    public void onDeactivated() {

    }
}
