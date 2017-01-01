package com.nostalgi.engine.World;

/**
 * Created by ksdkrol on 2016-12-25.
 */

public interface ITimeLine {
    boolean isLooping();
    void tick(float dTime);
    void stop();
    void resume();
}
