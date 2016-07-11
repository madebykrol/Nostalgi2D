package com.nostalgi.engine.physics;

/**
 * Created by Kristoffer on 2016-07-08.
 */
public class CollisionCategories {
    public static final short CATEGORY_PLAYER = 0x0001;
    public static final short CATEGORY_NPC = 0x0002;
    public static final short CATEGORY_ITEM = 0x0003;
    public static final short CATEGORY_MONSTER = 0x0004;
    public static final short CATEGORY_WALLS = 0x0005;
    public static final short CATEGORY_PROJECTILES = 0x0006;

    public static final short MASK_PLAYER = CATEGORY_MONSTER |
            CATEGORY_NPC |
            CATEGORY_ITEM |
            CATEGORY_WALLS |
            CATEGORY_PROJECTILES;

    public static final short MASK_MONSTER = CATEGORY_PLAYER |
            CATEGORY_WALLS |
            CATEGORY_PROJECTILES |
            CATEGORY_NPC;

    public static final short MASK_SCENERY = CATEGORY_PLAYER |
            CATEGORY_NPC |
            CATEGORY_MONSTER;



}
