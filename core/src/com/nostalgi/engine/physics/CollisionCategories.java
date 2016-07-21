package com.nostalgi.engine.physics;

/**
 * Created by Kristoffer on 2016-07-08.
 */
public class CollisionCategories {

    public static final short CATEGORY_NIL = 0;
    public static final short CATEGORY_PLAYER = 1;
    public static final short CATEGORY_NPC = 2;
    public static final short CATEGORY_ITEM = 4;
    public static final short CATEGORY_MONSTER =8;
    public static final short CATEGORY_WALL = 16;
    public static final short CATEGORY_PROJECTILES = 32;
    public static final short CATEGORY_FLOOR_1 = 64;
    public static final short CATEGORY_FLOOR_2 = 128;
    public static final short CATEGORY_FLOOR_3 = 258;
    public static final short CATEGORY_FLOOR_4 = 512;
    public static final short CATEGORY_TRIGGER = 1024;

    public static final short MASK_PLAYER = CATEGORY_MONSTER |
            CATEGORY_TRIGGER |
            CATEGORY_NPC |
            CATEGORY_ITEM |
            CATEGORY_PROJECTILES;

    public static final short MASK_MONSTER = CATEGORY_PLAYER |
            CATEGORY_TRIGGER |
            CATEGORY_WALL |
            CATEGORY_PROJECTILES |
            CATEGORY_NPC;
    public static final short MASK_WALLS = CATEGORY_PROJECTILES;

    public static final short MASK_TRIGGER = CATEGORY_PLAYER |CATEGORY_NPC |CATEGORY_MONSTER;
}
