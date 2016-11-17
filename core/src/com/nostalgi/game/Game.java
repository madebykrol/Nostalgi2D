package com.nostalgi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.nostalgi.engine.BaseGame;
import com.nostalgi.engine.NostalgiBaseEngine;
import com.nostalgi.engine.NostalgiRenderer;
import com.nostalgi.engine.Render.NostalgiCamera;

public class Game extends BaseGame {

	NostalgiCamera camera;
	NostalgiRenderer tiledMapRenderer;
	StretchViewport viewport;

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

		viewport = new StretchViewport(w,h,camera);

		this.gameEngine = new NostalgiBaseEngine(camera, tiledMapRenderer);
        this.gameEngine.loadLevel("maps/grasslands");

		this.gameEngine.init();
	}

	@Override
	public void dispose() {
		this.gameEngine.dispose();
	}


	@Override
	public void resize(int width, int height) {
		//viewport.update(width, height);

		//camera.update();
	}

}
