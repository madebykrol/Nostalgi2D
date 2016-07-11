package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.nostalgi.engine.NostalgiRenderer;
import com.nostalgi.render.NostalgiCamera;

/**
 * Created by Kristoffer on 2016-06-29.
 */
public interface IGameEngine {

    void init();
    void update();
    void render();
    void dispose();

    IGameState getGameState();
    IGameMode getGameMode();

    IHud getHud();
    void setHud(IHud hud);

    void setCurrentCamera(NostalgiCamera camera);
    NostalgiCamera getCurrentCamera();

    void setMapRenderer(NostalgiRenderer renderer);
    NostalgiRenderer getMapRenderer();

    InputProcessor getInputProcessor();
}
