package com.nostalgi.engine;

import com.nostalgi.engine.interfaces.ICharacter;
import com.nostalgi.engine.interfaces.IItem;

import javafx.animation.Animation;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public class BasePlayerCharacter implements ICharacter {
    @Override
    public Animation getCurrentAnimation() {
        return null;
    }

    @Override
    public void setCurrentAnimation() {

    }

    @Override
    public void addAnimation(int state, Animation animation) {

    }

    @Override
    public Animation getAnimation(int state) {
        return null;
    }

    @Override
    public Stat getStat(int mod) {
        return null;
    }

    @Override
    public void setStat(int mod, Stat stat) {

    }

    @Override
    public IItem getEquipmentItem(int slot) {
        return null;
    }

    @Override
    public IItem[] getEquipmentItems() {
        return new IItem[0];
    }

    @Override
    public IItem getInventoryItem(int slot) {
        return null;
    }

    @Override
    public IItem[] getInventoryItems() {
        return new IItem[0];
    }
}
