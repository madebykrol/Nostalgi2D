package com.nostalgi.engine.World;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.physics.BoundingVolume;
import com.nostalgi.engine.physics.CollisionCategories;

import java.util.ArrayList;

/**
 * Created by Krille on 13/11/2016.
 */

public class GravityActor extends BaseActor {

    private ArrayList<IActor> actorsInWell = new ArrayList<IActor>();

    public GravityActor () {
        canEverTick = true;

    }

    @Override
    public void createPhysicsBody() {
        BoundingVolume boundingVolume2 = new BoundingVolume();
        boundingVolume2.isSensor(true);
        boundingVolume2.setVolumeId("gravitywell");
        boundingVolume2.setCollisionCategory(CollisionCategories.CATEGORY_TRIGGER);
        boundingVolume2.setCollisionMask(CollisionCategories.MASK_TRIGGER);

        CircleShape shape2 = new CircleShape();
        shape2.setRadius(this.getMass()/10);
        boundingVolume2.setShape(shape2);

        this.setBoundingVolume(boundingVolume2);
    }

    @Override
    public void onOverlapBegin(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture) {
        BoundingVolume volume = (BoundingVolume) targetFixture.getUserData();
        if(volume.getVolumeId().equals("gravitywell")) {
            System.out.println("Begin gravity pull on object");
            if(!actorsInWell.contains(overlapper))
                actorsInWell.add(overlapper);
        }
    }

    @Override
    public void onOverlapEnd(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture) {
        BoundingVolume volume = (BoundingVolume) targetFixture.getUserData();
        if(volume.getVolumeId().equals("gravitywell")) {
            System.out.println("End gravity pull on object");
            actorsInWell.remove(overlapper);
        }
    }

    @Override
    public void tick(float time) {
        for(IActor actor : actorsInWell) {
            float angleBetween = MathUtils.atan2(this.getPosition().y - actor.getPosition().y, this.getPosition().x - actor.getPosition().x);

            float distanceBetween = this.getPosition().dst(actor.getPosition());

            float G  = 5000; // Approximation of G for the system.

            // G*M1*M2 / D^2
            float x = (G * (this.getMass() * actor.getMass()) / (distanceBetween*distanceBetween)) * MathUtils.cos(angleBetween);
            float y = (G * (this.getMass() * actor.getMass()) / (distanceBetween*distanceBetween)) * MathUtils.sin(angleBetween);

            actor.applyForce(new Vector2(x, y), actor.getPhysicsBody().getWorldCenter());
        }
    }
}
