package com.nostalgi.engine;

import com.nostalgi.engine.Utils.Guid;
import com.nostalgi.engine.interfaces.States.IPlayerState;


/**
 * Created by Kristoffer on 2016-07-26.
 */
public class BasePlayerState implements IPlayerState {

    private Guid uuid;
    private String playerName;
    private float ping;
    private float score;


    @Override
    public void setPlayerName(String name) {
        this.playerName = name;
    }

    @Override
    public String getPlayerName() {
        return Guid.createNew().toString();
    }

    @Override
    public void setPlayerUniqueId(Guid uuid) {
        this.uuid = uuid;
    }

    @Override
    public Guid getPlayerUniqueId() {
        return this.uuid;
    }

    @Override
    public void OnReconnected() {

    }

    @Override
    public void onDeactivated() {

    }

    @Override
    public void updatePing(float ping) {
        this.ping = ping;
    }

    @Override
    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public float getScore() {
        return this.score;
    }


}
