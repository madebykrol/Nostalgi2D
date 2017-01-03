package com.nostalgi.game.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.BaseLevel;
import com.nostalgi.engine.Exceptions.FailedToSpawnActorException;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.game.Actors.ExampleTopDownRPGAICharacter;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public class GrassLandLevel extends BaseLevel {

    public GrassLandLevel(TiledMap map, IWorld world) {
        super(map, world);
    }

    @Override
    public void initMap() {

        try {
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player",     true, new Vector2(32f, 32f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player2",    true, new Vector2(33f, 33f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player3",    true, new Vector2(20f, 20f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player4",    true, new Vector2(40f, 40f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player5",    true, new Vector2(51f, 51f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player6",    true, new Vector2(22f, 22f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player7",    true, new Vector2(37f, 37f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player8",    true, new Vector2(35f, 35f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player9",    true, new Vector2(38f, 38f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player10",   true, new Vector2(43f, 43f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player11",   true, new Vector2(40f, 40f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player12",   true, new Vector2(41f, 41f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player13",   true, new Vector2(42f, 42f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player14",   true, new Vector2(21f, 11f));
            this.getWorld().spawnActor(ExampleTopDownRPGAICharacter.class, "AI Player15",   true, new Vector2(44f, 44f));

        } catch (FailedToSpawnActorException e) {
            e.printStackTrace();
        }
        getWorld().getLightingSystem().updateAmbientLight(new Color(0.00f, 0.00f, 0.00f, 1f));
    }

    @Override
    public Vector2 getCameraInitLocation() {
        return new Vector2(32, 10);
    }
}
