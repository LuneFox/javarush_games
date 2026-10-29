package com.javarush.games.spaceinvaders.view.shapes;

/** Кадры орла и его взрыва. */
public class EagleShape {
    /** Обычный орёл. */
    public static final int[][] EAGLE = new int[][]{
            {62, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 62},
            {62, 62, 0, 0, 0, 62, 62, 0, 0, 0, 62, 62},
            {62, 62, 62, 0, 0, 62, 5, 62, 0, 62, 62, 62},
            {62, 5, 62, 0, 0, 62, 62, 0, 0, 62, 5, 62},
            {62, 5, 62, 62, 0, 62, 62, 0, 62, 62, 5, 62},
            {62, 62, 5, 62, 62, 62, 62, 62, 62, 5, 62, 62},
            {0, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 0},
            {0, 0, 62, 62, 0, 62, 62, 0, 62, 62, 0, 0},
            {0, 0, 0, 0, 0, 62, 62, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 62, 62, 62, 62, 0, 0, 0, 0},
            {0, 0, 0, 62, 62, 62, 62, 62, 62, 0, 0, 0},
            {0, 0, 62, 62, 0, 62, 62, 0, 62, 62, 0, 0}
    };

    /** Взорванный орёл, остающийся после анимации. */
    public static final int[][] DESTROYED = new int[][]{
            {62, 0, 0, 0, 0, 0, 0, 0, 0, 51, 0, 62},
            {51, 62, 0, 0, 0, 0, 62, 0, 0, 0, 0, 62},
            {62, 51, 0, 0, 0, 62, 0, 62, 0, 62, 62, 62},
            {62, 53, 62, 0, 0, 51, 0, 0, 0, 62, 5, 62},
            {0, 53, 62, 0, 0, 62, 62, 0, 62, 62, 0, 51},
            {62, 62, 5, 51, 51, 0, 62, 51, 62, 53, 51, 62},
            {0, 62, 62, 0, 62, 51, 62, 62, 62, 62, 62, 0},
            {0, 0, 62, 62, 0, 51, 62, 0, 62, 0, 0, 0},
            {0, 0, 0, 0, 0, 62, 62, 0, 0, 0, 0, 0},
            {0, 51, 0, 0, 0, 51, 0, 62, 0, 0, 0, 51},
            {0, 0, 0, 0, 62, 62, 51, 62, 62, 0, 0, 0},
            {0, 0, 62, 62, 0, 62, 62, 0, 51, 62, 0, 0}
    };

    /** Первый кадр взрыва орла. */
    public static final int[][] EXPLOSION_1 = makeExplosion(1);
    /** Второй кадр взрыва орла. */
    public static final int[][] EXPLOSION_2 = makeExplosion(2);
    /** Третий кадр взрыва орла. */
    public static final int[][] EXPLOSION_3 = makeExplosion(3);

    /** Запрещает создание экземпляров. */
    private EagleShape() {
    }

    /** Строит кадр взрыва с расширяющимся кругом. */
    private static int[][] makeExplosion(int frame) {
        int[][] shape = new int[12][12];
        int centerX = 6;
        int centerY = 6;
        int maxRadius = (frame == 1) ? 3 : (frame == 2) ? 4 : 5;
        int coreRadius = (frame == 1) ? 1 : 2;
        int middleRadius = (frame == 1) ? 2 : (frame == 2) ? 3 : 4;

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                int dx = x - centerX;
                int dy = y - centerY;
                int distance = dx * dx + dy * dy;
                if (distance > maxRadius * maxRadius) continue;

                int sparkle = (x * 7 + y * 11 + frame * 13) % 10;

                if (sparkle == 0) {
                    shape[y][x] = 34;
                } else if (sparkle == 1) {
                    shape[y][x] = 9;
                } else if (distance < coreRadius * coreRadius) {
                    shape[y][x] = (sparkle % 2 == 0) ? 2 : 3;
                } else if (distance < middleRadius * middleRadius) {
                    shape[y][x] = (sparkle % 3 == 0) ? 3 : 4;
                } else {
                    shape[y][x] = (sparkle % 4 == 0) ? 4 : 5;
                }
            }
        }
        return shape;
    }
}
