package com.javarush.games.spaceinvaders.view.shapes;

import java.util.Arrays;
import java.util.List;

public class TankShape {
    public static final int[][] TANK_1 = new int[][]{
            {33, 0, 0, 0, 33},
            {6, 97, 6, 97, 6},
            {33, 97, 97, 97, 33},
            {6, 6, 97, 6, 6},
            {33, 0, 97, 0, 33},
            {0, 0, 97, 0, 0}
    };
    public static final int[][] TANK_2 = new int[][]{
            {6, 0, 0, 0, 6},
            {33, 97, 6, 97, 33},
            {6, 97, 97, 97, 6},
            {33, 6, 97, 6, 33},
            {6, 0, 97, 0, 6},
            {0, 0, 97, 0, 0}
    };
    public static final int[][] TANK_KILL_1 = new int[][]{
            {71, 0, 0, 0, 71},
            {3, 3, 3, 3, 3},
            {3, 2, 2, 2, 3},
            {3, 2, 2, 2, 3},
            {71, 0, 2, 0, 71},
            {0, 0, 3, 0, 0}
    };
    public static final int[][] TANK_KILL_2 = new int[][]{
            {0, 0, 0, 0, 0},
            {0, 3, 3, 3, 0},
            {0, 2, 0, 2, 0},
            {0, 2, 2, 2, 0},
            {0, 0, 3, 0, 0},
            {0, 0, 0, 0, 0}
    };
    public static final int[][] TANK_KILL_3 = new int[][]{
            {0, 0, 0, 0, 0},
            {0, 0, 68, 0, 0},
            {0, 2, 0, 2, 0},
            {0, 0, 2, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
    };

    private static final List<int[][]> TETRIS_BULLETS = Arrays.asList(
            new int[][]{{71, 71}, {71, 71}},               // square
            new int[][]{{71}, {71}, {71}, {71}},           // stick

            new int[][]{{71, 71}, {71, 0}, {71, 0}},       // Г 0°
            new int[][]{{71, 71, 71}, {0, 0, 71}},         // Г 90°
            new int[][]{{0, 71}, {0, 71}, {71, 71}},       // Г 180°
            new int[][]{{71, 0, 0}, {71, 71, 71}},         // Г 270°

            new int[][]{{71, 0}, {71, 0}, {71, 71}},       // L 0°
            new int[][]{{71, 71, 71}, {71, 0, 0}},         // L 90°
            new int[][]{{71, 71}, {0, 71}, {0, 71}},       // L 180°
            new int[][]{{0, 0, 71}, {71, 71, 71}},         // L 270°

            new int[][]{{71, 71, 71}, {0, 71, 0}},         // Т 0°
            new int[][]{{0, 71}, {71, 71}, {0, 71}},       // Т 90°
            new int[][]{{0, 71, 0}, {71, 71, 71}},         // Т 180°
            new int[][]{{71, 0}, {71, 71}, {71, 0}},       // Т 270°

            new int[][]{{0, 71}, {71, 71}, {71, 0}},       // Z 0°
            new int[][]{{71, 71, 0}, {0, 71, 71}},         // Z 90°
            new int[][]{{71, 0}, {71, 71}, {0, 71}},       // S 0°
            new int[][]{{0, 71, 71}, {71, 71, 0}});        // S 90°

    public static int[][] getTetrisBullet() {
        int randomShapeNumber = (int) (Math.random() * TETRIS_BULLETS.size());
        return TETRIS_BULLETS.get(randomShapeNumber);
    }
}
