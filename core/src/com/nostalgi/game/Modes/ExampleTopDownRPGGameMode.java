package com.nostalgi.game.Modes;

import com.badlogic.gdx.Gdx;
import com.nostalgi.engine.BaseGameMode;
import com.nostalgi.engine.BaseGameState;
import com.nostalgi.engine.BaseHud;
import com.nostalgi.engine.BasePlayerState;
import com.nostalgi.engine.TimeLine;
import com.nostalgi.engine.Hud.DebugHudModule;
import com.nostalgi.engine.ITimeLine;
import com.nostalgi.engine.Utils.CurveFloat;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.game.Actors.ExampleTopDownRPGCharacter;
import com.nostalgi.game.Actors.Pickups.IPickupable;
import com.nostalgi.game.Controllers.ExampleNPCAIController;
import com.nostalgi.game.Controllers.ExampleTopDownRPGController;
import com.nostalgi.game.ExamplePlayerState;
import com.nostalgi.game.Hud.ExampleHudModule;

import java.util.TimerTask;

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

        this.world.getTimeManagementSystem().setTimer(1, new TimerTask() {
            @Override
            public void run() {
                System.out.println("Run every second");
            }
        }, true, true);

        ITimeLine tl = new TimeLine(1, 6, new TimeLine.ITimeLineCallback() {
            @Override
            public void keyFrame(int currentFrame, float y) {
                System.out.println("Tick for 6 seconds:" + currentFrame + " curved y for x: " + y);
            }

            @Override
            public void onFinished(ITimeLine timeline) {

            }

            @Override
            public void onStarted(ITimeLine timeline) {

            }
        }, new CurveFloat(1, 6));
        tl.setTimeStep(1f);
        tl.play();
    }

    public void handlePickup(IPickupable item) {

    }

    public void init() {

    }

}
