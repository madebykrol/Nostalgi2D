package com.nostalgi.engine.Utils;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.randomTriangular;

/**
 *
 * Implementation of Guid from https://github.com/madebykrol/Yogi/blob/master/yogi/framework/utils/Guid.php
 * Created by Krille on 19/11/2016.
 */

public class Guid {
    protected String uuid;

    public Guid(String uuid) {
        this.uuid = uuid;
    }

    public String toString() {
        return uuid;
    }

    public static Guid createNew() {
        return new Guid(String.format("%04x%04x-%04x-%04x-%04x-%04x%04x%04x", // 32 bits for "time_low"
                random.nextInt(0xffff), random.nextInt(0xffff),

                // 16 bits for "time_mid"
                random.nextInt(0xffff),

                // 16 bits for "time_hi_and_version",
                // four most significant bits holds version number 4
                random.nextInt(0x0fff) | 0x4000,

                // 16 bits, 8 bits for "clk_seq_hi_res",
                // 8 bits for "clk_seq_low",
                // two most significant bits holds zero and one for variant DCE1.1
                random.nextInt(0x3fff) | 0x8000,

                // 48 bits for "node"
                random.nextInt(0xffff), random.nextInt(0xffff), random.nextInt(0xffff)));
    }

    public static Guid parse(String uuid) {
        if(!uuid.isEmpty() && uuid.matches("^[A-Za-z0-9]{8}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{12}$")) {
            return new Guid(uuid);
        } else {
            return null;
        }
    }

    public boolean equals(Guid uuid) {
        return this.toString().equals(uuid.toString());
    }
}
