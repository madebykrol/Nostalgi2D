package com.nostalgi.engine.Hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nostalgi.engine.interfaces.Hud.IHudModule;

/**
 * Created by Kristoffer on 2016-07-12.
 */
public class DebugHudModule extends BaseHudModule {

    HudText fpsText;
    HudText timeText;

    float timePassed = 0;

    @Override
    public void init(Stage stage) {

        fpsText = new HudText("FPS: "+ Gdx.graphics.getFramesPerSecond(), 10f, 20f);
        timeText = new HudText("Game Time: 0", 10f, 50f);

        stage.addActor(fpsText);
        stage.addActor(timeText);
    }

    @Override
    public void update(float dTime) {

        fpsText.setVisible(this.isVisible());
        timeText.setVisible(this.isVisible());

        timePassed += dTime;
        fpsText.update("FPS: " + Gdx.graphics.getFramesPerSecond());
        timeText.update("Game Time: " + timePassed);

    }
}
