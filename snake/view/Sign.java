package com.javarush.games.snake.view;

public enum Sign {
    SNAKE_HEAD(new String[]{"頭", "\uD83D\uDC32"}),
    SNAKE_DEAD(new String[]{"死", "\uD83D\uDC80"}),
    SNAKE_BODY(new String[]{"体", "S"}),

    TERRAIN_FIELD(new String[]{"w", "w"}),
    TERRAIN_WOOD(new String[]{"木", "\uD83C\uDF42"}),
    TERRAIN_WATER(new String[]{"川", "~"}),
    TERRAIN_FIRE(new String[]{"炎", "\uD83D\uDD25"}),
    TERRAIN_FOREST_1(new String[]{"森", "\uD83C\uDF33"}),
    TERRAIN_FOREST_2(new String[]{"林", "\uD83C\uDF32"}),
    TERRAIN_WORMHOLE(new String[]{"下", "\uD83D\uDD73"}),
    TERRAIN_MOUNTAIN(new String[]{"山", "\uD83C\uDFD4"}),
    TERRAIN_WALL(new String[]{"壁", "⬜"}),
    TERRAIN_SAND(new String[]{". -", ". -"}),
    TERRAIN_VOID(new String[]{"", ""}),

    ORB_NEUTRAL(new String[]{"玉", "\uD83D\uDD2E"}),
    ORB_WATER(new String[]{"水", "\uD83D\uDCA7"}),
    ORB_FIRE(new String[]{"火", "\uD83C\uDF0B"}),
    ORB_EARTH(new String[]{"土", "\uD83C\uDF31"}),
    ORB_AIR(new String[]{"風", "\uD83C\uDF2A️"}),
    ORB_ALMIGHTY(new String[]{"力", "⚡"});

    private static SignType usedType;
    private final String[] appearances;

    Sign(String[] appearances) {
        this.appearances = appearances;
    }

    public static void switchSet() {
        usedType = (usedType == SignType.KANJI) ? SignType.EMOJI : SignType.KANJI;
    }

    public static String getSign(Sign sign) {
        return sign.appearances[usedType.ordinal()];
    }

    public static void setUsedType(SignType type) {
        Sign.usedType = type;
    }

    public static SignType getUsedType() {
        return usedType;
    }
}
