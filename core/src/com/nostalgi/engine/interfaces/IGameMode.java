package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface IGameMode extends Disposable{

    IGameState getGameState();
    void setGameState(IGameState gameState);

    <T extends IController> void setDefaultAIControllerClass(Class<T> defaultClass);
    Class getDefaultAIControllerClass();
    void postDefaultAIControllerCreation(IController controller);

    <T extends IController> void setDefaultControllerClass(Class<T> defaultClass);
    Class getDefaultControllerClass();
    void postDefaultControllerCreation(IController controller);

    <T extends IGameState> void setDefaultGameStateClass(Class<T> defaultClass);
    Class getDefaultGameStateClass();
    void postDefaultGameStateCreation(IGameState gameState);

    <T extends ICharacter> void setDefaultCharacterClass(Class<T> defaultClass);
    Class getDefaultCharacterClass();
    void postDefaultCharacterClassCreation(ICharacter character);

    <T extends IPlayerState> void setDefaultPlayerStateClass(Class<T> defaultClass);
    Class getDefaultPlayerStateClass();
    void postDefaultPlayerStateCreation(IPlayerState playerState);

    Vector2 choosePlayerStart(IController player);

    IHud getHud();
    void setHud(IHud hud);

    void tick(float dTime);

    void setGameInstance(IGameInstance gameInstance);
    IGameInstance getGameInstance();

    void init();

}
