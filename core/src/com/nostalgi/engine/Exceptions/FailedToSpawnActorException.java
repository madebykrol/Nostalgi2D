package com.nostalgi.engine.Exceptions;

/**
 * Created by ksdkrol on 2016-10-28.
 */

public class FailedToSpawnActorException extends Exception {
    public FailedToSpawnActorException(Exception innerException) {
        super("Could not spawn actor: "+ innerException.getMessage(), innerException);
    }
}
