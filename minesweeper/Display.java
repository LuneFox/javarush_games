package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;

public class Display {
    private Game game;
    private Pixel[][] matrix;

    Display(Game game) {
        this.game = game;
        this.matrix = new Pixel[100][100];
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                matrix[y][x] = new Pixel();
            }
        }
    }

    public void draw() {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                game.setCellColor(x, y, matrix[y][x].cellColor);
            }
        }
    }

    // OVERRIDES

    public void setCellColor(int x, int y, Color color) {
        if (x >= 0 && y >= 0 && x < 100 && y < 100) {
            this.matrix[y][x].cellColor = color;
        }
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
