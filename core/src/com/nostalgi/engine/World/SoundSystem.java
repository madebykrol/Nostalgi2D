package com.nostalgi.engine.World;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/**
 * Created by Krille on 10/12/2016.
 */

public class SoundSystem implements ISoundSystem {

    HashMap<String, ISound> sounds = new HashMap<String, ISound>();

    @Override
    public ISound createSound(String name, String soundFile) {

        if (sounds.containsKey(name)) {
            return this.sounds.get(name);
        }

        com.badlogic.gdx.audio.Sound sound = Gdx.audio.newSound(Gdx.files.internal(soundFile));

        ISound s = new com.nostalgi.engine.World.Sound(sound);

        sounds.put(name, s);
        return s;
    }

    @Override
    public ISound getLoadedSound(String name) {
        if(this.sounds.containsKey(name)) {
            return this.sounds.get(name);
        }
        return null;
    }
}
