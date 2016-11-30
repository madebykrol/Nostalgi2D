package com.nostalgi.engine.World;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.nostalgi.engine.Annotations.NostalgiField;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.IComponent;
import com.nostalgi.engine.interfaces.World.IWorld;
import com.nostalgi.engine.physics.BoundingVolume;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Krille on 15/11/2016.
 */

public class AmbientSoundActor extends BaseActor {

    @NostalgiField(fieldName = "SoundWave", withMethod = "createSound")
    private String soundWave;

    @NostalgiField(fieldName = "Loop")
    private boolean loop;

    @NostalgiField(fieldName = "Radius")
    private float radius;

    @NostalgiField(fieldName = "Falloff Distance")
    private float falloffDistance;

    private Sound sound;


    public AmbientSoundActor() {

    }

    public AmbientSoundActor(String soundWave, boolean loop, float radius, float falloffDistance) {
        this();

        this.soundWave = soundWave;
        this.loop = loop;
        this.radius = radius;
        this.falloffDistance = falloffDistance;

        createSound(soundWave);
    }

    @Override
    public void onOverlapBegin(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture) {
        BoundingVolume volume = (BoundingVolume) targetFixture.getUserData();
        if(volume.getVolumeId().equals("gravitywell")) {
            System.out.println("Begin gravity pull on object");
        }
    }

    @Override
    public void onOverlapEnd(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture) {
        BoundingVolume volume = (BoundingVolume) targetFixture.getUserData();
        if(volume.getVolumeId().equals("gravitywell")) {
            System.out.println("End gravity pull on object");
        }
    }

    public void createSound(String soundWave) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal(soundWave));
    }

    @Override
    public void destroy() {
        if(this.sound != null) {
            this.sound.dispose();
        }
    }
}
