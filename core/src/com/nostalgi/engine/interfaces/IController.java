package com.nostalgi.engine.interfaces;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.nostalgi.engine.Utils.Guid;
import com.nostalgi.engine.interfaces.States.IPlayerState;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public interface IController {
    /**
     * Possess a character, when a character is possessed it is being controlled by the controller
     * this could be a AI Controller or a player driven Controller.
     * @param character
     */
    void possessCharacter(com.nostalgi.engine.interfaces.World.ICharacter character);

    /**
     * @return the possessed character.
     */
    com.nostalgi.engine.interfaces.World.ICharacter getCurrentPossessedCharacter();

    /**
     * get the gesture listener, this is used on smartphones and tablets
     * @return
     */
    GestureDetector.GestureListener getGestureListener();

    /**
     * Get the input processor, this handles all the key / mouse input.
     *
     * @return
     */
    InputProcessor getInputProcessor();

    /**
     * Tick this controller, this is called as often as possible, typically once per frame
     * @param dTime
     */
    void tick(float dTime);

    void setControllerId(Guid uuid);

    Guid getControllerId();

    void setPlayerState(IPlayerState playerState);
    IPlayerState getPlayerState();
}
