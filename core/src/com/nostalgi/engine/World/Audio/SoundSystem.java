package com.nostalgi.engine.World.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.Utils.NMath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Krille on 10/12/2016.
 */

public class SoundSystem implements ISoundSystem, ISoundEventListener {

    HashMap<String, ISound> sounds = new HashMap<String, ISound>();
    HashMap<String, IMusic> music = new HashMap<String, IMusic>();
    ArrayList<INoiseListener> listeners = new ArrayList<INoiseListener>();

    @Override
    public ISound createSound(String soundFile) {
        if (sounds.containsKey(soundFile)) {
            return this.sounds.get(soundFile);
        }

        com.badlogic.gdx.audio.Sound sound = Gdx.audio.newSound(Gdx.files.internal(soundFile));

        ISound s = new Sound(sound, this);

        sounds.put(soundFile, s);
        return s;
    }

    @Override
    public IMusic createMusic(String soundFile) {
        if (music.containsKey(soundFile)) {
            return this.music.get(soundFile);
        }

        com.badlogic.gdx.audio.Music sound = Gdx.audio.newMusic(Gdx.files.internal(soundFile));

        IMusic m = new Music(sound);

        music.put(soundFile, m);
        return m;
    }

    @Override
    public ISound getLoadedSound(String fileName) {
        if(this.sounds.containsKey(fileName)) {
            return this.sounds.get(fileName);
        }
        return null;
    }

    @Override
    public IMusic getLoadedMusic(String fileName) {
        if(this.music.containsKey(fileName)) {
            return this.music.get(fileName);
        }
        return null;
    }

    @Override
    public float calculatePan(Vector2 soundPosition, Vector2 playerPosition) {
        float angleBetween = NMath.angleBetween(soundPosition, playerPosition);

        if((angleBetween <= 35 && angleBetween >= 0) || (angleBetween <= 0 && angleBetween >= -65)) {
            return -1;
        } else if((angleBetween <= 180 && angleBetween >= 115) ||  (angleBetween <= -180 && angleBetween >= -115)) {
            return 1;
        }
        return 0;
    }

    @Override
    public float calculateVolume(float radius, float falloffRadius, Vector2 soundPosition, Vector2 playerPosition) {
        float distanceBetween = soundPosition.dst(playerPosition);
        float maximumDistance = radius + falloffRadius;

        if(maximumDistance - distanceBetween > 0) {
            float absDistance = Math.abs(maximumDistance - distanceBetween);
            if(absDistance <=  radius && absDistance <= maximumDistance) {
                return absDistance / maximumDistance;
            }
            return 1;
        }
        return 0;
    }

    @Override
    public void setNoiseListener(INoiseListener noiseListener) {
        this.listeners.add(noiseListener);
    }

    @Override
    public void soundStarted(ISound sound, Vector2 position) {

    }

    @Override
    public void soundStopped(ISound sound, Vector2 position) {

    }

    @Override
    public void musicStarted(IMusic music, Vector2 position) {

    }
}
