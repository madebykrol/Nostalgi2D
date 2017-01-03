package com.nostalgi.engine.interfaces.States;

import com.nostalgi.engine.Utils.Guid;

/**
 * Created by ksdkrol on 2016-07-11.
 */
public interface IPlayerState {
    void setPlayerName(String name);
    String getPlayerName();
    void setPlayerUniqueId(Guid uuid);
    Guid getPlayerUniqueId();

    void OnReconnected();
    void onDeactivated();

    void updatePing(float ping);
    void setScore(float score);
    float getScore();

    void join(IPlayerState ps);
    
}
