package com.nostalgi.engine.interfaces.Hud;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ksdkrol on 2016-07-12.
 */
public interface IHudModule {
    boolean isVisible();
    boolean isVisible(boolean visible);
    void init(Stage stage);
    void update(float dTime);
}
