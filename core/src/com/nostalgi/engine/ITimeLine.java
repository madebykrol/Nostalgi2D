package com.nostalgi.engine;

/**
 * Created by ksdkrol on 2016-12-25.
 */

public interface ITimeLine {

    void reverse();
    void reverseFromEnd();
    void playFromStart();
    void play();
    void tick(float dTime);
    void stop();
    void pause();
    void resume();
    void onStarted();
    void onFinished();
    boolean isPlaying();
    void setTimeStep(float timeStep);

}
