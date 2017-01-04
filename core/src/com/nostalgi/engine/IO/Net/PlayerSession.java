package com.nostalgi.engine.IO.Net;

import com.nostalgi.engine.Utils.Guid;
import com.nostalgi.engine.interfaces.IController;

/**
 * Created by ksdkrol on 2016-10-20.
 */

public class PlayerSession {
    private Guid playerId;
    private float ping;
    private String playerName;
    private IController currentController;

    public IController getCurrentController() {
        return currentController;
    }

    public void setCurrentController(IController currentController) {
        this.currentController = currentController;
    }

    public float getPing() {
        return ping;
    }

    public void setPing(float ping) {
        this.ping = ping;
    }

    public Guid getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Guid playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Guid) {
            return this.playerId.equals(obj);
        } else if (obj instanceof PlayerSession) {
            return this.playerId.equals(((PlayerSession) obj).getPlayerId());
        }

        return obj == this;
    }
}
