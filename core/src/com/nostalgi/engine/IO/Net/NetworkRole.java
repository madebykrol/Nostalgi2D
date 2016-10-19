package com.nostalgi.engine.IO.Net;

/**
 * Created by Krille on 19/10/2016.
 */

public enum NetworkRole {
    /**
     * Role to indicate that this object is the authority of the current network session. Typically describing servers
     */
    ROLE_AUTHORITY,

    /**
     * Role indicating that this object is the slave of the current network session. Typically describing clients
     */
    ROLE_SLAVE,

}
