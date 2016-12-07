package com.nostalgi.engine.World;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nostalgi.engine.interfaces.World.IWall;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.physics.BoundingVolume;

import java.util.ArrayList;

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

    /**
     * @inheritDoc
     */
    public Wall(int[] floors) {
        this(floors, new Vector2(0,0));
    }

    /**
     * @inheritDoc
     */
    public Wall(int[] floors, Vector2 position) {
        this(floors, position, new float[0]);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getName() {
        return "Wall-"+this.hashCode();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setName(String name) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public float[] getVertices() {
        return this.vertices;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    /**
     * @inheritDoc
     */
    @Override
    public float getX() {
        return this.position.x;
    }

    /**
     * @inheritDoc
     */
    @Override
    public float getY() {
        return this.position.y;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setX(float x) {
        this.position.x = x;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setY(float y) {
        this.position.y = y;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPosition(Vector2 pos) {
        this.position = pos;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setBoundingVolume(BoundingVolume body) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public BoundingVolume getBoundingVolume(int index) {
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<BoundingVolume> getBoundingVolumes() {
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Vector2 getWorldPosition() {
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int[] getFloors() {
        return this.floors;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setFloors(int[] floors) {
        this.floors = floors;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isOnFloor(int floor) {
        for (int floor1 : floors) {
            if (floor1 == floor) {
                return true;
            }
        }

        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Body getPhysicsBody() {
        return this.body;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPhysicsBody(Body body) {
        this.body = body;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getFloorLevel() {
        return 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setFloorLevel(int floor) {

    }


    /**
     * @inheritDoc
     */
    @Override
    public boolean isStatic() {
        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isStatic(boolean isStatic) {
        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isSensor() {
        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isSensor(boolean isSenor) {
        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public float getDensity() {
        return 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setDensity(float density) {

    }

    @Override
    public float getFriction() {
        return 0;
    }

    @Override
    public void setFriction(float friction) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void draw(Batch batch, float timeElapsed) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public float getMass() {
        return 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setMass(float mass) {

    }

}
