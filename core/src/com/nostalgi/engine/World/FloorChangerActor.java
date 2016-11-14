package com.nostalgi.engine.World;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public class FloorChangerActor extends BaseActor {

    @Override
    public void onOverlapBegin(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture) {
       if(overlapper instanceof ICharacter) {
           ICharacter character = (ICharacter)overlapper;
           character.setFloorLevel(this.getFloorLevel());
       }
    }

    @Override
    public void onOverlapEnd(IActor overlapper, Fixture instigatorFixture, Fixture targetFixture) {

    }
}
