package com.nostalgi.engine.World;

import com.badlogic.gdx.utils.Timer;

import java.util.TimerTask;

/**
 * Created by ksdkrol on 2016-12-25.
 */
public class BaseTimeLine implements ITimeLine {

    private boolean looping;


    public BaseTimeLine (boolean looping, TimerTask task) {
        this.looping = looping;
    }

    @Override
    public boolean isLooping() {
        return false;
    }

    @Override
    public void tick(float dTime) {
        System.out.println("Im ticking every second");
    }

    @Override
    public void stop() {

    }

    @Override
    public void resume() {

    }
}
