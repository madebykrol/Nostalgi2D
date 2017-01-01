package com.nostalgi.engine.World;

import com.badlogic.gdx.utils.Disposable;

import java.util.TimerTask;

/**
 * Created by Krille on 10/12/2016.
 */
public interface ITimeManagementSystem extends Disposable {
    void setTimer(float tickAfterNSeconds, TimerTask task, boolean loop, boolean startImmediate);
    void stopTimer(TimerTask timer);
    boolean restartTimer(TimerTask timer);
}
