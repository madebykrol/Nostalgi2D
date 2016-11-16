package com.nostalgi.engine.World;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.nostalgi.engine.Annotations.NostalgiField;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.physics.BoundingVolume;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Krille on 15/11/2016.
 */

public class AmbientSoundActor implements IActor {

    @NostalgiField(fieldName = "SoundWave")
    private String soundWave;

    private Sound sound;

    @Override
    public IActor getParent() {
        return null;
    }

    @Override
    public void setParent(IActor parent) {

    }

    @Override
    public HashMap<String, IActor> getChildren() {
        return null;
    }

    @Override
    public IActor getChild(String name) {
        return null;
    }

    @Override
    public void addChildren(IActor[] children) {

    }

    @Override
    public void addChildren(HashMap<String, IActor> children) {

    }

    @Override
    public void addChild(IActor actor) {

    }

    @Override
    public boolean canEverTick() {
        return false;
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public Animation getCurrentAnimation() {
        return null;
    }

    @Override
    public void setCurrentAnimation(Animation animation) {

    }

    @Override
    public void setCurrentAnimation(int state) {

    }

    @Override
    public void addAnimation(int state, Animation animation) {

    }

    @Override
    public Animation getAnimation(int state) {
        return null;
    }

    @Override
    public boolean isAnimated() {
        return false;
    }

    @Override
    public boolean fixtureNeedsUpdate() {
        return false;
    }

    @Override
    public boolean fixtureNeedsUpdate(boolean update) {
        return false;
    }

    @Override
    public void onOverlapBegin(IActor instigator, Fixture instigatorFixture, Fixture targetFixture) {

    }

    @Override
    public void onOverlapEnd(IActor instigator, Fixture instigatorFixture, Fixture targetFixture) {

    }

    @Override
    public void createPhysicsBody() {

    }

    @Override
    public void onSpawn() {

    }

    @Override
    public void onDespawn() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void addOnDestroyListener() {

    }

    @Override
    public boolean isReplicated() {
        return false;
    }

    @Override
    public boolean physicsSimulated() {
        return false;
    }

    @Override
    public boolean physicsSimulated(boolean simulated) {
        return false;
    }

    @Override
    public boolean blockOnCollision(IActor actor, Contact contact) {
        return false;
    }

    @Override
    public void applyForce(Vector2 force, Vector2 targetPoint) {

    }

    @Override
    public void applyForceFromOrigin(Vector2 origin, float force, float radius) {

    }

    @Override
    public Vector2 getPosition() {
        return null;
    }

    @Override
    public Vector2 getWorldPosition() {
        return null;
    }

    @Override
    public void setPosition(Vector2 position) {

    }

    @Override
    public void setBoundingVolume(BoundingVolume body) {

    }

    @Override
    public BoundingVolume getBoundingVolume(int index) {
        return null;
    }

    @Override
    public ArrayList<BoundingVolume> getBoundingVolumes() {
        return null;
    }

    @Override
    public Body getPhysicsBody() {
        return null;
    }

    @Override
    public void setPhysicsBody(Body body) {

    }

    @Override
    public int getFloorLevel() {
        return 0;
    }

    @Override
    public void setFloorLevel(int floor) {

    }

    @Override
    public void setWorld(IWorld world) {

    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public boolean isStatic(boolean isStatic) {
        return false;
    }

    @Override
    public boolean isSensor() {
        return false;
    }

    @Override
    public boolean isSensor(boolean isSenor) {
        return false;
    }

    @Override
    public float getDensity() {
        return 0;
    }

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

    @Override
    public void draw(Batch batch, float timeElapsed) {

    }

    @Override
    public float getMass() {
        return 0;
    }

    @Override
    public void setMass(float mass) {

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }
}
