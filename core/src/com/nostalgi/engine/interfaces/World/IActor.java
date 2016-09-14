package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nostalgi.engine.interfaces.physics.BoundingVolume;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ksdkrol on 2016-07-05.
 */
public interface IActor {

    IActor getParent();
    void setParent(IActor parent);

    HashMap<String, IActor> getChildren();
    IActor getChild(String name);
    void addChildren(IActor[] children);
    void addChildren(HashMap<String, IActor> children);
    void addChild(IActor actor);

    void setName(String name);
    String getName();

    boolean canEverTick();
    void tick(float delta);

    Animation getCurrentAnimation();

    void setCurrentAnimation(Animation animation);
    void setCurrentAnimation(int state);

    void addAnimation(int state, Animation animation);
    Animation getAnimation(int state);

    boolean isAnimated();

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
    BoundingVolume getBoundingVolume();

    void onOverlapBegin(IActor overlapper);
    void onOverlapEnd(IActor overlapper);

    int getFloorLevel();
    void setFloorLevel(int floor);

    void setWorld(World world);

    void draw(Batch batch, float timeElapsed);

}
