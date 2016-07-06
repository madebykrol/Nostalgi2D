package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public interface IHud extends Disposable {
    void init();
    void update(float delta);
    void draw(float delta);
    void setScreenWidth(int width);
    void setScreenHeight(int height);
    void setScreenDimensions(int width, int height);
    void setStage(Stage stage);
    void setFont(BitmapFont font);
    InputProcessor getInputProcessor();
}
