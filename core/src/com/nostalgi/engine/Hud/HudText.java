package com.nostalgi.engine.Hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Kristoffer on 2016-07-12.
 */
public class HudText extends Actor {
    //Remember to pass this in!


    protected BitmapFont font;
    protected String text;
    protected float x;
    protected float y;

    public HudText(String text, float screenX, float screenY){
        font = new BitmapFont();
        font.setColor(0.5f,0.4f,0,1);   //Brown is an underrated Colour
        this.text = text;
        this.x = screenX;
        this.y = screenY;
    }

    public void update(String text) {
        this.text = text;
    }

    public void update(String text, float screenX, float screenY) {
        update(text);
        this.x = screenX;
        this.y = screenY;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, text, x, y);
        //Also remember that an actor uses local coordinates for drawing within
        //itself!
    }
}
