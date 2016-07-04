package com.nostalgi.engine;

import com.nostalgi.engine.interfaces.IGameEngine;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.IGameState;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class NostalgiBaseEngine implements IGameEngine {

    protected IGameState state;
    protected IGameMode mode;

    public NostalgiBaseEngine(IGameState state, IGameMode mode) {
        this.state = state;
        this.mode = mode;
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
}
