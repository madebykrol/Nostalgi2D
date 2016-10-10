package com.nostalgi.engine.interfaces.World;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface IDoor extends IActor {
    void onTryOpen(IActor instigator);
    void tryOpen();
    void onOpen();
    void onClose();
    boolean isLocked();
}
