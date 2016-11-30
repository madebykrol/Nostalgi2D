package com.nostalgi.engine;

import com.nostalgi.engine.IO.Net.NetworkRole;
import com.nostalgi.engine.interfaces.Hud.IHud;
import com.nostalgi.engine.interfaces.IController;
import com.nostalgi.engine.interfaces.IGameInstance;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.States.IPlayerState;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;
import com.nostalgi.engine.interfaces.World.IWorld;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class BaseGameMode implements IGameMode {

    private IGameState gameState;
    private ArrayList<IController> playerControllers =  new ArrayList<IController>();
    private IHud hud;
    private final NetworkRole isAuthority;
    private Class defaultControllerClass;
    private Class defaultGameStateClass;
    private Class defaultPlayerStateClass;
    private Class defaultCharacterClass;

    private IGameInstance gameInstance;

    public BaseGameMode (IWorld world) {

        this.isAuthority = NetworkRole.ROLE_AUTHORITY;
    }

    @Override
    public IGameState getGameState() {
        return gameState;
    }

    @Override
    public void setGameState(IGameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public <T extends IController> void setDefaultControllerClass(Class<T> defaultClass) {
        defaultControllerClass = defaultClass;
    }

    @Override
    public Class getDefaultControllerClass() {
        return defaultControllerClass;
    }

    @Override
    public void postDefaultControllerCreation(IController controller) {

    }

    @Override
    public <T extends IGameState> void setDefaultGameStateClass(Class<T> defaultClass) {
        this.defaultGameStateClass = defaultClass;
    }

    @Override
    public Class getDefaultGameStateClass() {
        return this.defaultGameStateClass;
    }

    @Override
    public void postDefaultGameStateCreation(IGameState gameState) {

    }

    @Override
    public <T extends ICharacter> void setDefaultCharacterClass(Class<T> defaultClass) {
        this.defaultCharacterClass = defaultClass;
    }

    @Override
    public Class getDefaultCharacterClass() {
        return this.defaultCharacterClass;
    }

    @Override
    public void postDefaultCharacterClassCreation(ICharacter character) {

    }

    @Override
    public <T extends IPlayerState> void setDefaultPlayerStateClass(Class<T> defaultClass) {
        this.defaultPlayerStateClass = defaultClass;
    }

    @Override
    public Class getDefaultPlayerStateClass() {
        return this.defaultPlayerStateClass;
    }

    @Override
    public void postDefaultPlayerStateCreation(IPlayerState playerState) {

    }

    @Override
    public void tick(float dTime) {
        this.gameState.update(dTime);
    }

    @Override
    public void setGameInstance(IGameInstance gameInstance) {
        this.gameInstance = gameInstance;
    }

    @Override
    public IGameInstance getGameInstance() {
        return this.gameInstance;
    }

    @Override
    public IController getCurrentController() {
        if(!this.playerControllers.isEmpty())
            return this.playerControllers.get(0);
        return null;
    }

    @Override
    public void setCurrentController(IController controller) {
        if(this.playerControllers.size() > 0) {
            this.playerControllers.set(0, controller);
        } else {
            this.addController(controller);
        }
    }

    @Override
    public IController getController(int player) {
        return this.playerControllers.get(player);
    }

    @Override
    public void addController(IController controller) {
        this.playerControllers.add(controller);
    }

    @Override
    public ArrayList<IController> getControllers() {
        return this.playerControllers;
    }

    @Override
    public IActor choosePlayerStart(IController player) {
        return null;
    }

    @Override
    public IHud getHud() {
        return hud;
    }

    @Override
    public void setHud(IHud hud) {
        this.hud = hud;
    }

    @Override
    public void dispose() {

    }
}
