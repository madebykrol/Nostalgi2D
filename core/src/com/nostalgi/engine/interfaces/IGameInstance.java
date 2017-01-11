package com.nostalgi.engine.interfaces;

import java.util.ArrayList;

/**
 * Created by Krille on 30/11/2016.
 */

public interface IGameInstance {
    void onStart();
    void onPause();
    void onResume();
    void onStop();
    void onEnd();

    void save(ISaveSlot slot, ISaveEventCallback callback);
    void load(ISaveSlot slot, ILoadEventCallback callback);

    String onSave();
    void onLoad(String data);
    ArrayList<ISaveSlot> getSaveSlots();
}
