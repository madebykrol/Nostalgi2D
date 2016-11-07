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
import com.nostalgi.engine.States.AnimationState;
import com.nostalgi.engine.World.NostalgiWorld;
import com.nostalgi.engine.interfaces.Factories.IAnimationFactory;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.ILevel;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.game.Controllers.ExampleTopDownRPGController;
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
	IController playerController;
	Viewport viewport;

	int w;
	int h;

	IGameMode gameMode;
	IGameState gameState;
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

		// setup Playerstate
		playerState = new BasePlayerState();

		// setup Game state
		gameState = new BaseGameState();
		gameState.addPlayerState(playerState);

		gameMode = new BaseGameMode(this.gameState);

		int unitScale = 32;

		// setup physics world
		IHud hud = new BaseHud(w/2, h/2);
		hud.addModule("Main", new ExampleHudModule());
		hud.addModule("Debug", new DebugHudModule(gameState));

		gameMode.setHud(hud);

		camera = new NostalgiCamera(
				w, h,
				unitScale);

		world = new NostalgiWorld(new World(gameState.getGravity(), true), gameMode, camera);

		// Setup start level
		ILevel grassland = new GrassLandLevel(new TmxMapLoader(), new NostalgiActorFactory(world), new NostalgiWallFactory(world));
        gameState.setCurrentLevel(grassland);

		playerController = new ExampleTopDownRPGController(world);

		gameMode.addController(playerController);

		// setup map renderer.
		tiledMapRenderer = new NostalgiRenderer((1/(float)unitScale));
		tiledMapRenderer.loadLevel(grassland);

		world.setWorldBounds(grassland.getCameraBounds());

		world.setCameraPositionSafe(grassland.getCameraInitLocation());
		viewport = new StretchViewport(h, w, camera);


		this.gameEngine = new NostalgiBaseEngine(world, camera, tiledMapRenderer);
        ICharacter defaultPawn = createPlayerCharacter("DefaultPawn1");

        ICharacter defaultPawn2 = createPlayerCharacter("DefaultPawn2");
		this.playerController.possessCharacter(defaultPawn);

		this.gameEngine.init();
	}


	private ICharacter createPlayerCharacter(String name) {
        try {
            ICharacter playerCharacter = world.spawnActor(BasePlayerCharacter.class, name, true, new Vector2(8, 53));
            animationFactory = new NostalgiAnimationFactory();

            playerCharacter.addAnimation(AnimationState.WalkingEastAnimation,
                    animationFactory.createAnimation("Spritesheet/char_walk_east.png",
                            32, 64, 1, 2, 1f / 6f,
                            Animation.PlayMode.LOOP));

            playerCharacter.addAnimation(AnimationState.WalkingWestAnimation,
                    animationFactory.createAnimation("Spritesheet/char_walk_west.png",
                            32, 64, 1, 2, 1f / 6f,
                            Animation.PlayMode.LOOP));

            playerCharacter.addAnimation(AnimationState.WalkingNorthAnimation,
                    animationFactory.createAnimation("Spritesheet/char_walk_north.png",
                            32, 64, 1, 5, 1f / 6f,
                            Animation.PlayMode.LOOP));

            playerCharacter.addAnimation(AnimationState.WalkingSouthAnimation,
                    animationFactory.createAnimation("Spritesheet/char_walk_south.png",
                            32, 64, 1, 5, 1f / 6f,
                            Animation.PlayMode.LOOP));

            playerCharacter.addAnimation(AnimationState.IdleFaceSouthAnimation,
                    animationFactory.createAnimation("Spritesheet/char_idle.png",
                            32, 64, 1, 1, 1f / 6f,
                            Animation.PlayMode.LOOP));

            playerCharacter.addAnimation(AnimationState.IdleFaceNorthAnimation,
                    animationFactory.createAnimation("Spritesheet/char_idle_north.png",
                            32, 64, 1, 1, 1f / 6f,
                            Animation.PlayMode.LOOP));

            playerCharacter.addAnimation(AnimationState.IdleFaceEastAnimation,
                    animationFactory.createAnimation("Spritesheet/char_idle_east.png",
                            32, 64, 1, 1, 1f / 6f,
                            Animation.PlayMode.LOOP));

            playerCharacter.addAnimation(AnimationState.IdleFaceWestAnimation,
                    animationFactory.createAnimation("Spritesheet/char_idle_west.png",
                            32, 64, 1, 1, 1f / 6f,
                            Animation.PlayMode.LOOP));

            return playerCharacter;
        }  catch(FailedToSpawnActorException e) {
            System.out.println(e.getMessage());
        }

        return null;
	}

	@Override
	public void dispose() {
		this.animationFactory.dispose();
		this.gameEngine.dispose();
	}

}
