package com.nostalgi.game;

import com.nostalgi.engine.Utils.Guid;
import com.nostalgi.engine.interfaces.States.IPlayerState;

/**
 * Created by Krille on 14/12/2016.
 */

public class ExamplePlayerState implements IPlayerState {
    @Override
    public void setPlayerName(String name) {

    }

    @Override
    public String getPlayerName() {
        return null;
    }

    @Override
    public void setPlayerUniqueId(Guid uuid) {

    }

    @Override
    public Guid getPlayerUniqueId() {
        return null;
    }

    @Override
    public void OnReconnected() {

    }

    @Override
    public void onDeactivated() {

    }

    @Override
    public void updatePing(float ping) {

    }

    @Override
    public void setScore(float score) {

    }

    @Override
    public float getScore() {
        return 0;
    }
}
