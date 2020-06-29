package com.javarush.games.spaceinvaders.shapes;

public class ObjectShape {
    public static final int[][] BRICK = new int[][]{{128, 128, 131, 128, 128, 128, 131, 128, 128, 128}, {23, 23, 92, 23, 23, 23, 92, 23, 23, 23}, {92, 92, 92, 92, 92, 92, 92, 92, 92, 92}, {23, 23, 23, 23, 92, 23, 23, 23, 23, 92}, {23, 23, 23, 23, 92, 23, 23, 23, 23, 92}, {92, 92, 92, 92, 92, 92, 92, 92, 92, 92}, {23, 23, 92, 23, 23, 23, 92, 23, 23, 23}, {23, 23, 92, 23, 23, 23, 92, 23, 23, 23}, {92, 92, 92, 92, 92, 92, 92, 92, 92, 92}};
    public static final int[][] QUESTION_BRICK = new int[][]{{24, 24, 24, 24, 24, 24, 24, 24, 24, 24}, {24, 1, 128, 128, 92, 92, 128, 128, 1, 131}, {24, 128, 128, 92, 128, 128, 92, 128, 128, 131}, {24, 128, 128, 128, 128, 128, 92, 128, 128, 131}, {24, 128, 128, 128, 92, 92, 128, 128, 128, 131}, {24, 128, 128, 128, 92, 128, 128, 128, 128, 131}, {24, 128, 128, 128, 128, 128, 128, 128, 128, 131}, {24, 1, 128, 128, 92, 128, 128, 128, 1, 131}, {131, 131, 131, 131, 131, 131, 131, 131, 131, 131}};
    public static final int[][] QUESTION_BRICK_EMPTY = new int[][]{{24, 24, 24, 24, 24, 24, 24, 24, 24, 24}, {24, 1, 128, 128, 128, 128, 128, 128, 1, 131}, {24, 128, 128, 128, 128, 128, 128, 128, 128, 131}, {24, 128, 128, 128, 128, 128, 128, 128, 128, 131}, {24, 128, 128, 128, 128, 128, 128, 128, 128, 131}, {24, 128, 128, 128, 128, 128, 128, 128, 128, 131}, {24, 128, 128, 128, 128, 128, 128, 128, 128, 131}, {24, 1, 128, 128, 128, 128, 128, 128, 1, 131}, {131, 131, 131, 131, 131, 131, 131, 131, 131, 131}};
    public static final int[][] COIN_WIDTH_7 = new int[][]{{0, 0, 59, 59, 59, 0, 0}, {0, 59, 68, 68, 68, 59, 0}, {59, 68, 68, 60, 68, 68, 59}, {59, 68, 60, 68, 131, 68, 59}, {59, 68, 60, 68, 131, 68, 59}, {59, 68, 60, 68, 131, 68, 59}, {59, 68, 68, 131, 68, 68, 59}, {0, 59, 68, 68, 68, 59, 0}, {0, 0, 59, 59, 59, 0, 0}};
    public static final int[][] COIN_WIDTH_5 = new int[][]{{0, 0, 59, 59, 59, 0, 0}, {0, 59, 68, 68, 68, 59, 0}, {0, 59, 68, 60, 68, 59, 0}, {0, 59, 60, 68, 131, 59, 0}, {0, 59, 60, 68, 131, 59, 0}, {0, 59, 60, 68, 131, 59, 0}, {0, 59, 68, 131, 68, 59, 0}, {0, 59, 68, 68, 68, 59, 0}, {0, 0, 59, 59, 59, 0, 0}};
    public static final int[][] COIN_WIDTH_3 = new int[][]{{0, 0, 0, 59, 0, 0, 0}, {0, 0, 59, 68, 59, 0, 0}, {0, 0, 59, 60, 59, 0, 0}, {0, 0, 59, 60, 59, 0, 0}, {0, 0, 59, 60, 59, 0, 0}, {0, 0, 59, 131, 59, 0, 0}, {0, 0, 59, 131, 59, 0, 0}, {0, 0, 59, 68, 59, 0, 0}, {0, 0, 0, 59, 0, 0, 0}};
    public static final int[][] COIN_WIDTH_1 = new int[][]{{0, 0, 0, 60, 0, 0, 0}, {0, 0, 0, 59, 0, 0, 0}, {0, 0, 0, 59, 0, 0, 0}, {0, 0, 0, 59, 0, 0, 0}, {0, 0, 0, 59, 0, 0, 0}, {0, 0, 0, 59, 0, 0, 0}, {0, 0, 0, 59, 0, 0, 0}, {0, 0, 0, 59, 0, 0, 0}, {0, 0, 0, 60, 0, 0, 0}};
    public static final int[][] BOSS_TANK_1 = new int[][]{{4, 4, 31, 0, 0, 0, 0, 0, 0, 0, 4, 4, 68}, {31, 31, 31, 0, 31, 31, 31, 31, 31, 0, 68, 31, 31}, {4, 4, 31, 31, 4, 4, 4, 68, 68, 31, 68, 4, 68}, {31, 31, 31, 4, 4, 31, 31, 4, 68, 68, 68, 31, 31}, {4, 4, 31, 4, 4, 31, 4, 68, 4, 68, 68, 4, 68}, {31, 31, 31, 4, 4, 31, 4, 68, 4, 68, 68, 31, 31}, {4, 4, 31, 4, 4, 4, 68, 68, 4, 68, 68, 4, 68}, {31, 31, 31, 31, 4, 4, 4, 4, 68, 68, 68, 31, 31}, {4, 4, 31, 0, 31, 31, 68, 4, 68, 0, 68, 4, 68}, {31, 31, 31, 0, 0, 0, 68, 0, 0, 0, 68, 31, 31}, {4, 4, 68, 0, 0, 0, 68, 0, 0, 0, 4, 4, 68}, {0, 0, 0, 0, 0, 0, 68, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 68, 0, 0, 0, 0, 0, 0}};
    public static final int[][] BOSS_TANK_2 = new int[][]{{31, 31, 31, 0, 0, 0, 0, 0, 0, 0, 4, 31, 31}, {4, 4, 31, 0, 31, 31, 31, 31, 31, 0, 68, 4, 68}, {31, 31, 31, 31, 4, 4, 4, 68, 68, 31, 68, 31, 31}, {4, 4, 31, 4, 4, 31, 31, 4, 68, 68, 68, 4, 68}, {31, 31, 31, 4, 4, 31, 4, 68, 4, 68, 68, 31, 31}, {4, 4, 31, 4, 4, 31, 4, 68, 4, 68, 68, 4, 68}, {31, 31, 31, 4, 4, 4, 68, 68, 4, 68, 68, 31, 31}, {4, 4, 31, 31, 4, 4, 4, 4, 68, 68, 68, 4, 68}, {31, 31, 31, 0, 31, 31, 68, 4, 68, 0, 68, 31, 31}, {4, 4, 31, 0, 0, 0, 68, 0, 0, 0, 68, 4, 68}, {31, 31, 68, 0, 0, 0, 68, 0, 0, 0, 4, 31, 31}, {0, 0, 0, 0, 0, 0, 68, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 68, 0, 0, 0, 0, 0, 0}};
    public static final int[][] BOSS_TANK_KILL_1 = new int[][]{{68, 68, 68, 0, 0, 0, 0, 0, 0, 0, 68, 68, 68}, {68, 2, 68, 0, 68, 68, 68, 68, 68, 0, 68, 2, 68}, {68, 2, 68, 68, 68, 2, 2, 2, 68, 68, 68, 2, 68}, {68, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 68}, {68, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 68}, {68, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 68}, {68, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 68}, {68, 2, 68, 68, 68, 2, 2, 2, 68, 68, 68, 2, 68}, {68, 2, 68, 0, 68, 68, 68, 68, 68, 0, 68, 2, 68}, {68, 2, 68, 0, 0, 0, 68, 0, 0, 0, 68, 2, 68}, {68, 68, 68, 0, 0, 0, 68, 0, 0, 0, 68, 68, 68}, {0, 0, 0, 0, 0, 0, 68, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 68, 0, 0, 0, 0, 0, 0}};
    public static final int[][] BOSS_TANK_KILL_2 = new int[][]{{0, 0, 0, 59, 0, 0, 0, 0, 0, 59, 0, 0, 0}, {0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0}, {0, 2, 0, 0, 0, 2, 2, 2, 0, 0, 0, 2, 0}, {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0}, {0, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 0}, {0, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 0}, {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0}, {0, 2, 0, 0, 0, 2, 2, 2, 0, 0, 0, 2, 0}, {0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0}, {0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0}, {0, 0, 0, 59, 0, 0, 2, 0, 0, 59, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    public static final int[][] BOSS_TANK_KILL_3 = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 68, 68, 68, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 68, 2, 2, 2, 68, 0, 0, 0, 0}, {0, 0, 0, 0, 68, 2, 0, 2, 68, 0, 0, 0, 0}, {0, 0, 0, 0, 68, 2, 0, 2, 68, 0, 0, 0, 0}, {0, 0, 0, 0, 68, 2, 2, 2, 68, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 68, 68, 68, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    public static final int[][] BOSS_TANK_KILL_4 = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 34, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 34, 0, 0, 0, 34, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 68, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 68, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 34, 0, 0, 0, 34, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 34, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    public static final int[][] TANK_1 = new int[][]{{33, 0, 0, 0, 33}, {6, 97, 6, 97, 6}, {33, 97, 97, 97, 33}, {6, 6, 97, 6, 6}, {33, 0, 97, 0, 33}, {0, 0, 97, 0, 0}};
    public static final int[][] TANK_2 = new int[][]{{6, 0, 0, 0, 6}, {33, 97, 6, 97, 33}, {6, 97, 97, 97, 6}, {33, 6, 97, 6, 33}, {6, 0, 97, 0, 6}, {0, 0, 97, 0, 0}};
    public static final int[][] TANK_KILL_1 = new int[][]{{71, 0, 0, 0, 71}, {3, 3, 3, 3, 3}, {3, 2, 2, 2, 3}, {3, 2, 2, 2, 3}, {71, 0, 2, 0, 71}, {0, 0, 3, 0, 0}};
    public static final int[][] TANK_KILL_2 = new int[][]{{0, 0, 0, 0, 0}, {0, 3, 3, 3, 0}, {0, 2, 0, 2, 0}, {0, 2, 2, 2, 0}, {0, 0, 3, 0, 0}, {0, 0, 0, 0, 0}};
    public static final int[][] TANK_KILL_3 = new int[][]{{0, 0, 0, 0, 0}, {0, 0, 68, 0, 0}, {0, 2, 0, 2, 0}, {0, 0, 2, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
    public static final int[][] BOSS_TANK_AMMO = new int[][]{{59, 59, 59}, {59, 59, 59}, {3, 59, 3}, {0, 3, 0}};
    public static final int[][] MUSHROOM = new int[][]{{0, 0, 0, 4, 5, 5, 4, 0, 0, 0}, {0, 0, 4, 4, 5, 5, 4, 5, 0, 0}, {0, 4, 4, 4, 4, 4, 4, 5, 5, 0}, {0, 4, 5, 5, 4, 4, 4, 4, 4, 0}, {5, 4, 5, 5, 4, 4, 5, 5, 4, 4}, {4, 4, 4, 4, 4, 4, 5, 5, 4, 5}, {0, 5, 5, 4, 2, 2, 4, 4, 4, 0}, {0, 0, 0, 2, 2, 2, 2, 0, 0, 0}, {0, 0, 0, 2, 2, 2, 105, 0, 0, 0}, {0, 0, 0, 0, 2, 105, 0, 0, 0, 0}};
    public static final int[][] STAR = new int[][]{{0, 0, 0, 0, 3, 0, 0, 0, 0}, {0, 0, 0, 3, 3, 3, 0, 0, 0}, {0, 0, 0, 3, 3, 3, 0, 0, 0}, {3, 3, 3, 3, 3, 3, 3, 3, 3}, {0, 3, 3, 1, 3, 1, 3, 3, 0}, {0, 0, 3, 1, 3, 1, 3, 0, 0}, {0, 0, 3, 3, 3, 3, 3, 0, 0}, {0, 3, 3, 3, 0, 3, 3, 3, 0}, {0, 3, 3, 0, 0, 0, 3, 3, 0}};
    public static final int[][] FIREBALL_1 = new int[][]{{0, 5, 0, 5, 5, 0, 0, 0}, {0, 0, 5, 0, 5, 5, 5, 0}, {5, 0, 0, 0, 5, 4, 5, 5}, {0, 0, 5, 5, 5, 4, 4, 5}, {0, 5, 5, 4, 4, 2, 4, 5}, {0, 5, 4, 4, 2, 4, 5, 5}, {0, 5, 5, 4, 4, 5, 5, 0}, {0, 0, 5, 5, 5, 5, 0, 0}};
    public static final int[][] FIREBALL_2 = new int[][]{{0, 0, 0, 0, 0, 5, 0, 0}, {0, 5, 5, 5, 0, 0, 0, 5}, {5, 5, 4, 5, 5, 0, 5, 0}, {5, 4, 4, 4, 5, 0, 0, 5}, {5, 4, 2, 4, 5, 5, 5, 5}, {5, 5, 4, 2, 4, 4, 5, 0}, {0, 5, 5, 4, 4, 5, 5, 0}, {0, 0, 5, 5, 5, 5, 0, 0}};
    public static final int[][] FIREBALL_3 = new int[][]{{0, 0, 5, 5, 5, 5, 0, 0}, {0, 5, 5, 4, 4, 5, 5, 0}, {5, 5, 4, 2, 4, 4, 5, 0}, {5, 4, 2, 4, 4, 5, 5, 0}, {5, 4, 4, 5, 5, 5, 0, 0}, {5, 5, 4, 5, 0, 0, 0, 5}, {0, 5, 5, 5, 0, 5, 0, 0}, {0, 0, 0, 5, 5, 0, 5, 0}};
    public static final int[][] FIREBALL_4 = new int[][]{{0, 0, 5, 5, 5, 5, 0, 0}, {0, 5, 5, 4, 4, 5, 5, 0}, {0, 5, 4, 4, 2, 4, 5, 5}, {5, 5, 5, 5, 4, 2, 4, 5}, {5, 0, 0, 5, 4, 4, 4, 5}, {0, 5, 0, 5, 5, 4, 5, 5}, {5, 0, 0, 0, 5, 5, 5, 0}, {0, 0, 5, 0, 0, 0, 0, 0}};
    public static final int[][] BULLET = new int[][]{{71}, {71}};
}


