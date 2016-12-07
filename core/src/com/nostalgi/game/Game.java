package com.nostalgi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nostalgi.engine.BaseGame;
import com.nostalgi.engine.BasePlayerState;
import com.nostalgi.engine.Exceptions.FailedToSpawnActorException;
import com.nostalgi.engine.NostalgiBaseEngine;
import com.nostalgi.engine.NostalgiRenderer;
import com.nostalgi.engine.Render.NostalgiCamera;
import com.nostalgi.engine.World.PointLightActor;

public class Game extends BaseGame {

	NostalgiRenderer tiledMapRenderer;

	int w;
	int h;
	int vW;
	int vH;
	int unitScale = 32;

	public Game(boolean headless) {
		this(64, 36, 32, headless);
	}

	public Game(int virtualWidth, int virtualHeight, int unitScale, boolean headless) {
		super(headless, false);
		this.unitScale = unitScale;
		this.vW = virtualWidth;
		this.vH = virtualHeight;
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

		this.gameEngine = new NostalgiBaseEngine(camera, tiledMapRenderer, null, false);
        this.gameEngine.loadLevel("maps/grasslands");


		this.gameEngine.createNewPlayer(new BasePlayerState());
		this.gameEngine.getWorld().getLightingSystem().updateAmbientLight(new Color(0.05f, 0.05f, 0.05f, 0.1f));

		try {
			this.gameEngine.getWorld().spawnActor(PointLightActor.class, "Light 1", false, new Vector2(5f, 5f), this.gameEngine.getWorld().getGameMode().getCurrentController().getCurrentPossessedCharacter(), null);
		} catch (FailedToSpawnActorException e) {
			e.printStackTrace();
		}


		this.gameEngine.init();
	}

	@Override
	public void dispose() {
		this.gameEngine.dispose();
	}

}
