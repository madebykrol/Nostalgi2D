package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nostalgi.engine.physics.BoundingVolume;

import java.util.ArrayList;

/**
 * Created by ksdkrol on 2016-10-31.
 */

public interface IWorldObject {
    /**
     * Get world position, relative to parent
     */
    Vector2 getPosition();

    /**
     * Get absolute position relative to origin (0,0)
     */
    Vector2 getWorldPosition();

    /**
     * Set relative position relative to parent
     * @param position
     */
    void setPosition(Vector2 position);

    void setBoundingVolume(BoundingVolume body);
    BoundingVolume getBoundingVolume(int index);

    ArrayList<BoundingVolume> getBoundingVolumes();

    Body getPhysicsBody();
    void setPhysicsBody(Body body);
    int getFloorLevel();
    void setFloorLevel(int floor);

    void setWorld(IWorld world);

    boolean isStatic();
    boolean isStatic(boolean isStatic);

    boolean isSensor();
    boolean isSensor(boolean isSenor);

    float getDensity();

    void setDensity(float density);

    float getFriction();

    void setFriction(float friction);

    void draw(Batch batch, float timeElapsed);

    float getMass();
    void setMass(float mass);

    void setName(String name);
    String getName();



}
