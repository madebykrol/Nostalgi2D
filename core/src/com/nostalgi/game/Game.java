package com.nostalgi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nostalgi.engine.BaseGame;
import com.nostalgi.engine.Factories.NostalgiActorFactory;
import com.nostalgi.engine.Factories.NostalgiWallFactory;
import com.nostalgi.engine.NostalgiBaseEngine;
import com.nostalgi.engine.NostalgiRenderer;
import com.nostalgi.engine.Render.NostalgiCamera;
import com.nostalgi.engine.World.NostalgiWorld;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.World.ILevel;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.game.Modes.ExampleTopDownRPGGameMode;
import com.nostalgi.game.levels.GrassLandLevel;

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

		viewport = new StretchViewport(h, w, camera);

		this.gameEngine = new NostalgiBaseEngine(camera, tiledMapRenderer);
        this.gameEngine.loadLevel("maps/grasslands");

		this.gameEngine.init();
	}

	@Override
	public void dispose() {
		this.gameEngine.dispose();
	}

}
