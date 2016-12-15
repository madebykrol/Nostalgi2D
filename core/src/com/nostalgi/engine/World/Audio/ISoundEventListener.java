package com.nostalgi.engine.World.Audio;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Krille on 14/12/2016.
 */
public interface ISoundEventListener {
    void soundStarted(ISound sound, Vector2 position);
    void soundStopped(ISound sound, Vector2 position);
    void musicStarted(IMusic music, Vector2 position);
}
