package com.nostalgi.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.interfaces.IGameEngine;
import com.nostalgi.engine.interfaces.IGameMode;
import com.nostalgi.engine.interfaces.IGameState;
import com.nostalgi.engine.interfaces.IHud;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class NostalgiBaseEngine implements IGameEngine {

    protected IGameState state;
    protected IGameMode mode;
    protected IHud hud;
    protected SpriteBatch batch;
    protected OrthographicCamera currentCamera;
    protected MapRenderer mapRenderer;
    protected InputMultiplexer inputProcessor;

    protected World world;

    public NostalgiBaseEngine(Vector2 gravity) {
        world = new World(gravity, true);
    }

    public NostalgiBaseEngine(IGameState state, IGameMode mode, MapRenderer mapRenderer) {
        this(state.getGravity());
        this.state = state;
        this.mode = mode;

        this.batch = new SpriteBatch();
        this.mapRenderer = mapRenderer;
    }

    @Override
    public void init() {
        inputProcessor = new InputMultiplexer();

        if(this.hud.getInputProcessor() != null)
            inputProcessor.addProcessor(this.hud.getInputProcessor());

        if (this.state.getPlayerCharacter().getCurrentController().getGestureListener() != null)
            inputProcessor.addProcessor(new GestureDetector(
                    state.getPlayerCharacter().getCurrentController().getGestureListener()));

        if(this.state.getPlayerCharacter().getCurrentController().getInputProcessor() != null)
            inputProcessor.addProcessor(
                    state.getPlayerCharacter().getCurrentController().getInputProcessor());
    }

    @Override
    public void update() {
        this.state.update(Gdx.graphics.getDeltaTime());

        this.currentCamera.update();
        this.mapRenderer.setView(this.currentCamera);
    }

    @Override
    public void render() {
        this.mapRenderer.render();
        if(hud != null) {
            hud.draw(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        }
        ArrayList<Polygon> boundingBoxes = getGameState().getCurrentLevel().getMapBounds();
        ShapeRenderer batch = new ShapeRenderer();
        batch.setProjectionMatrix(this.currentCamera.combined);
        batch.setAutoShapeType(true);
        batch.begin();
            batch.setColor(1,0,0,1);

            for(Polygon shape : boundingBoxes) {
                float[] vertices = new float[shape.getVertices().length];
                if(vertices.length > 2) {

                    for(int i = 0; i < vertices.length; i++) {
                        vertices[i] = (shape.getVertices()[i] + shape.getX())/32f;
                        vertices[i+1] = (shape.getVertices()[i+1] + shape.getY())/32f;
                        i++;
                    }

                    batch.polygon(vertices);
                }
            }

        batch.end();
        batch.dispose();
    }

    @Override
    public void dispose() {
        this.hud.dispose();
    }


    @Override
    public IGameState getGameState() {
        return this.state;
    }

    @Override
    public IGameMode getGameMode() {
        return this.mode;
    }

    @Override
    public IHud getHud() {
        return this.hud;
    }

    @Override
    public void setHud(IHud hud) {
        this.hud = hud;
    }

    @Override
    public void setCurrentCamera(OrthographicCamera camera) {
        this.currentCamera = camera;
    }

    @Override
    public OrthographicCamera getCurrentCamera() {
        return this.currentCamera;
    }

    @Override
    public void setMapRenderer(MapRenderer renderer) {
        this.mapRenderer = renderer;
    }

    @Override
    public MapRenderer getMapRenderer() {
        return this.mapRenderer;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return inputProcessor;
    }
}
