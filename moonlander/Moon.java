package com.javarush.games.moonlander;

import com.javarush.engine.cell.Color;

public class Moon {
    private final MoonLanderGame game;
    public final double MIN_RADIUS = 4;
    public final double MAX_RADIUS = 48;
    public double posX;
    public double posY;
    public double radius = MIN_RADIUS;

    public Moon(MoonLanderGame game, double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        this.game = game;
    }

    public void draw() {
        int intRadius = (int) radius;
        double squareRadius = radius * radius;
        for (int x = (-intRadius); x <= intRadius; x++) {
            int drawX = (int) (posX + x);
            double squareX = x * x;
            for (int y = (-intRadius); y <= intRadius; y++) {
                if (!(drawX < 0 || (int) (posY + y) < 0 || drawX > 63 || (int) (posY + y) > 63)) {
                    if (squareX + (y * y) < squareRadius) {
                        game.setCellValueEx((int) posX + x, (int) posY + y, Color.PALEGOLDENROD, "");
                        if (radius > 20 && squareX + (y * y) > squareRadius - 40) {
                            game.setCellColor((int) posX + x, (int) posY + y, Color.TAN);
                        } else if (radius < 20 && squareX + (y * y) > squareRadius - 5) {
                            game.setCellColor((int) posX + x, (int) posY + y, Color.TAN);
                        }
                    }
                }
            }
        }
    }
}
