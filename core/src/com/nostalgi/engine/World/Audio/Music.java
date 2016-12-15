package com.nostalgi.engine.World.Audio;

import java.util.ArrayList;

/**
 * Created by Krille on 13/12/2016.
 */

public class Music implements com.nostalgi.engine.World.Audio.IMusic {

    private com.badlogic.gdx.audio.Music music;

    public Music(com.badlogic.gdx.audio.Music music) {
        this.music = music;
    }

    @Override
    public void play() {
        this.music.play();
    }

    @Override
    public boolean isPlaying() {
        return this.music.isPlaying();
    }

    @Override
    public boolean isLooping() {
        return this.music.isLooping();
    }

    @Override
    public void pause() {
        this.music.pause();
    }

    @Override
    public void stop() {
        this.music.stop();
    }

    @Override
    public void setVolume(float volume) {
        this.music.setVolume(volume);
    }

    @Override
    public void setPan(float pan, float volume) {
        this.music.setPan(pan, volume);
    }

    @Override
    public void dispose() {
        this.music.dispose();
    }
}
