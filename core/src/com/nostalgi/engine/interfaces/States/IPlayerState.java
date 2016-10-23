package com.nostalgi.engine.interfaces.States;

import java.util.UUID;

/**
 * Created by ksdkrol on 2016-07-11.
 */
public interface IPlayerState {
    void setPlayerName(String name);
    String getPlayerName();
    void setPlayerUniqueId(UUID id);
    UUID getPlayerUniqueId();

    void OnReconnected();
    void onDeactivated();
}
