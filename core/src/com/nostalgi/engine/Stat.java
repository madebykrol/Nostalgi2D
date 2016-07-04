package com.nostalgi.engine;

/**
 * Created by Kristoffer on 2016-06-30.
 */
public class Stat {

    /**
     *
     */
    private float value;

    /**
     *
     */
    private String name;

    public Stat(String name, float value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Get the name of the stat
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the stat value.
     * @return
     */
    public float getValue() {
        return this.value;
    }
}
