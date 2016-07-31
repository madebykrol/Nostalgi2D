package com.nostalgi.engine.interfaces.Factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Kristoffer on 2016-07-12.
 */
public interface IAnimationFactory extends Disposable {
    Animation createAnimation(String spriteSheet,
                              int frameWidth,
                              int frameHeight,
                              int spriteRows,
                              int spriteCols);
    Animation createAnimation(String spriteSheet,
                              int frameWidth,
                              int frameHeight,
                              int spriteRows,
                              int spriteCols,
                              float frameDuration);
    Animation createAnimation(String spriteSheet,
                              int frameWidth,
                              int frameHeight,
                              int spriteRows,
                              int spriteCols,
                              float frameDuration,
                              Animation.PlayMode playMode);
}
