package com.nostalgi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nostalgi.engine.*;
import com.nostalgi.engine.Render.NostalgiCamera;
import com.nostalgi.engine.Utils.Guid;
import com.nostalgi.engine.interfaces.IGameInstance;

public class Game extends BaseGame {

	NostalgiRenderer tiledMapRenderer;
	IGameInstance gameInstance;

	int w;
	int h;
	int vW;
	int vH;
	int unitScale = 32;

	public Game(IGameInstance gameInstance, boolean headless) {
		this(gameInstance,64, 36, 32, headless);
	}

	public Game(IGameInstance gameInstance, int virtualWidth, int virtualHeight, int unitScale, boolean headless) {
		super(headless, false);
		this.unitScale = unitScale;
		this.vW = virtualWidth;
		this.vH = virtualHeight;
		this.gameInstance = gameInstance;
	}

	@Override
	public void create () {

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new NostalgiCamera(
				w, h,
				unitScale);
        tiledMapRenderer = new NostalgiRenderer((1/(float)unitScale));

		viewport = new FitViewport(vW, vH, camera);
		viewport.apply();

		this.gameEngine = new NostalgiBaseEngine(camera, tiledMapRenderer, gameInstance, false);
        this.gameEngine.loadLevel("maps/grasslands");

		this.gameEngine.createNewPlayer("player 1", Guid.createNew());

		this.gameEngine.init();
	}

	@Override
	public void dispose() {
		this.gameEngine.dispose();
	}

}
