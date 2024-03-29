package com.javarush.games.spaceinvaders.view.shapes;

public class DecoShape {
    public static final int[][] FLOOR = new int[][]{
            {143, 118, 118, 118, 118, 118, 118, 118, 118, 126, 143, 118, 118, 118, 118, 143},
            {118, 143, 143, 143, 143, 143, 143, 143, 143, 126, 118, 143, 143, 143, 143, 126},
            {118, 143, 143, 143, 143, 143, 143, 143, 143, 126, 118, 143, 143, 143, 143, 126},
            {118, 143, 143, 143, 143, 143, 143, 143, 143, 126, 118, 143, 143, 143, 143, 126}};

    public static final int[][] BUSH = new int[][]{
            {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 89, 89, 89, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 89, 89, 89, 89, 89, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0},
            {0, 0, 1, 89, 55, 55, 89, 1, 0, 1, 89, 89, 89, 1, 0, 1, 89, 89, 1, 0},
            {0, 1, 1, 89, 89, 89, 55, 89, 1, 89, 89, 55, 55, 89, 1, 89, 89, 89, 89, 1},
            {1, 89, 89, 89, 89, 89, 89, 89, 89, 89, 55, 89, 89, 89, 89, 89, 55, 55, 89, 1},
            {1, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 55, 89, 89, 1, 0},
            {0, 1, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 1, 0}};

    public static final int[][] HILL = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 33, 33, 33, 33, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 33, 33, 33, 33, 33, 1, 33, 33, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 33, 33, 33, 33, 33, 1, 1, 1, 33, 33, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 33, 33, 33, 33, 33, 33, 1, 1, 1, 33, 33, 33, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 33, 33, 33, 33, 33, 33, 33, 1, 1, 1, 33, 33, 33, 33, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 33, 33, 33, 33, 33, 1, 1, 33, 33, 1, 33, 33, 33, 33, 33, 33, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 33, 33, 33, 33, 33, 33, 1, 1, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 1, 0, 0, 0},
            {0, 0, 1, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 1, 0, 0},
            {0, 1, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 1, 0},
            {1, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 1}};

    public static final int[][] CLOUD = new int[][]{
            {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 14, 133, 14, 1, 0, 0, 1, 1, 1, 0, 1, 14, 14, 14, 1, 1, 1, 0, 0},
            {0, 0, 1, 1, 14, 133, 69, 133, 14, 1, 1, 14, 14, 14, 1, 14, 14, 133, 69, 14, 14, 14, 1, 0},
            {1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 133, 69, 14, 14, 133, 69, 133, 14, 14, 14, 14, 1},
            {1, 14, 133, 69, 14, 133, 69, 14, 14, 14, 14, 133, 69, 133, 14, 14, 14, 14, 14, 14, 133, 69, 14, 1},
            {0, 1, 14, 133, 133, 69, 14, 14, 14, 1, 1, 14, 14, 14, 14, 14, 14, 14, 1, 14, 14, 14, 1, 0},
            {0, 0, 1, 14, 14, 14, 14, 14, 1, 0, 0, 1, 1, 1, 14, 14, 14, 1, 0, 1, 1, 1, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0}};
}
