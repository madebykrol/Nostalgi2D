package com.nostalgi.engine;

import com.nostalgi.engine.interfaces.ISaveSlot;

/**
 * Created by ksdkrol on 2017-01-11.
 */
public interface IGameInstanceStore {
    String read(ISaveSlot slot);
    void write(String data, ISaveSlot slot);
}
