package com.nostalgi.engine;

import com.nostalgi.engine.interfaces.States.IPlayerState;


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
    public void setPlayerUniqueId(int id) {

    }

    @Override
    public int getPlayerUniqueId() {
        return 0;
    }

    @Override
    public void OnReconnected() {

    }

    @Override
    public void onDeactivated() {

    }
}
