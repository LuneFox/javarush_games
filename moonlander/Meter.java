package com.javarush.games.moonlander;

class Meter extends GameObject {
    private MoonLanderGame game;

    Meter(int x, int y, MoonLanderGame game) {
        super(x, y, new int[45][2]);
        this.game = game;
    }

    void displayHeight(double radius) {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                matrix[y][x] = (y == (int) (radius - 4) ? 3 : 1);
            }
        }
        draw(game);
    }

    void displaySpeed(double speed) {
        for (int y = matrix.length - 1; y >= 0; y--) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (y > 22 && y < 22 + (int) (speed * 12)) {
                    matrix[y][x] = 22;
                } else if (y < 22 && y > 22 + (int) (speed * 12)) {
                    matrix[y][x] = 22;
                } else {
                    matrix[y][x] = 1;
                }
            }
        }
        matrix[22][0] = 5;
        matrix[22][1] = 5;
        draw(game);
    }
}
