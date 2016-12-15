package com.nostalgi.engine.World.Audio;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Krille on 10/12/2016.
 */
public interface ISoundSystem {

    ISound createSound(String soundFile);
    ISound getLoadedSound(String fileName);
    IMusic getLoadedMusic(String fileName);

    IMusic createMusic(String soundFile);

    /**
     * Calculate a value between -1 and 1
     * Where -1 will play the sound with 100% on the left speaker and 1 will play it 100% on the right.
     * and by that 0 will be 100% in both speakers.
     *
     * @param soundPosition
     * @param playerPosition
     * @return
     */
    float calculatePan(Vector2 soundPosition, Vector2 playerPosition);

    /**
     *
     * @param radius
     * @param falloffRadius
     * @param soundPosition
     * @param playerPosition
     * @return
     */
    float calculateVolume(float radius, float falloffRadius, Vector2 soundPosition, Vector2 playerPosition);

    void setNoiseListener(INoiseListener noiseListener);

}
