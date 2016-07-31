package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.NostalgiRenderer;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.Render.NostalgiCamera;

/**
 * Created by Kristoffer on 2016-06-29.
 */
public interface IGameEngine {

    void init();
    void update();
    void render();
    void dispose();
    IGameMode getGameMode();

    World getWorld();

    void setCurrentCamera(NostalgiCamera camera);
    NostalgiCamera getCurrentCamera();

    void setMapRenderer(NostalgiRenderer renderer);
    NostalgiRenderer getMapRenderer();

    InputProcessor getInputProcessor();
}
