package com.pixelwarriors.engine;

/**
 * Created by Kristoffer on 2016-06-30.
 */
public interface IItem {

    public String getName();
    public int getItemClass();

    public boolean isStackable();
    public boolean isConsumable();

    public float getBuyPrice();
    public float getSellPrice();

    public int getStackMaxSize();
    public int getStackSize();
    public Stat[] getStatModifiers();

    public int getRarity();


    // events
    public void onConsumed(ICharacter consumer);
    public void onPickup(ICharacter picker);
    public void onUse(ICharacter user);

}
