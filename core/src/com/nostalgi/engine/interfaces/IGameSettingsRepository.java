package com.nostalgi.engine.interfaces;

/**
 * Created by Krille on 20/11/2016.
 */

public interface IGameSettingsRepository {
    float getZoom();
    void setZoom(float zoom);

    <T> T getObject(String key);
    <T> void setObject(T object);

    void commit();
}
