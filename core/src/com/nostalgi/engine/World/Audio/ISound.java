package com.nostalgi.engine.World.Audio;

import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Krille on 11/12/2016.
 */
public interface ISound extends Disposable {

    /**
     * Play this sound.
     * @param volume
     * @param pitch
     * @param pan
     * @return
     */
    ISoundReference play(float volume, float pitch, float pan);

    /**
     * Pause all instances of this sound.
     * @return
     */
    void pause();

    /**
     * Stop all instances of this sound.
     */
    void stop();

    /**
     * Resumes all instances of this sound.
     */
    void resume();


    interface ISoundReference {
        /**
         * Resume the specific instance.
         */
        void resume();

        /**
         * Stop a specific instance of this sound.
         */
        void stop();

        /**
         * Pause a specific instance of this sound.
         * @return
         */
        void pause();

        void setPitch(float pitch);

        void setPan(float pan, float volume);

        void setVolume(float volume);

        void setLooping(boolean looping);
    }

}
