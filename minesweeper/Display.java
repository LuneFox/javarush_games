package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;

/**
 * Logical display to flatten layers and increase the drawing speed, allows making effects.
 */

public class Display {
    private final Game game;
    private final Pixel[][] matrix;
    private boolean interlace;

    Display(Game game) {
        this.game = game;
        this.matrix = new Pixel[100][100];
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                matrix[y][x] = new Pixel();
            }
        }
        interlace = false;
    }

    public void draw() {
        if (Screen.getType() == Screen.ScreenType.BOARD) {
            if (interlace) {
                for (int y = 0; y < matrix.length; y += 2) {
                    for (int x = 0; x < matrix[0].length; x++) {
                        game.setCellColor(x, y, matrix[y][x].cellColor);
                    }
                }
            } else {
                for (int y = 1; y < matrix.length; y += 2) {
                    for (int x = 0; x < matrix[0].length; x++) {
                        game.setCellColor(x, y, matrix[y][x].cellColor);
                    }
                }
            }
            interlace = !interlace;
        } else {
            for (int y = 0; y < matrix.length; y++) {
                for (int x = 0; x < matrix[0].length; x++) {
                    game.setCellColor(x, y, matrix[y][x].cellColor);
                }
            }
        }
    }

    // OVERRIDES

    public void setCellColor(int x, int y, Color color) {
        if (Util.isOnScreen(x, y)) this.matrix[y][x].cellColor = color;
    }

    // INNER CLASSES

    public static class Pixel {
        Color cellColor;
        Color textColor;
        String value;
        int textSize;
        int number;

        Pixel() {
            this.cellColor = Color.NONE;
            this.textColor = Color.NONE;
            this.value = "";
            this.textSize = 0;
            this.number = 0;
        }
    }
}
