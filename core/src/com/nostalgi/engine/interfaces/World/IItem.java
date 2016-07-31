package com.nostalgi.engine.interfaces.World;

import com.nostalgi.engine.Stat;

/**
 * Created by Kristoffer on 2016-06-30.
 */
public interface IItem extends IActor {

    String getName();
    int getItemClass();

    boolean isStackable();
    boolean isConsumable();

    float getBuyPrice();
    float getSellPrice();

    int getStackMaxSize();
    int getStackSize();
    Stat[] getStatModifiers();

    int getRarity();


    // events
    void onConsumed(ICharacter consumer);
    void onPickup(ICharacter picker);
    void onUse(ICharacter user);

}
