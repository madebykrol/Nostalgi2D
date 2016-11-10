package com.nostalgi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nostalgi.engine.BaseGame;
import com.nostalgi.engine.BaseGameMode;
import com.nostalgi.engine.BaseGameState;
import com.nostalgi.engine.BaseHud;
import com.nostalgi.engine.BasePlayerCharacter;
import com.nostalgi.engine.Exceptions.FailedToSpawnActorException;
import com.nostalgi.engine.Factories.NostalgiActorFactory;
import com.nostalgi.engine.Factories.NostalgiAnimationFactory;
import com.nostalgi.engine.Factories.NostalgiWallFactory;
import com.nostalgi.engine.Hud.DebugHudModule;
import com.nostalgi.engine.BasePlayerState;
import com.nostalgi.game.Hud.ExampleHudModule;
import com.nostalgi.engine.States.AnimationStates;
import com.nostalgi.engine.World.NostalgiWorld;
import com.nostalgi.engine.interfaces.Factories.IAnimationFactory;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.ILevel;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.game.Controllers.ExampleTopDownRPGController;
import com.nostalgi.game.Modes.ExampleTopDownRPGGameMode;
import com.nostalgi.game.levels.GrassLandLevel;
import com.nostalgi.engine.NostalgiBaseEngine;
import com.nostalgi.engine.NostalgiRenderer;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.Render.NostalgiCamera;

public class ExampleTopDownRPGGame extends BaseGame {

	NostalgiCamera camera;
	NostalgiRenderer tiledMapRenderer;
	Viewport viewport;

	int w;
	int h;

	IGameMode gameMode;
	IPlayerState playerState;

	IAnimationFactory animationFactory;
	IWorld world;

	public ExampleTopDownRPGGame(boolean headless) {
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

		world = new NostalgiWorld(new World(new Vector2(0,0), true), tiledMapRenderer, camera);

        // Setup start level
        ILevel grassland = new GrassLandLevel(new TmxMapLoader(), new NostalgiActorFactory(world), new NostalgiWallFactory(world));
        // setup map renderer.
        tiledMapRenderer.loadLevel(grassland);

        gameMode = new ExampleTopDownRPGGameMode(world);

        world.setGameMode(gameMode);


		world.setWorldBounds(grassland.getCameraBounds());

		world.setCameraPositionSafe(grassland.getCameraInitLocation());
		viewport = new StretchViewport(h, w, camera);

		this.gameEngine = new NostalgiBaseEngine(world, camera, tiledMapRenderer);

		this.gameEngine.init();
	}




	@Override
	public void dispose() {
        world.dispose();
		this.gameEngine.dispose();
	}

}
