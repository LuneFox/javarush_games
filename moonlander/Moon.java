package com.javarush.games.moonlander;

import com.javarush.engine.cell.Color;

public class Moon {
    private final MoonLanderGame game;
    public double posX;
    public double posY;
    public double radius = 1;

    public Moon(MoonLanderGame game, double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        this.game = game;
    }

    public void draw() {
        int intRadius = (int) radius;
        double squareRadius = radius * radius;
        for (int x = (-intRadius); x <= intRadius; x++) {
            double drawX = posX + x;
            double squareX = x * x;
            for (int y = (-intRadius); y <= intRadius; y++) {
                if (!(drawX < 0 || posY + y < 0 || drawX > MoonLanderGame.WIDTH - 1 || posY + y > MoonLanderGame.HEIGHT - 1)) {
                    if (squareX + (y * y) < squareRadius) {
                        game.setCellColor((int) posX + x, (int) posY + y, Color.YELLOW);
                    }
                }
            }
        }
    }
}
