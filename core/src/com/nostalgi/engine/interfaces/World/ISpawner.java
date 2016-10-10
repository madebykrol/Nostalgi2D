package com.nostalgi.engine.interfaces.World;

/**
 * Created by ksdkrol on 2016-07-03.
 */
public interface ISpawner extends IActor {
    void onSpawn();
    void onDespawn();

    IActor spawn();
    void despawn(int id);
}
