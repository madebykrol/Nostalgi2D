package com.nostalgi.engine;

import com.nostalgi.engine.Utils.CurveFloat;
import com.nostalgi.engine.Utils.Guid;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Managing timers.
 * Timers will not prolong your game. They will terminate when the application is destroyed.
 *
 * Created by ksdkrol on 2016-12-25.
 */
public class TimeManagementSystem implements ITimeManagementSystem {

    private Timer timer;
    private ArrayList<TimerReference> timers = new ArrayList<TimerReference>();

    private ArrayList<ITimeLine> timeLines = new ArrayList<ITimeLine>();

    public TimeManagementSystem() {
        this.timer = new Timer(true);
    }

    @Override
    public void setTimer(float tickAfterNSeconds, TimerTask task, boolean loop, boolean startImmediately) {

        long delay = (long)(tickAfterNSeconds * 1000);
        if(startImmediately) {
            delay = 0;
        }

        if(loop) {
            timer.scheduleAtFixedRate(task, delay, (long)(tickAfterNSeconds * 1000));
        } else {
            timer.schedule(task, delay);
        }

        TimerReference tr = new TimerReference();

        tr.loop = loop;
        tr.tickAfterNSeconds = tickAfterNSeconds;
        tr.startImmediately = startImmediately;
        tr.task = task;

        timers.add(tr);
    }

    @Override
    public void stopTimer(TimerTask timer) {
        timer.cancel();
    }

    @Override
    public boolean restartTimer(TimerTask timer) {
        stopTimer(timer);
        int index = timers.indexOf(timer);
        if(index >= 0) {
            TimerReference tr = timers.get(index);

            long delay = (long)(tr.tickAfterNSeconds * 1000);
            if(tr.startImmediately) {
                delay = 0;
            }

            if(tr.loop) {
                this.timer.scheduleAtFixedRate(tr.task, delay, (long)(tr.tickAfterNSeconds * 1000));
            } else {
                this.timer.schedule(tr.task, delay);
            }
        } else {
            return false;
        }

        return true;
    }


    @Override
    public void dispose() {
        timer.cancel();
        timer.purge();
    }

    public class TimerReference  {
        public float tickAfterNSeconds;
        public boolean startImmediately;
        public boolean loop;
        public TimerTask task;
        public boolean equals(Object object) {
            if (object == null)
                return false;
            if (object == this)
                return true;

            if(object instanceof TimerTask) {
                return (this.task == object);
            }

            return false;
        }
    }

    public interface ITimerTask {
        void run(float delta);
    }
    public interface ITimerHandle {
        Guid getTimerId();
    }

    public enum State {
        STOPPED, RUNNING, PAUSED, ENDED
    }
}
