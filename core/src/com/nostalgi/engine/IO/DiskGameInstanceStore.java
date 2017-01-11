package com.nostalgi.engine.IO;

import com.nostalgi.engine.IGameInstanceStore;
import com.nostalgi.engine.interfaces.ISaveSlot;

/**
 * Created by ksdkrol on 2017-01-11.
 */

public class DiskGameInstanceStore implements IGameInstanceStore {

    @Override
    public String read(ISaveSlot slot) {
        return null;
    }

    @Override
    public void write(String data, ISaveSlot slot) {

    }
}
