package com.nostalgi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nostalgi.engine.BaseGame;
import com.nostalgi.engine.BasePlayerState;
import com.nostalgi.engine.NostalgiBaseEngine;
import com.nostalgi.engine.NostalgiRenderer;
import com.nostalgi.engine.Render.NostalgiCamera;

public class Game extends BaseGame {

	NostalgiCamera camera;
	NostalgiRenderer tiledMapRenderer;
	Viewport viewport;

	int w;
	int h;

	public Game(boolean headless) {
		super(headless, false);
	}


	@Override
	public void create () {

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
        int unitScale = 32;

		camera = new NostalgiCamera(
				w, h,
				unitScale);
        tiledMapRenderer = new NostalgiRenderer((1/(float)unitScale));

		viewport = new FitViewport(20, 20, camera);
		viewport.apply();

		this.gameEngine = new NostalgiBaseEngine(camera, tiledMapRenderer, false);
        this.gameEngine.loadLevel("maps/grasslands");


		this.gameEngine.createNewPlayer(new BasePlayerState());

		this.gameEngine.init();
	}

	@Override
	public void dispose() {
		this.gameEngine.dispose();
	}


	@Override
	public void resize(int width, int height) {
		//viewport.update(width, height);
		//viewport.setWorldHeight(32);
		//viewport.setWorldWidth(32);
		//viewport.apply();
		camera.update();
	}

}
