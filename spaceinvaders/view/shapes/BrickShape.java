package com.javarush.games.spaceinvaders.view.shapes;

public class BrickShape {
    public static final int[][] BRICK = new int[][]{
            {128, 128, 131, 128, 128, 128, 131, 128, 128, 128},
            {23, 23, 92, 23, 23, 23, 92, 23, 23, 23},
            {92, 92, 92, 92, 92, 92, 92, 92, 92, 92},
            {23, 23, 23, 23, 92, 23, 23, 23, 23, 92},
            {23, 23, 23, 23, 92, 23, 23, 23, 23, 92},
            {92, 92, 92, 92, 92, 92, 92, 92, 92, 92},
            {23, 23, 92, 23, 23, 23, 92, 23, 23, 23},
            {23, 23, 92, 23, 23, 23, 92, 23, 23, 23},
            {92, 92, 92, 92, 92, 92, 92, 92, 92, 92}
    };
    public static final int[][] QUESTION_BRICK_FULL = new int[][]{
            {24, 24, 24, 24, 24, 24, 24, 24, 24, 24},
            {24, 1, 128, 128, 92, 92, 128, 128, 1, 131},
            {24, 128, 128, 92, 128, 128, 92, 128, 128, 131},
            {24, 128, 128, 128, 128, 128, 92, 128, 128, 131},
            {24, 128, 128, 128, 92, 92, 128, 128, 128, 131},
            {24, 128, 128, 128, 92, 128, 128, 128, 128, 131},
            {24, 128, 128, 128, 128, 128, 128, 128, 128, 131},
            {24, 1, 128, 128, 92, 128, 128, 128, 1, 131},
            {131, 131, 131, 131, 131, 131, 131, 131, 131, 131}
    };
    public static final int[][] QUESTION_BRICK_EMPTY = new int[][]{
            {24, 24, 24, 24, 24, 24, 24, 24, 24, 24},
            {24, 1, 128, 128, 128, 128, 128, 128, 1, 131},
            {24, 128, 128, 128, 128, 128, 128, 128, 128, 131},
            {24, 128, 128, 128, 128, 128, 128, 128, 128, 131},
            {24, 128, 128, 128, 128, 128, 128, 128, 128, 131},
            {24, 128, 128, 128, 128, 128, 128, 128, 128, 131},
            {24, 128, 128, 128, 128, 128, 128, 128, 128, 131},
            {24, 1, 128, 128, 128, 128, 128, 128, 1, 131},
            {131, 131, 131, 131, 131, 131, 131, 131, 131, 131}
    };
}
