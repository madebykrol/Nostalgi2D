package com.nostalgi.engine.interfaces;

/**
 * Created by ksdkrol on 2017-01-11.
 */
public interface ILoadEventCallback {
    void onLoadBegin();
    void onLoadFinished();
    void onLoadFailed(Exception e);
}
