package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.nostalgi.engine.Stat;
import com.nostalgi.engine.interfaces.IController;

/**
 * Created by Kristoffer on 2016-06-30.
 */
public interface ICharacter extends IActor, Disposable{


    void setCurrentController(IController controller);
    IController getCurrentController();

    <T extends IController> void setAIControllerClass(Class<T> defaultClass);
    Class getAIControllerClass();

    boolean shouldSpawnWithAiController();
    boolean shouldSpawnWithAiController(boolean should);

    boolean shouldBePossessedOnSpawn();
    boolean shouldBePossessedOnSpawn(boolean should);

    void lookAt(Vector2 target);
    float getRotation();

    void setWalkingState(int state);
    int getWalkingState();

    void moveForward(float velocity);
    void stop();

    void setDimensions(float width, float height);

    void setWidth(float width);
    void setHeight(float height);

    float getWidth();
    float getHeight();

    boolean isMoving();
    boolean isMoving(boolean moving);

    boolean isJumping();
    boolean isJumping(boolean jumping);
}
