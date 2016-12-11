package com.nostalgi.game.Modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.BaseGameMode;
import com.nostalgi.engine.BaseGameState;
import com.nostalgi.engine.BaseHud;
import com.nostalgi.engine.BasePlayerCharacter;
import com.nostalgi.engine.BasePlayerState;
import com.nostalgi.engine.Exceptions.FailedToSpawnActorException;
import com.nostalgi.engine.Factories.NostalgiAnimationFactory;
import com.nostalgi.engine.Hud.DebugHudModule;
import com.nostalgi.engine.States.AnimationState;
import com.nostalgi.engine.interfaces.Factories.IAnimationFactory;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.game.Actors.ExampleTopDownRPGCharacter;
import com.nostalgi.game.Controllers.ExampleTopDownRPGController;
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
        this.setDefaultCharacterClass(ExampleTopDownRPGCharacter.class);
    }

}
