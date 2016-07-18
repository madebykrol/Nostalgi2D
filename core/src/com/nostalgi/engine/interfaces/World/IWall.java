package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface IWall {
    float[] getVertices();
    void setVertices(float[] vertices);

    float getX();
    float getY();

    void setX(float x);
    void setY(float y);

    void setPosition(Vector2 pos);
    Vector2 getPosition();

    int[] getFloor();
    void setFloors(int[] floors);
    boolean isOnFloor(int floor);
    Body getPhysicsBody();
    void setPhysicsBody(Body body);
}
