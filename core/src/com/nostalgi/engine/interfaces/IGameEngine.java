package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.InputProcessor;
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

    com.nostalgi.engine.interfaces.States.IGameState getGameState();
    IGameMode getGameMode();

    com.nostalgi.engine.interfaces.Hud.IHud getHud();
    void setHud(com.nostalgi.engine.interfaces.Hud.IHud hud);

    void setCurrentCamera(NostalgiCamera camera);
    NostalgiCamera getCurrentCamera();

    void setMapRenderer(NostalgiRenderer renderer);
    NostalgiRenderer getMapRenderer();

    InputProcessor getInputProcessor();
}
