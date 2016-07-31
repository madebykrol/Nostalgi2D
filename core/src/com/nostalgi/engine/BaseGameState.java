package com.nostalgi.engine;

import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.World.ILevel;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public class BaseGameState implements IGameState {

    private ILevel currentLevel;
    private float gameTime;

    public BaseGameState() {
    }

    @Override
    public ILevel getCurrentLevel() {
        return this.currentLevel;
    }

    @Override
    public void setCurrentLevel(ILevel level) {
        this.currentLevel = level;
    }

    @Override
    public void update(float delta) {
        gameTime += delta;
    }

    @Override
    public float getGameTime() {
        return this.gameTime;
    }

    @Override
    public Vector2 getGravity() {
        return new Vector2(0,0);
    }
}
