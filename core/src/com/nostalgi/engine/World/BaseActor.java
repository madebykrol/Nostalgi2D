package com.nostalgi.engine.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.nostalgi.engine.Annotations.NostalgiField;
import com.nostalgi.engine.Annotations.Replicated;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.physics.BoundingVolume;
import com.nostalgi.engine.physics.CollisionCategories;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kristoffer on 2016-07-15.
 */
public class BaseActor implements IActor {

    @Replicated
    @NostalgiField(fieldName = "Floor")
    private int floor = 1;

    @NostalgiField(fieldName = "PhysicsSimulated")
    private boolean physicsSimulated;

    @NostalgiField(fieldName = "Mass")
    private float mass;

    private IActor parent;
    private HashMap<String, IActor> children = new HashMap<String, IActor>();

    @Replicated
    protected boolean canEverTick;

    /**
     * Position relative to parent
     */
    @Replicated
    private Vector2 position;

    /**
     * Absolut world position
     */
    @Replicated
    private Vector2 worldPosition;

    @Replicated
    private boolean transformationNeedsUpdate = true;

    @Replicated
    private ArrayList<BoundingVolume> boundingVolumes = new ArrayList<BoundingVolume>();

    @Replicated
    private Animation currentAnimation;

    private HashMap<Integer, Animation> animations = new HashMap<Integer, Animation>();

    @NostalgiField(fieldName = "Name")
    private String name = "Actor"+this.hashCode();

    private IWorld world;

    private Body physicsBody;


    private boolean isReplicated = false;

    @NostalgiField(fieldName = "Density")
    private float density = 100f;
    @NostalgiField(fieldName = "Friction")
    private float friction = 1f;
    @NostalgiField(fieldName = "IsStatic")
    private boolean isStatic = false;
    @NostalgiField(fieldName = "IsSensor")
    private boolean isSensor = false;

    private boolean fixtureNeedsUpdate = false;

    public BaseActor() {

    }

    public BaseActor(IWorld world) {
        this.world = world;
    }

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
    public void onOverlapBegin(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture) {}

    @Override
    public void onOverlapEnd(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture) { }

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
        return canEverTick;
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
    public void setWorld(IWorld world) {
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

    @Override
    public boolean isSensor() {
        return this.isSensor;
    }

    @Override
    public boolean isSensor(boolean isSensor) {
        return this.isSensor = isSensor;
    }

    @Override
    public float getMass() {
        return this.mass;
    }

    @Override
    public void setMass(float mass) {
        this.mass = mass;
    }

    @Override
    public void addOnDestroyListener() {}

    @Override
    public boolean isReplicated() {
        return this.isReplicated;
    }

    @Override
    public boolean physicsSimulated() {
        return this.physicsSimulated;
    }

    @Override
    public boolean  physicsSimulated( boolean simulated) {
        return (this.physicsSimulated = simulated);
    }

    @Override
    public boolean blockOnCollision(IActor actor, Contact contact) {
        return contact.isEnabled();
    }

    @Override
    public void applyForce(Vector2 force, Vector2 targetPoint) {
        getPhysicsBody().applyForce(force, targetPoint, true);
    }

    @Override
    public void applyRadialForce(Vector2 origin, float force, float falloffRadius) {
        float angleBetween = Math.abs(MathUtils.atan2(origin.y-this.getPosition().y,  origin.x-this.getPosition().x));

        System.out.println(angleBetween);

        float distanceBetween = this.getPosition().dst(origin);
        float falloff = 1;

        // F/M1 * (R-D) * cos(a)|sin(a)
        float x = ((force / this.getMass()) * (falloff) )* MathUtils.cos(angleBetween);
        float y = ((force / this.getMass()) * (falloff) )* MathUtils.sin(angleBetween);


        this.getPhysicsBody().applyLinearImpulse(new Vector2(x, y), this.getPhysicsBody().getWorldCenter(), true);
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
