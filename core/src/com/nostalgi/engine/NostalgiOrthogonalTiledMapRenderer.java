package com.nostalgi.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Kristoffer on 2016-07-06.
 */
public class NostalgiOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {

    private final String groundLayer;
    private ShapeRenderer shapeRenderer;
    public NostalgiOrthogonalTiledMapRenderer (TiledMap map, float unitScale) {
        this(map, unitScale, "Ground");

    }

    public NostalgiOrthogonalTiledMapRenderer(TiledMap map, float unitScale, String groundLayer) {
        super(map, unitScale);
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

                    this.getBatch().draw(new Texture(Gdx.files.internal("badlogic.jpg")), 30, 30);

                }
            } else {
                for (MapObject object : layer.getObjects()) {
                    renderObject(object);
                }
            }
        }
        endRender();

    }
}
