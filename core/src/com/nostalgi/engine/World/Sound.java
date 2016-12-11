package com.nostalgi.engine.World;

import java.util.ArrayList;

/**
 * Created by Krille on 11/12/2016.
 */

public class Sound implements ISound {

    private com.badlogic.gdx.audio.Sound sound;

    public Sound(com.badlogic.gdx.audio.Sound sound) {
        this.sound = sound;
    }


    @Override
    public ISoundReference play(float volume, float pitch, float pan) {
        long id = this.sound.play(volume, pitch, pan);
        return new SoundReference(id, this.sound);
    }

    @Override
    public void pause() {
        this.sound.pause();
    }

    @Override
    public void stop() {
        this.sound.stop();
    }

    @Override
    public void resume() {
        this.sound.resume();
    }

    @Override
    public void dispose() {
        this.sound.dispose();
    }

    public class SoundReference implements ISoundReference {

        private long id;
        private com.badlogic.gdx.audio.Sound sound;

        public SoundReference(long id, com.badlogic.gdx.audio.Sound sound) {
            this.id = id;
            this.sound = sound;
        }

        @Override
        public void resume() {
            this.sound.resume(this.id);
        }

        @Override
        public void stop() {
            this.sound.stop(this.id);
        }

        @Override
        public void pause() {
            this.sound.pause(this.id);
        }

        @Override
        public void setPitch(float pitch) {
            this.sound.setPitch(this.id, pitch);
        }

        @Override
        public void setPan(float pan, float volume) {
            this.sound.setPan(this.id, pan, volume);
        }

        @Override
        public void setVolume(float volume) {
            this.sound.setVolume(this.id, volume);
        }

        @Override
        public void setLooping(boolean looping) {
            this.sound.setLooping(this.id, looping);
        }

    }
}
