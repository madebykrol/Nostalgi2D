package com.nostalgi.game;

import com.nostalgi.engine.BaseGameInstance;
import com.nostalgi.engine.IGameInstanceStore;

/**
 * Created by ksdkrol on 2017-01-11.
 */

public class ExampleGameInstance extends BaseGameInstance {
    public ExampleGameInstance(IGameInstanceStore store) {
        super(store);
    }

    @Override
    public String onSave() {
        return null;
    }

    @Override
    public void onLoad(String data) {

    }
}
