package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.InputProcessor;
import com.nostalgi.engine.NostalgiRenderer;
import com.nostalgi.engine.Render.NostalgiCamera;
import com.nostalgi.engine.Utils.Guid;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.IWorld;

/**
 * Created by Kristoffer on 2016-06-29.
 */
public interface IGameEngine {

    void init();
    void update();
    void render();
    void dispose();

    IWorld getWorld();

    void setCurrentCamera(NostalgiCamera camera);
    NostalgiCamera getCurrentCamera();

    void setMapRenderer(NostalgiRenderer renderer);
    NostalgiRenderer getMapRenderer();

    InputProcessor getInputProcessor();

    void loadLevel(String level);

    void createNewPlayer(String playerName, Guid playerId);


}
