package com.nostalgi.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.nostalgi.engine.interfaces.ILevel;

/**
 * Created by Kristoffer on 2016-07-06.
 */
public class NostalgiMapRenderer extends OrthogonalTiledMapRenderer {

    private final String groundLayer;
    private ShapeRenderer shapeRenderer;
    private ILevel level;

    public NostalgiMapRenderer(ILevel level, float unitScale) {
        this(level, unitScale, "Ground");

    }

    public NostalgiMapRenderer(ILevel level, float unitScale, String groundLayer) {
        super(level.getMap(), unitScale);
        this.level = level;
        this.groundLayer = groundLayer;
        shapeRenderer = new ShapeRenderer();
    }


    @Override
    public void render() {
        beginRender();

        for (MapLayer layer : map.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                renderTileLayer((TiledMapTileLayer) layer);

                if (layer.getName().equals(groundLayer)) {

                    this.getBatch().draw(new Texture(Gdx.files.internal("badlogic.jpg")), 1.5f, 1.5f, 1.5f, 1.5f);

                }
            } else {
                for (MapObject object : layer.getObjects()) {
                    renderObject(object);
                }
            }
        }
        endRender();

    }

    protected void renderPlayer() {

    }

    protected void renderNPCS() {

    }

    protected void renderMonsters() {

    }

    protected void renderItems() {

    }

    protected void renderChests() {

    }
}
