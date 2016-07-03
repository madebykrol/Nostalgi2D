package com.pixelwarriors.engine;

import javafx.animation.Animation;

/**
 * Created by Kristoffer on 2016-06-30.
 */
public interface ICharacter {

    public Animation getCurrentAnimation();
    public void setCurrentAnimation();
    public void addAnimation(int state, Animation animation);
    public Animation getAnimation(int state);


    public Stat getStat(int mod);
    public void setStat(int mod, Stat stat);

    public IItem getEquipmentItem(int slot);
    public IItem[] getEquipmentItems();

    public IItem getInventoryItem(int slot);
    public IItem[] getInventoryItems();


}
