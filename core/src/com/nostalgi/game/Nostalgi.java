package com.nostalgi.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nostalgi.engine.BaseController;
import com.nostalgi.engine.BaseGameMode;
import com.nostalgi.engine.BaseGameState;
import com.nostalgi.engine.BaseHud;
import com.nostalgi.engine.BasePlayerCharacter;
import com.nostalgi.engine.GrassLandLevel;
import com.nostalgi.engine.NostalgiBaseEngine;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameEngine;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.IGameState;
import com.nostalgi.engine.interfaces.IHud;
import com.nostalgi.render.NostalgiCamera;

public class Nostalgi extends ApplicationAdapter {

	NostalgiCamera camera;
	TiledMapRenderer tiledMapRenderer;
	IController playerController;
	Viewport viewport;

	int w;
	int h;

	IGameMode gameMode;
	IGameState gameState;
	IGameEngine gameEngine;


	@Override
	public void create () {

		this.gameState = new BaseGameState(
				new GrassLandLevel(new TmxMapLoader()),
				new BasePlayerCharacter());

		camera = new NostalgiCamera(
				1024, 768,
				gameState.getCurrentLevel().getBounds(),
				gameState.getCurrentLevel().getTileSize());

		camera.setPositionSafe(
				gameState.getCurrentLevel().getCameraInitLocation().x,
				gameState.getCurrentLevel().getCameraInitLocation().y);

		this.playerController = new BaseController(camera);

		this.playerController.possessCharacter(gameState.getPlayerCharacter());

		this.gameMode = new BaseGameMode(this.gameState);

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		this.tiledMapRenderer = new OrthogonalTiledMapRenderer(
				gameState.getCurrentLevel().getMap(),
				1 / (float)gameState.getCurrentLevel().getTileSize());

		this.gameEngine = new NostalgiBaseEngine(this.gameState, this.gameMode, tiledMapRenderer);

		IHud hud = new BaseHud(w, h, this.gameState);
		hud.init();

		this.gameEngine.setHud(hud);
		this.gameEngine.init();


		this.gameEngine.setCurrentCamera(camera);

		viewport = new StretchViewport(768, 1024, camera);

		Gdx.input.setInputProcessor(gameEngine.getInputProcessor());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameEngine.update();

		gameEngine.render();
	}


	@Override
	public void resize(int width, int height) {

	}

}
