package com.nostalgi.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.nostalgi.engine.interfaces.IGameEngine;

/**
 * Created by ksdkrol on 2016-09-14.
 */
public abstract class BaseGame extends ApplicationAdapter {

    protected IGameEngine gameEngine;

    private boolean headless = false;
    private boolean isAuthority = false;

    public BaseGame(boolean headless) {
        this.headless = headless;
    }

    public void setGameEngine(IGameEngine engine) {
        this.gameEngine = engine;
    }

    public IGameEngine getGameEngine() {
        return this.gameEngine;
    }

    @Override
    public void render () {
        gameEngine.update();

        if(!headless) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            gameEngine.render();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

}
