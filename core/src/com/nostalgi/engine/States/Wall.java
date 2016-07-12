package com.nostalgi.engine.States;

import com.badlogic.gdx.physics.box2d.Body;
import com.nostalgi.engine.interfaces.World.IWall;

/**
 * Created by Kristoffer on 2016-07-10.
 */
public class Wall implements IWall {

    protected Body body;

    public Wall() {

    }

    public Wall(Body body) {
        this.body = body;
    }

    @Override
    public Body getBody() {
        return this.body;
    }

    @Override
    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Body) {

            Body b = (Body)o;
            return (this.body.equals(b));
        } else if (o instanceof Wall) {
            Wall w = (Wall)o;
            return this.equals(w);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash+((this.body != null) ? this.body.hashCode() : 0);

        return hash;
    }
}
