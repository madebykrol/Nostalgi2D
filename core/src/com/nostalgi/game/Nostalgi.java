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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nostalgi.engine.BaseGameMode;
import com.nostalgi.engine.BaseGameState;
import com.nostalgi.engine.BasePlayerCharacter;
import com.nostalgi.engine.GrassLandLevel;
import com.nostalgi.engine.NostalgiBaseEngine;
import com.nostalgi.engine.interfaces.IGameEngine;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.IGameState;
import com.nostalgi.render.NostalgiCamera;

public class Nostalgi extends ApplicationAdapter implements InputProcessor {

	NostalgiCamera camera;
	TiledMapRenderer tiledMapRenderer;
	Viewport viewport;
	private BitmapFont font;
	private SpriteBatch batch;

	float w;
	float h;

	IGameMode gameMode;
	IGameState gameState;
	IGameEngine gameEngine;


	Vector3 last_touch_down = new Vector3();

	@Override
	public void create () {

		this.gameState = new BaseGameState(
				new GrassLandLevel(new TmxMapLoader()),
				new BasePlayerCharacter());

		this.gameMode = new BaseGameMode(this.gameState);

		this.gameEngine = new NostalgiBaseEngine(this.gameState, this.gameMode);

		this.gameEngine.init();

		tiledMapRenderer = new OrthogonalTiledMapRenderer(
				gameState.getCurrentLevel().getMap(),
				1 / (float)gameState.getCurrentLevel().getTileSize());

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new NostalgiCamera(
				1024, 768,
				gameState.getCurrentLevel().getHeight(), gameState.getCurrentLevel().getWidth(),
				gameState.getCurrentLevel().getTileSize());

		camera.setPositionSafe(
				gameState.getCurrentLevel().getCameraInitLocation().x,
				gameState.getCurrentLevel().getCameraInitLocation().y);

		camera.zoom = 1f;

		viewport = new StretchViewport(768, 1024, camera);

		font = new BitmapFont();
		batch = new SpriteBatch();

		camera.update();

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		batch.end();
	}


	@Override
	public void resize(int width, int height) {

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.LEFT)
			camera.setPositionSafe(camera.position.x - 1, camera.position.y);
		if(keycode == Input.Keys.RIGHT)
			camera.setPositionSafe(camera.position.x + 1, camera.position.y);
		if(keycode == Input.Keys.UP)
			camera.setPositionSafe(camera.position.x, camera.position.y + 1);
		if(keycode == Input.Keys.DOWN)
			camera.setPositionSafe(camera.position.x,camera.position.y-1);

		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		last_touch_down = new Vector3(screenX, screenY, 0);

		moveCameraToWorldLocation(last_touch_down);

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}


	public void moveCameraToWorldLocation(Vector3 screenTouch) {
		Vector3 worldPos = camera.unproject(screenTouch);

		camera.setPositionSafe(worldPos.x, worldPos.y);
		camera.update();
	}
}
