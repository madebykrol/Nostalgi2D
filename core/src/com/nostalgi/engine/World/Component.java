package com.nostalgi.engine.World;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.IComponent;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.physics.BoundingVolume;

import java.util.ArrayList;

/**
 * Created by Krille on 06/12/2016.
 */

public class Component implements IComponent {

    Vector2 position;

    IActor attachedTo;

    String name;

    @Override
    public IActor attachedTo(IActor actor) {
        return this.attachedTo = actor;
    }

    @Override
    public IActor attachedTo() {
        return this.attachedTo;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public Vector2 getWorldPosition() {
        return null;
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void tick(float dTime) {

    }
}
