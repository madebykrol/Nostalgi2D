package com.nostalgi.engine.interfaces.World;

import com.badlogic.gdx.math.Vector2;
import com.nostalgi.engine.physics.BoundingVolume;

/**
 * Created by Krille on 26/11/2016.
 */

public interface IComponent {

    IActor attachedTo(IActor actor);
    IActor attachedTo();

    /**
     * Relative position to parent Actor.
     * @return
     */
    Vector2 getPosition();

    /**
     * Get World position.
     * @return
     */
    Vector2 getWorldPosition();

    /**
     * Set Position relative to parent Actor.
     * @param position
     */
    void setPosition(Vector2 position);

    /**
     * Set name of component.
     * @param name
     */
    void setName(String name);

    /**
     * Get name of component.
     */
    String getName();

    void tick(float dTime);
}
