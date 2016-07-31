package com.nostalgi.engine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nostalgi.engine.interfaces.World.IWall;

/**
 * Created by Kristoffer on 2016-07-10.
 */
public class Wall implements IWall {

    private float[] vertices;
    private int[] floors = new int[4];
    private Vector2 position;
    private Body body;

    public Wall(int[] floors, Vector2 position, float[] vertices) {
        this.vertices = vertices;
        this.floors = floors;
        this.position = position;
    }

    public Wall(int[] floors) {
        this(floors, new Vector2(0,0));
    }

    public Wall(int[] floors, Vector2 position) {
        this(floors, position, new float[0]);
    }

    @Override
    public float[] getVertices() {
        return this.vertices;
    }

    @Override
    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public float getX() {
        return this.position.x;
    }

    @Override
    public float getY() {
        return this.position.y;
    }

    @Override
    public void setX(float x) {
        this.position.x = x;
    }

    @Override
    public void setY(float y) {
        this.position.y = y;
    }

    @Override
    public void setPosition(Vector2 pos) {
        this.position = pos;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public int[] getFloor() {
        return this.floors;
    }

    @Override
    public void setFloors(int[] floors) {
        this.floors = floors;
    }

    @Override
    public boolean isOnFloor(int floor) {
        for (int floor1 : floors) {
            if (floor1 == floor) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Body getPhysicsBody() {
        return this.body;
    }

    @Override
    public void setPhysicsBody(Body body) {
        this.body = body;
    }
}
