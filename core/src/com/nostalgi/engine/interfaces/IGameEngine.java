package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;

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

    void setCurrentCamera(OrthographicCamera camera);
    OrthographicCamera getCurrentCamera();

    void setMapRenderer(MapRenderer renderer);
    MapRenderer getMapRenderer();

    InputProcessor getInputProcessor();
}
