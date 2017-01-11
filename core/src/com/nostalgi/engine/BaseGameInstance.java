package com.nostalgi.engine;

import com.nostalgi.engine.interfaces.IGameInstance;
import com.nostalgi.engine.interfaces.ILoadEventCallback;
import com.nostalgi.engine.interfaces.ISaveEventCallback;
import com.nostalgi.engine.interfaces.ISaveSlot;
import com.sun.org.apache.bcel.internal.generic.ILOAD;

import java.util.ArrayList;

/**
 * Created by Krille on 13/12/2016.
 */

public abstract class BaseGameInstance implements IGameInstance {

    private IGameInstanceStore gameInstanceStore;
    private ArrayList<ISaveSlot> slots = new ArrayList<ISaveSlot>();

    public BaseGameInstance(IGameInstanceStore store) {
        gameInstanceStore = store;

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public void save(ISaveSlot slot, ISaveEventCallback callback) {
        // Write data to storage.
        String dataToWrite = onSave();
    }

    @Override
    public void load(ISaveSlot slot, ILoadEventCallback callback) {
        // Read data from storage.
        onLoad("");
    }

    public ArrayList<ISaveSlot> getSaveSlots() {
        return slots;
    }
}
