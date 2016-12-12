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

    /**
     * Get rotation in radians
     * @return
     */
    float getRotation();

    /**
     * Set rotation in radians
     * @param rotation
     */
    void setRotation(float rotation);

    void setBoundingVolume(BoundingVolume body);
    BoundingVolume getBoundingVolume(int index);

    ArrayList<BoundingVolume> getBoundingVolumes();

    Body getPhysicsBody();
    void setPhysicsBody(Body body);
    int getFloorLevel();
    void setFloorLevel(int floor);

    boolean isStatic();
    boolean isStatic(boolean isStatic);

    boolean isKinematic();
    boolean isKinematic(boolean isKinematic);

    boolean isSensor();
    boolean isSensor(boolean isSenor);

    float getFriction();

    void setFriction(float friction);

    void draw(Batch batch, float timeElapsed);

    float getMass();

    void setName(String name);
    String getName();

    /**
     * Last chance for actor to add bounding volumes before its being spawned to the world.
     * This method is called right before createBody
     *
     */
    void preCreatePhysicsBody();
}
