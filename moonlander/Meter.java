package com.javarush.games.moonlander;

public class Meter extends GameObject {
    private Moon moon;
    private MoonLanderGame game;

    public Meter(MoonLanderGame game, Moon moon) {
        super(66, 19, new int[45][2]);
        this.game = game;
        this.moon = moon;
    }

    public void display() {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                matrix[y][x] = (y == (int) (moon.radius - 4) ? 2 : 1);
            }
        }
        draw(game);
    }
}
