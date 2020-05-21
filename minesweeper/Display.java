package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;

public class Display extends Game {
    private Game game;
    private Pixel[][] matrix;

    public Display(Game game) {
        this.game = game;
        this.matrix = new Pixel[game.getScreenHeight()][game.getScreenWidth()];
    }

    public void draw() {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                game.setCellColor(x, y, matrix[y][x].cellColor);
            }
        }
    }

    // OVERRIDES

    @Override
    public void setCellColor(int x, int y, Color color) {
        this.matrix[y][x].cellColor = color;
    }

    @Override
    public Color getCellColor(int x, int y) {
        return matrix[y][x].cellColor;
    }

    @Override
    public void setCellValue(int x, int y, String value) {
        this.matrix[y][x].value = value;
    }

    @Override
    public String getCellValue(int x, int y) {
        return matrix[y][x].value;
    }

    @Override
    public void setCellNumber(int x, int y, int value) {
        this.matrix[y][x].number = value;
    }

    @Override
    public int getCellNumber(int x, int y) {
        return this.matrix[y][x].number;
    }

    @Override
    public void setCellTextColor(int x, int y, Color color) {
        this.matrix[y][x].textColor = color;
    }

    @Override
    public Color getCellTextColor(int x, int y) {
        return this.matrix[y][x].textColor;
    }

    @Override
    public void setCellTextSize(int x, int y, int size) {
        this.matrix[y][x].textSize = size;
    }

    @Override
    public int getCellTextSize(int x, int y) {
        return this.matrix[y][x].textSize;
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value) {
        this.matrix[y][x].cellColor = cellColor;
        this.matrix[y][x].value = value;
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value, Color textColor) {
        this.matrix[y][x].cellColor = cellColor;
        this.matrix[y][x].value = value;
        this.matrix[y][x].textColor = textColor;
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value, Color textColor, int textSize) {
        this.matrix[y][x].cellColor = cellColor;
        this.matrix[y][x].value = value;
        this.matrix[y][x].textColor = textColor;
        this.matrix[y][x].textSize = textSize;
    }


    // INNER CLASSES

    public class Pixel {
        Color cellColor;
        Color textColor;
        String value;
        int textSize;
        int number;
    }
}
