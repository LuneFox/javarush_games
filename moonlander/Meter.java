package com.javarush.games.moonlander;

public class Meter extends GameObject {
    private Moon moon;
    private MoonLanderGame game;
    private Lander lander;

    public Meter(int x, int y, MoonLanderGame game) {
        super(x, y, new int[45][2]);
        this.game = game;
    }

    public void displayHeight(double radius) {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                matrix[y][x] = (y == (int) (radius - 4) ? 2 : 1);
            }
        }
        draw(game);
    }

    public void displaySpeed(double speed) {
        for (int y = matrix.length - 1; y >= 0; y--) {
            for (int x = 0; x < matrix[0].length; x++) {
                int difference = 22 - ((int) ((speed * 12) + 22.5));
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
