package com.nostalgi.engine;

import com.nostalgi.engine.interfaces.World.IActor;
import com.nostalgi.engine.interfaces.World.ICharacter;

/**
 * Created by Kristoffer on 2016-07-16.
 */
public class TriggerActor extends BaseActor {

    @Override
    public void onOverlapBegin(IActor overlapper) {
       if(overlapper instanceof ICharacter) {
           ICharacter character = (ICharacter)overlapper;
           if(this.getName().equals("Floor2Switch")) {
               character.setFloorLevel(2);
           } else  if(this.getName().equals("Floor1Switch")) {
               character.setFloorLevel(1);
           }
       }
    }

    @Override
    public void onOverlapEnd(IActor overlapper) {

    }
}
