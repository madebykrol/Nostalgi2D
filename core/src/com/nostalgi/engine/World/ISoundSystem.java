package com.nostalgi.engine.World;

/**
 * Created by Krille on 10/12/2016.
 */
public interface ISoundSystem {

    ISound createSound(String name, String soundFile);
    ISound getLoadedSound(String name);

}
