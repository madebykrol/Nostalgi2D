package com.nostalgi.engine.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.physics.BoundingVolume;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kristoffer on 2016-07-15.
 */
public abstract class BaseActor implements IActor {

    private int floor = 1;

    private IActor parent;
    private HashMap<String, IActor> children = new HashMap<String, IActor>();

    private Vector2 position;
    private Vector2 worldPosition;

    private ArrayList<BoundingVolume> boundingVolumes = new ArrayList<BoundingVolume>();

    private Animation currentAnimation;
    private HashMap<Integer, Animation> animations = new HashMap<Integer, Animation>();
    private String name = "Actor"+this.hashCode();

    private World world;
    private boolean transformationNeedsUpdate = true;

    private Body physicsBody;

    private float density = 100f;
    private float friction = 1f;

    private boolean isStatic = false;

    private boolean fixtureNeedsUpdate = false;

    @Override
    public IActor getParent() {
        return this.parent;
    }
    public void setParent(IActor parent) {
        this.parent = parent;
        this.transformationNeedsUpdate = true;
    }

    public HashMap<String, IActor> getChildren() {
        return this.children;
    }
    public IActor getChild(String name) {
        return this.children.get(name);
    }

    public void addChildren(IActor[] children) {
        for (IActor aChildren : children) {
            this.addChild(aChildren);
        }
    }

    public void addChildren(HashMap<String, IActor> children) {

        for (Object o : children.entrySet()) {
            IActor actor = (IActor) o;
            this.addChild(actor);
        }
    }

    public void addChild(IActor actor) {
        this.children.put(actor.getName(), actor);
        actor.setParent(this);
    }

    @Override
    public Vector2 getWorldPosition() {
        this.doWorldTransformation();
        return worldPosition;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
        this.transformationNeedsUpdate = true;
    }

    @Override
    public void setBoundingVolume(BoundingVolume volume) {
        this.boundingVolumes.add(volume);
    }

    @Override
    public BoundingVolume getBoundingVolume(int index) {
        return this.boundingVolumes.get(index);
    }

    public ArrayList<BoundingVolume> getBoundingVolumes() {return this.boundingVolumes;}

    @Override
    public void onOverlapBegin(IActor overlapper) {}

    @Override
    public void onOverlapEnd(IActor overlapper) { }

    @Override
    public com.badlogic.gdx.graphics.g2d.Animation getCurrentAnimation() {
        return this.currentAnimation;
    }

    @Override
    public void setCurrentAnimation(com.badlogic.gdx.graphics.g2d.Animation animation) {
        this.currentAnimation = animation;
    }

    @Override
    public void setCurrentAnimation(int state) {
        if(animations.containsKey(state))
            this.currentAnimation = animations.get(state);

    }

    @Override
    public void addAnimation(int state, com.badlogic.gdx.graphics.g2d.Animation animation) {
        this.animations.put(state, animation);
    }

    @Override
    public com.badlogic.gdx.graphics.g2d.Animation getAnimation(int state) {
        if(animations.containsKey(state))
            return animations.get(state);

        return null;
    }

    @Override
    public boolean isAnimated() {
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean canEverTick() {
        return false;
    }

    @Override
    public void tick(float delta) {}

    @Override
    public int getFloorLevel() {
        return this.floor;
    }

    @Override
    public void setFloorLevel(int floor) {
        this.floor = floor;
        this.fixtureNeedsUpdate = true;
    }

    public boolean fixtureNeedsUpdate() {
        return this.fixtureNeedsUpdate;
    }

    public boolean fixtureNeedsUpdate(boolean update) {
        this.fixtureNeedsUpdate = update;
        return update;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void draw(Batch batch, float timeElapsed) {

    }

    @Override
    public float getDensity() {
        return density;
    }

    @Override
    public void setDensity(float density) {
        this.density = density;
    }

    @Override
    public float getFriction() {
        return friction;
    }

    @Override
    public void setFriction(float friction) {
        this.friction = friction;
    }

    @Override
    public void setPhysicsBody(Body body) {
        this.physicsBody = body;
    }

    @Override
    public Body getPhysicsBody() {

        return physicsBody;
    }

    @Override
    public boolean isStatic() {
        return this.isStatic;
    }

    @Override
    public boolean  isStatic(boolean isStatic) {
        return this.isStatic = isStatic;
    }

    public void transformationHasUpdated() {
        this.transformationNeedsUpdate = true;
    }

    private void doWorldTransformation() {
        if(this.transformationNeedsUpdate) {
            if(this.parent != null) {
                Vector2 worldPosition = new Vector2();
                worldPosition.x = this.position.x + this.parent.getWorldPosition().x;
                worldPosition.y = this.position.y + this.parent.getWorldPosition().y;

                this.worldPosition = worldPosition;
            }
            else  {
                this.worldPosition = this.position;
            }
            this.transformationNeedsUpdate = false;
        }
    }
}
