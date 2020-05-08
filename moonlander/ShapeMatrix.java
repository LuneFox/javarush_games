package com.javarush.games.moonlander;

public class ShapeMatrix {
    public static final int[][] FIRE_DOWN_1 = new int[][]{
            {5, 5},
            {0, 0},
            {4, 4}
    };

    public static final int[][] FIRE_DOWN_2 = new int[][]{
            {0, 0},
            {5, 5},
            {0, 0}
    };

    public static final int[][] FIRE_UP_1 = new int[][]{
            {4, 4},
            {0, 0},
            {5, 5}
    };

    public static final int[][] FIRE_UP_2 = new int[][]{
            {0, 0},
            {5, 5},
            {0, 0}
    };

    public static final int[][] FIRE_RIGHT_1 = new int[][]{
            {4, 0, 5},
            {4, 0, 5}
    };

    public static final int[][] FIRE_RIGHT_2 = new int[][]{
            {0, 5, 0},
            {0, 5, 0}
    };

    public static final int[][] FIRE_LEFT_1 = new int[][]{
            {5, 0, 4},
            {5, 0, 4}
    };

    public static final int[][] FIRE_LEFT_2 = new int[][]{
            {0, 5, 0},
            {0, 5, 0}
    };

    public static final int[][] FIRE_MAIN_1 = new int[][]{
            {0, 0, 5, 0, 0, 0, 0, 5, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 0, 0, 5},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 0, 0, 5},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 5, 0, 0, 0, 0, 5, 0, 0},
    };

    public static final int[][] FIRE_MAIN_2 = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 5, 0, 0, 0, 0, 5, 0, 0},
            {0, 5, 0, 0, 0, 0, 0, 0, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 5, 0, 0, 0, 0, 0, 0, 5, 0},
            {0, 0, 5, 0, 0, 0, 0, 5, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

    };

    public static final int[][] LANDER = new int[][]{
            {9, 0, 0, 0, 9, 9, 0, 0, 0, 9},
            {0, 9, 0, 9, 73, 73, 9, 0, 9, 0},
            {0, 0, 9, 9, 9, 9, 9, 9, 0, 0},
            {0, 9, 9, 21, 9, 9, 21, 9, 9, 0},
            {9, 73, 9, 9, 44, 44, 9, 9, 73, 9},
            {9, 73, 9, 9, 44, 44, 9, 9, 73, 9},
            {0, 9, 9, 21, 9, 9, 21, 9, 9, 0},
            {0, 0, 9, 9, 9, 9, 9, 9, 0, 0},
            {0, 9, 0, 9, 73, 73, 9, 0, 9, 0},
            {9, 0, 0, 0, 9, 9, 0, 0, 0, 9},
    };

    public static final int[][] EARTH = new int[][]{
            {0, 0, 0, 0, 33, 33, 0, 0, 0, 0},
            {0, 0, 33, 33, 7, 7, 33, 33, 0, 0},
            {0, 29, 7, 7, 7, 6, 6, 7, 7, 0},
            {0, 33, 7, 7, 7, 6, 6, 6, 7, 0},
            {33, 6, 6, 6, 7, 7, 6, 6, 7, 7},
            {33, 6, 6, 6, 6, 7, 7, 6, 7, 7},
            {0, 33, 6, 6, 7, 7, 7, 6, 7, 0},
            {0, 33, 33, 6, 7, 6, 6, 7, 33, 0},
            {0, 0, 33, 33, 33, 33, 33, 33, 0, 0},
            {0, 0, 0, 0, 33, 33, 0, 0, 0, 0},
    };

}