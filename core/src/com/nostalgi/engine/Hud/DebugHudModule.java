package com.nostalgi.engine.Hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nostalgi.engine.interfaces.States.IGameState;

/**
 * Created by Kristoffer on 2016-07-12.
 */
public class DebugHudModule extends BaseHudModule {

    HudText fpsText;
    HudText timeText;

    IGameState state;

    public DebugHudModule(IGameState state) {
        this.state = state;
    }

    @Override
    public void init(Stage stage) {

        fpsText = new HudText("FPS: "+ Gdx.graphics.getFramesPerSecond(), 10f, 20f, Color.WHITE);
        timeText = new HudText("Game Time: 0", 10f, 50f, Color.WHITE);

        stage.addActor(fpsText);
        stage.addActor(timeText);
    }

    @Override
    public void update(float dTime) {

        fpsText.setVisible(this.isVisible());
        timeText.setVisible(this.isVisible());

        fpsText.update("FPS: " + Gdx.graphics.getFramesPerSecond());
        timeText.update("Game Time: " + state.getGameTime());

    }
}
