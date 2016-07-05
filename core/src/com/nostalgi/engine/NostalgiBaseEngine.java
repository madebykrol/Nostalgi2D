package com.nostalgi.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nostalgi.engine.interfaces.IGameEngine;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.IGameState;
import com.nostalgi.engine.interfaces.IHud;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class NostalgiBaseEngine implements IGameEngine {

    protected IGameState state;
    protected IGameMode mode;
    protected IHud hud;

    protected SpriteBatch batch;

    public NostalgiBaseEngine(IGameState state, IGameMode mode) {
        this.state = state;
        this.mode = mode;

        this.batch = new SpriteBatch();
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public IGameState getGameState() {
        return this.state;
    }

    @Override
    public IGameMode getGameMode() {
        return this.mode;
    }

    @Override
    public IHud getHud() {
        return this.hud;
    }

    @Override
    public void setHud(IHud hud) {
        this.hud = hud;
    }
}
