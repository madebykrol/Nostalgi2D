package com.nostalgi.game.Modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.BaseGameMode;
import com.nostalgi.engine.BaseGameState;
import com.nostalgi.engine.BaseHud;
import com.nostalgi.engine.BasePlayerState;
import com.nostalgi.engine.Exceptions.FailedToSpawnActorException;
import com.nostalgi.engine.Hud.DebugHudModule;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.game.Actors.ExampleTopDownRPGAICharacter;
import com.nostalgi.game.Actors.ExampleTopDownRPGCharacter;
import com.nostalgi.game.Controllers.ExampleNPCAIController;
import com.nostalgi.game.Controllers.ExampleTopDownRPGController;
import com.nostalgi.game.ExamplePlayerState;
import com.nostalgi.game.Hud.ExampleHudModule;

/**
 * Created by Krille on 05/11/2016.
 */

public class ExampleTopDownRPGGameMode extends BaseGameMode {
    private IWorld world;
    public ExampleTopDownRPGGameMode(IWorld world) {
        super(world);

        this.world = world;

        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        // setup Playerstate
        IPlayerState playerState = new BasePlayerState();

        // setup Game state
        IGameState gameState = new BaseGameState();
        gameState.addPlayerState(playerState);

        this.setGameState(gameState);

        // setup physics world
        IHud hud = new BaseHud(w/2, h/2);
        hud.addModule("Main", new ExampleHudModule(world));
        hud.addModule("Debug", new DebugHudModule(gameState));

        this.setHud(hud);

        this.setDefaultControllerClass(ExampleTopDownRPGController.class);
        this.setDefaultAIControllerClass(ExampleNPCAIController.class);
        this.setDefaultCharacterClass(ExampleTopDownRPGCharacter.class);
        this.setDefaultPlayerStateClass(ExamplePlayerState.class);
    }

    public void init() {
        try {
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player", true, new Vector2(32f, 32f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player2", true, new Vector2(33f, 33f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player3", true, new Vector2(20f, 20f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player4", true, new Vector2(40f, 40f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player5", true, new Vector2(51f, 51f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player6", true, new Vector2(22f, 22f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player7", true, new Vector2(37f, 37f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player8", true, new Vector2(35f, 35f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player9", true, new Vector2(38f, 38f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player10", true, new Vector2(43f, 43f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player11", true, new Vector2(40f, 40f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player12", true, new Vector2(41f, 41f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player13", true, new Vector2(42f, 42f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player14", true, new Vector2(21f, 11f));
            this.world.spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player15", true, new Vector2(44f, 44f));

        } catch (FailedToSpawnActorException e) {
            e.printStackTrace();
        }
    }

}
