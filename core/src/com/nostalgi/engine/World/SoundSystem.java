package com.nostalgi.engine.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.Utils.NMath;

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
}
