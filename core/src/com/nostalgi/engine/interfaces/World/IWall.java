package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface IWall extends IWorldObject {
    /**
     * Get vertices for this wall, the vertices represent the polygon shape of the wall.
     * @return
     */
    float[] getVertices();

    /**
     * Set the polygon vertices for this wall.
     * @param vertices
     */
    void setVertices(float[] vertices);

    /**
     * Get the X position of this wall
     * @return
     */
    float getX();

    /**
     * Get the Y position of this wall.
     * @return
     */
    float getY();

    /**
     * Set the X position of this wall.
     * @param x
     */
    void setX(float x);

    /**
     * Set the Y position of this wall.
     * @param y
     */
    void setY(float y);

    /**
     * Set the position of this wall.
     * @param pos
     */
    void setPosition(Vector2 pos);

    /**
     * Get the position of this wall.
     * @return
     */
    Vector2 getPosition();

    /**
     * Get all the floor this wall spans,
     * @return
     */
    int[] getFloors();

    /**
     * Set the floors for this wall.
     * @param floors
     */
    void setFloors(int[] floors);

    /**
     * Check whether or not this wall is on floor.
     * @param floor
     * @return
     */
    boolean isOnFloor(int floor);

}
