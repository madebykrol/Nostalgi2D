package com.nostalgi.engine;

import com.nostalgi.engine.Utils.CurveFloat;

/**
 * Each timeline is ran within a managed thread.
 * A timeline can be paused. which will stop the timeline running more frames
 * If a timeline has been stopped the thread is stopped and a new one will be started on the consequent play call.
 *
 * A timer will self terminate after a default time of 60 seconds. unless you change it..
 * Pay attention to performance if you raise this terminate timer to high as it will leave "dead threads" on your cpu.
 *
 * Created by ksdkrol on 2016-12-25.
 */
public class TimeLine implements ITimeLine {

    private boolean isReversing;
    private float currentTime = 0;
    private int currentFrame = 0;

    private float terminateTime = 60;
    private float suspendTime = 0;

    private CurveFloat curveFunction;

    private float start = 0;
    private float stop = 1;

    private float timeStep = 0;
    private float timeDiff = 0;

    private ITimeLineCallback frameCallback;

    private Object lock =  new Object();
    private TimeManagementSystem.State state = TimeManagementSystem.State.STOPPED;

    private Thread runner;

    private boolean isPlaying;
    public TimeLine(float start, float stop, ITimeLineCallback frameCallback, CurveFloat curveFunction) {
        this.start = start;
        this.stop = stop;
        this.frameCallback = frameCallback;
        this.curveFunction = curveFunction;
    }

    /**
     * Creates a new Timeline with a timestep.
     * @param start
     * @param stop
     * @param frameCallback
     * @param curveFunction
     * @param timeStep
     */
    public TimeLine(float start, float stop, ITimeLineCallback frameCallback, CurveFloat curveFunction, float timeStep) {
        this(start, stop, frameCallback, curveFunction);
    }

    @Override
    public void reverse() {
        this.isReversing = true;
        play();
    }

    @Override
    public void reverseFromEnd() {
        this.currentTime = stop;
        this.currentFrame = (this.timeStep >= 1) ? Math.round((stop*this.timeStep)) : Math.round((stop/this.timeStep));
        reverse();
    }

    @Override
    public void playFromStart() {
        this.currentTime = start;
        this.currentFrame = (int)start;
        play();
    }

    @Override
    public void play() {
        if(!isPlaying) {
            stop();
        }
        isPlaying = true;
        this.state = TimeManagementSystem.State.RUNNING;
        this.runner = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    double lastTimeMillis = System.currentTimeMillis() / 1000.0;
                    while (isPlaying) {
                        double currentTimeMillis = System.currentTimeMillis() / 1000.0;
                        float dTime = (float) (currentTimeMillis - lastTimeMillis);
                        lastTimeMillis = currentTimeMillis;
                        tick(dTime);
                    }
                }
            }
        });
        runner.start();
        onStarted();

    }

    @Override
    public void tick(float dTime) {
        if(this.state == TimeManagementSystem.State.RUNNING) {
            if (timeDiff >= timeStep) {
                timeDiff = 0;
                if (isReversing) {
                    if (this.currentTime >= start) {
                        this.frameCallback.keyFrame(currentFrame, this.curveFunction.curve(currentFrame));
                        this.currentTime -= (dTime + timeStep);
                        currentFrame--;
                    } else {
                        isPlaying = false;
                        onFinished();

                    }
                } else {
                    if (this.currentTime <= stop) {
                        this.frameCallback.keyFrame(currentFrame, this.curveFunction.curve(currentFrame));
                        this.currentTime += (dTime + timeStep);
                        currentFrame++;
                    } else {
                        isPlaying = false;
                        onFinished();
                    }
                }
            }
            timeDiff += dTime;
        } else {
            suspendTime += dTime;

            if(suspendTime >=  terminateTime) {
                suspendTime  = 0;
                stop();
            }
        }
    }

    @Override
    public void stop() {
        isPlaying = false;
        this.state = TimeManagementSystem.State.STOPPED;
    }

    @Override
    public void pause() {
        this.state = TimeManagementSystem.State.PAUSED;
    }

    @Override
    public void resume() {
        this.state = TimeManagementSystem.State.RUNNING;
    }

    @Override
    public void onStarted() {
        this.frameCallback.onStarted(this);
    }

    @Override
    public void onFinished() {
        this.frameCallback.onFinished(this);
    }

    @Override
    public boolean isPlaying() {
        return this.isPlaying;
    }

    @Override
    public void setTimeStep(float timeStep) {
        this.timeStep = timeStep;
    }


    public interface ITimeLineCallback {
        void keyFrame(int frame, float y);
        void onFinished(ITimeLine timeline);
        void onStarted(ITimeLine timeline);
    }
}
