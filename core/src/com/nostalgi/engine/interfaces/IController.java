package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public interface IController {
    void possessCharacter(com.nostalgi.engine.interfaces.World.ICharacter character);
    com.nostalgi.engine.interfaces.World.ICharacter getCurrentPossessedCharacter();

    GestureDetector.GestureListener getGestureListener();
    InputProcessor getInputProcessor();
    void update(float dTime);
}
