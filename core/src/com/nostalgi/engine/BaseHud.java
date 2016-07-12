package com.nostalgi.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nostalgi.engine.interfaces.Hud.IHudModule;
import com.nostalgi.engine.interfaces.States.IGameState;
import com.nostalgi.engine.interfaces.Hud.IHud;

import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by Kristoffer on 2016-07-05.
 */
public class BaseHud implements IHud {

    protected Stage stage;
    protected Batch batch;
    protected Skin skin;

    protected BitmapFont font;

    protected int screenWidth;
    protected int screenHeight;

    protected boolean yDown;
    protected IGameState gameState;

    protected HashMap<String, IHudModule> modules = new HashMap<String, IHudModule>();

    public BaseHud(int width, int height, IGameState gameState) {
        this(width, height, gameState, false);
    }

    public BaseHud(int width, int height, IGameState gameState, boolean yDown) {
        screenWidth = width;
        screenHeight = height;
        this.yDown = yDown;
        this.gameState = gameState;
    }

    @Override
    public void init() {
        this.stage = new Stage();
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();

    }

    @Override
    public void addModule(String name, IHudModule module) {
        this.modules.put(name, module);
    }

    @Override
    public IHudModule getModule(String name) {
        return null;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(float delta) {


        Iterator it = modules.entrySet().iterator();
        while(it.hasNext()) {
            IHudModule module = (IHudModule)it.next();
            module.draw(delta, stage);
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        stage.draw();

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        font.draw(batch, "Game Time: " + gameState.getGameTime(), 10, 50);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
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
    public void setFont(BitmapFont font) {
        this.font = font;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return this.stage;
    }
}
