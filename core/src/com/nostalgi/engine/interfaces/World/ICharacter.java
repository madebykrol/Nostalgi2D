package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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
    void setFacingDirection(Direction dir);
    Direction getFacingDirection();


    void moveForward(Vector2 velocityVector);
    void stop();
    Vector2 getVelocity();


    /**
     * Set dimensions of character sprite in unitspace.
     * @param width
     * @param height
     */
    void setDimensions(float width, float height);

    void setWidth(float width);
    void setHeight(float height);

    float getWidth();
    float getHeight();
}
