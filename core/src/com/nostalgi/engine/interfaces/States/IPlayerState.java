package com.nostalgi.engine.interfaces.States;

/**
 * Created by ksdkrol on 2016-07-11.
 */
public interface IPlayerState {
    void setPlayerName(String name);
    String getPlayerName();
    void setPlayerUniqueId(int id);
    int getPlayerUniqueId();

    void OnReconnected();
    void onDeactivated();
}
