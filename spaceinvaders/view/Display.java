package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

public class Display {
    public static final int SIZE = 100;
    private final SpaceInvadersGame game;
    private final Pixel[][] matrix;

    public Display(SpaceInvadersGame game) {
        this.game = game;
        this.matrix = createPixelMatrix();
    }

    public static boolean isWithinScreen(int x, int y) {
        return (x >= 0) && (y >= 0) && (x < SIZE) && (y < SIZE);
    }

    private Pixel[][] createPixelMatrix() {
        Pixel[][] result = new Pixel[SIZE][SIZE];
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                result[y][x] = new Pixel();
            }
        }
        return result;
    }

    public void draw() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                game.setCellColor(x, y, matrix[y][x].cellColor);
            }
        }
    }

    public void drawPixel(int x, int y, Color color) {
        if (isWithinScreen(x, y) && isNotTransparentColor(color)) {
            this.matrix[y][x].cellColor = color;
        }
    }

    private boolean isNotTransparentColor(Color color) {
        return color != Color.NONE;
    }

    public static class Pixel {

        Color cellColor;

        Pixel() {
            this.cellColor = Color.NONE;
        }

    }
}
