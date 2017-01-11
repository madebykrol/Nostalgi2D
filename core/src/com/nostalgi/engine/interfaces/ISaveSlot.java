package com.nostalgi.engine.interfaces;

import java.util.Date;

/**
 * Created by ksdkrol on 2017-01-11.
 */

public interface ISaveSlot {
    Date getLastSaveDate();
    int getSlot();
    boolean isUsed();
}
