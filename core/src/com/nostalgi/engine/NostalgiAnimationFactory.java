package com.nostalgi.engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nostalgi.engine.interfaces.IAnimationFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Kristoffer on 2016-07-12.
 */
public class NostalgiAnimationFactory implements IAnimationFactory {

    private HashMap<String, Texture> texturePool = new HashMap<String, Texture>();

    @Override
    public Animation createAnimation(String spriteSheet, int frameWidth, int frameHeight, int spriteRows, int spriteCols) {
        return this.createAnimation(spriteSheet, frameWidth, frameHeight, spriteRows, spriteCols, 1f/6f);
    }

    @Override
    public Animation createAnimation(String spriteSheet, int frameWidth, int frameHeight, int spriteRows, int spriteCols, float frameDuration) {
        return createAnimation(spriteSheet, frameWidth, frameHeight, spriteRows, spriteCols, frameDuration, Animation.PlayMode.NORMAL);
    }

    @Override
    public Animation createAnimation(String spriteSheet, int fameWidth, int frameHeight, int spriteRows, int spriteCols, float frameDuration, Animation.PlayMode playMode) {

        if(!this.texturePool.containsKey(spriteSheet)) {
             texturePool.put(spriteSheet, new Texture(spriteSheet));
        }

        TextureRegion[][] tmpFrames = TextureRegion.split(texturePool.get(spriteSheet), 32, 64);

        TextureRegion[] animationFrames = new TextureRegion[spriteRows * spriteCols];
        int index = 0;

        for (int i = 0; i < spriteCols; i++){
            for(int j = 0; j < spriteRows; j++) {
                animationFrames[index++] = tmpFrames[j][i];
            }
        }

        Animation a = new Animation(1f/6f, animationFrames);
        a.setPlayMode(Animation.PlayMode.LOOP);

        return a;
    }

    @Override
    public void dispose() {
        for(Map.Entry<String, Texture> entry : this.texturePool.entrySet()) {
            entry.getValue().dispose();
            this.texturePool.remove(entry.getKey());
        }
    }
}
