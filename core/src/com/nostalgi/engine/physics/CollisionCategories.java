package com.nostalgi.engine.physics;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;

/**
 * Created by Kristoffer on 2016-07-08.
 */
public class CollisionCategories {

    public static final short CATEGORY_NIL = 0;
    public static final short CATEGORY_PLAYER = 1;
    public static final short CATEGORY_ACTOR = 2;
    public static final short CATEGORY_ITEM = 4;
    public static final short CATEGORY_WALL = 8;
    public static final short CATEGORY_PROJECTILES = 16;
    public static final short CATEGORY_FLOOR_1 = 32;
    public static final short CATEGORY_FLOOR_2 = 64;
    public static final short CATEGORY_FLOOR_3 = 128;
    public static final short CATEGORY_FLOOR_4 = 256;
    public static final short CATEGORY_TRIGGER = 512;

    public static final short CATEGORY_CUSTOM_1 = 1024;
    public static final short CATEGORY_CUSTOM_2 = 2048;
    public static final short CATEGORY_CUSTOM_3 = 4096;
    public static final short CATEGORY_CUSTOM_4 = 8192;

    public static final short MASK_PLAYER =
            CATEGORY_TRIGGER |
            CATEGORY_ACTOR |
            CATEGORY_ITEM |
            CATEGORY_PROJECTILES |
            CATEGORY_PLAYER;

    public static final short MASK_ACTOR = CATEGORY_PLAYER |
            CATEGORY_TRIGGER |
            CATEGORY_WALL |
            CATEGORY_PROJECTILES |
            CATEGORY_ACTOR;
    public static final short MASK_WALLS = CATEGORY_PROJECTILES;

    public static final short MASK_NPC = CATEGORY_TRIGGER |
            CATEGORY_WALL |
            CATEGORY_PROJECTILES;

    public static final short MASK_TRIGGER = CATEGORY_PLAYER | CATEGORY_ACTOR ;

    public static short categoryFromString(String s) {
        try {
            return getFieldFromString("CATEGORY_" + s);
        } catch (ClassNotFoundException e) {
            return CATEGORY_NIL;
        } catch (NoSuchFieldException e) {
            return CATEGORY_NIL;
        } catch (IllegalAccessException e) {
            return CATEGORY_NIL;
        }
    }

    public static short maskFromString(String s) {
        try {
            return getFieldFromString("MASK_" + s);
        } catch (ClassNotFoundException e) {
            return CATEGORY_NIL;
        } catch (NoSuchFieldException e) {
            return CATEGORY_NIL;
        } catch (IllegalAccessException e) {
            return CATEGORY_NIL;
        }
    }

    public static short floorFromInt(int floor) {
        return categoryFromString("FLOOR_"+floor);
    }

    public static short getFieldFromString(String s) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        try {
            Class myClass = ClassReflection.forName("com.nostalgi.engine.physics.CollisionCategories");
            Field myField = ClassReflection.getDeclaredField(myClass, s.toUpperCase());
            return (Short)myField.get(null);
        } catch (ReflectionException e) {
            return 0;
        }
    }
}
