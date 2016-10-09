package com.nostalgi.engine.Hud;

import com.nostalgi.engine.interfaces.Hud.IHudModule;

/**
 * Created by Krille on 09/10/2016.
 */

public abstract class BaseHudModule implements IHudModule {

    private boolean visible;

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public boolean isVisible(boolean visible) {
        return this.visible = visible;
    }
}
