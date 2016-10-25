package com.nostalgi.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.nostalgi.engine.interfaces.Hud.IHudModule;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.Hud.IHud;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Kristoffer on 2016-07-05.
 */
public class BaseHud implements IHud {

    private Stage stage;
    private Skin skin;

    private int screenWidth;
    private int screenHeight;

    private boolean yDown;
    private OrthographicCamera HUDCamera;

    private HashMap<String, IHudModule> modules = new HashMap<String, IHudModule>();

    public BaseHud(int width, int height) {
        this(width, height, new OrthographicCamera(width, height), false);
    }

    public BaseHud(int width, int height, boolean yDown) {
        this(width, height, new OrthographicCamera(width, height), yDown);
    }

    public BaseHud(int width, int height, OrthographicCamera camera, boolean yDown) {
        screenWidth = width;
        screenHeight = height;
        this.yDown = yDown;
        this.HUDCamera = camera;

        this.stage = new Stage(new StretchViewport(screenWidth, screenHeight, HUDCamera));
        this.skin = new Skin();
    }

    @Override
    public void init() {

    }

    @Override
    public void addModule(String name, IHudModule module) {
        this.modules.put(name, module);
        module.init(stage);
    }

    @Override
    public IHudModule getModule(String name) {
        return this.modules.get(name);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(float delta) {

        for(Map.Entry<String, IHudModule> entry : modules.entrySet()) {
            IHudModule module = entry.getValue();
            module.update(delta);
        }
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void setScreenWidth(int width) {
        this.screenWidth = width;
    }

    @Override
    public void setScreenHeight(int height) {
        this.screenHeight  = height;
    }

    @Override
    public void setScreenDimensions(int width, int height) {
        this.screenWidth = width;
        this.screenHeight =  height;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return this.stage;
    }
}
