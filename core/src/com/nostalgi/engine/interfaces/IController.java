package com.nostalgi.engine.interfaces;

/**
 * Created by ksdkrol on 2016-07-04.
 */
public interface IController {
    void possessCharacter(ICharacter character);
    ICharacter getCurrentPossessedCharacter();
}
