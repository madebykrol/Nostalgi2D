package com.nostalgi.game;

import com.nostalgi.engine.BasePlayerState;
import com.nostalgi.engine.Utils.Guid;
import com.nostalgi.engine.interfaces.States.IPlayerState;

/**
 * Created by Krille on 14/12/2016.
 */

public class ExamplePlayerState extends BasePlayerState {

    float maxHp = 100;
    float maxStamina = 100;
    float maxMana = 100;

    float hp = 100;
    float stamina = 100;
    float mana = 100;

    /**
     * Add or subtract stamina from playerstate.
     * @param stamina
     */
    public void addStamina(float stamina) {
        if(stamina > 0) {
            if (this.stamina < maxStamina) {
                if (this.stamina + stamina > maxStamina) {
                    this.stamina += maxStamina - this.stamina;
                } else {
                    this.stamina += stamina;
                }
            }
        } else {
            if(this.stamina > 0) {
                if(this.stamina + stamina <= 0) {
                    this.stamina = 0;
                } else {
                    this.stamina += stamina;
                }
            }
        }
    }

    public float getStamina() {
        return this.stamina;
    }

    public float getMaxStamina() {
        return this.maxStamina;
    }

    public void setMaxStamina(float stamina) {
        this.maxStamina = stamina;
    }

    public float getHp() {
        return this.hp;
    }

    public float getMaxHp() {
        return this.maxHp;
    }

    public void setMaxHp(float hp) {
        this.maxHp = hp;
    }

    public float getMana() {
        return this.mana;
    }

    public float getMaxMana() {
        return this.maxMana;
    }

    public void setMaxMana(float mana) {
        this.maxMana = mana;
    }
}
