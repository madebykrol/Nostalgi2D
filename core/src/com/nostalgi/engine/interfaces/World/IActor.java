package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.HashMap;

/**
 * Created by ksdkrol on 2016-07-05.
 */
public interface IActor {

    IActor getParent();
    void setParent(IActor parent);

    HashMap<String, IActor> getChildren();
    IActor getChild(String name);
    void setChildren(IActor[] children);
    void setChildren(HashMap<String, IActor> children);

    void setName(String name);
    String getName();

    boolean canEverTick();
    void tick();

    Animation getCurrentAnimation();

    void setCurrentAnimation(Animation animation);
    void setCurrentAnimation(int state);

    void addAnimation(int state, Animation animation);
    Animation getAnimation(int state);

    boolean isAnimated();

    Vector2 getPosition();
    void setPosition(Vector2 position);

    void setPhysicsBody(Body body);
    Body getPhysicsBody();

    void onOverlapBegin(IActor overlapper);
    void onOverlapEnd(IActor overlapper);
}
