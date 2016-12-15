package com.nostalgi.engine.World.Audio;

import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Krille on 13/12/2016.
 */

public interface IMusic extends Disposable {

    void play();
    boolean isPlaying();
    boolean isLooping();
    void pause();
    void stop();
    void setVolume(float volume);
    void setPan(float pan, float volume);


}
