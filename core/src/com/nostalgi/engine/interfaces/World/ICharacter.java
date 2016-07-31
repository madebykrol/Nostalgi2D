package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.nostalgi.engine.Direction;
import com.nostalgi.engine.Stat;
import com.nostalgi.engine.interfaces.IController;

/**
 * Created by Kristoffer on 2016-06-30.
 */
public interface ICharacter extends IActor, Disposable{

    Stat getStat(int mod);
    void setStat(int mod, Stat stat);

    IItem getEquipmentItem(int slot);
    IItem[] getEquipmentItems();

    IItem getInventoryItem(int slot);
    IItem[] getInventoryItems();

    void setCurrentController(IController controller);
    IController getCurrentController();

    Sprite getStaticSprite(float deltaT);
    void face(float direction);
    void face(Vector2 target);
    float getFacingDirection();

    void setWalkingState(int state);
    int getWalkingState();


    void moveForward(float velocity);
    void stop();
    Vector2 getVelocity();


    /**
     * Set dimensions of character sprite in unitspace.
     * @param width width of character
     * @param height height of character
     */
    void setDimensions(float width, float height);

    void setWidth(float width);
    void setHeight(float height);

    float getWidth();
    float getHeight();
}
