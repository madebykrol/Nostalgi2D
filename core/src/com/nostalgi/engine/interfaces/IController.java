package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public interface IController {
    void possessCharacter(ICharacter character);
    ICharacter getCurrentPossessedCharacter();

    GestureDetector.GestureListener getGestureListener();
    InputProcessor getInputProcessor();
    void update(float dTime);

    boolean hasHitWall(boolean hasHit);
    boolean hasHitWall();
}
