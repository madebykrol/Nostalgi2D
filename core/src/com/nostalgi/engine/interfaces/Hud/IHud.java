package com.nostalgi.engine.interfaces.Hud;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public interface IHud extends Disposable {
    void init();
    void addModule(String name, IHudModule module);
    IHudModule getModule(String name);
    void update(float dTime);
    void draw(float dTime);
    void setScreenWidth(int width);
    void setScreenHeight(int height);
    void setScreenDimensions(int width, int height);
    void setStage(Stage stage);
    InputProcessor getInputProcessor();
}
