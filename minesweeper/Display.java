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
        this.matrix[y][x].cellColor = color;
    }

    public Color getCellColor(int x, int y) {
        return matrix[y][x].cellColor;
    }

    public void setCellValue(int x, int y, String value) {
        this.matrix[y][x].value = value;
    }

    public String getCellValue(int x, int y) {
        return matrix[y][x].value;
    }

    public void setCellNumber(int x, int y, int value) {
        this.matrix[y][x].number = value;
    }

    public int getCellNumber(int x, int y) {
        return this.matrix[y][x].number;
    }

    public void setCellTextColor(int x, int y, Color color) {
        this.matrix[y][x].textColor = color;
    }

    public Color getCellTextColor(int x, int y) {
        return this.matrix[y][x].textColor;
    }

    public void setCellTextSize(int x, int y, int size) {
        this.matrix[y][x].textSize = size;
    }

    public int getCellTextSize(int x, int y) {
        return this.matrix[y][x].textSize;
    }

    public void setCellValueEx(int x, int y, Color cellColor, String value) {
        this.matrix[y][x].cellColor = cellColor;
        this.matrix[y][x].value = value;
    }

    public void setCellValueEx(int x, int y, Color cellColor, String value, Color textColor) {
        this.matrix[y][x].cellColor = cellColor;
        this.matrix[y][x].value = value;
        this.matrix[y][x].textColor = textColor;
    }

    public void setCellValueEx(int x, int y, Color cellColor, String value, Color textColor, int textSize) {
        this.matrix[y][x].cellColor = cellColor;
        this.matrix[y][x].value = value;
        this.matrix[y][x].textColor = textColor;
        this.matrix[y][x].textSize = textSize;
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
