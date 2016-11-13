package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.physics.BoundingVolume;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ksdkrol on 2016-07-05.
 */
public interface IActor extends IWorldObject {

    IActor getParent();
    void setParent(IActor parent);

    HashMap<String, IActor> getChildren();
    IActor getChild(String name);
    void addChildren(IActor[] children);
    void addChildren(HashMap<String, IActor> children);
    void addChild(IActor actor);

    boolean canEverTick();
    void tick(float delta);

    Animation getCurrentAnimation();

    void setCurrentAnimation(Animation animation);
    void setCurrentAnimation(int state);

    void addAnimation(int state, Animation animation);
    Animation getAnimation(int state);

    boolean isAnimated();

    boolean fixtureNeedsUpdate();
    boolean fixtureNeedsUpdate(boolean update);

    void onOverlapBegin(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture);
    void onOverlapEnd(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture);

    void postSpawn();
    void postDespawned();

    void destroy();

    void addOnDestroyListener();

    boolean isReplicated();

    boolean physicsSimulated();
    boolean physicsSimulated(boolean simulated);
}
