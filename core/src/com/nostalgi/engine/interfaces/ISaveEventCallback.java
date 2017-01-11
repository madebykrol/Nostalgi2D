package com.nostalgi.engine.interfaces;

/**
 * Created by ksdkrol on 2017-01-11.
 */
public interface ISaveEventCallback {
    void onSaveBegin();
    void onSaveFinished();
    void onSaveFailed(Exception e);
}
