package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.model.InteractiveObject;

/**
 * Logical display to flatten layers and increase the drawing speed, allows making effects.
 */
public class Display extends InteractiveObject {
    public static final int SIZE = 100;
    private final Pixel[][] matrix;
    private boolean interlaceEnabled;
    private boolean interlacePhase;

    public Display() {
        setPosition(0, 0);
        this.height = SIZE;
        this.width = SIZE;
        this.matrix = createPixelMatrix();
    }

    private Pixel[][] createPixelMatrix() {
        Pixel[][] result = new Pixel[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result[y][x] = new Pixel();
            }
        }
        return result;
    }

    public void draw() {
        if (interlaceEnabled) {
            drawInterlacedRows();
            switchInterlacePhase();
        } else {
            drawRows(0, 0);
        }
    }

    private void drawInterlacedRows() {
        if (interlacePhase) {
            drawRows(0, 1);
        } else {
            drawRows(1, 1);
        }
    }

    private void drawRows(int startRow, int skip) {
        for (int y = startRow; y < height; y += (skip + 1)) {
            for (int x = 0; x < width; x++) {
                game.setCellColor(x, y, matrix[y][x].cellColor);
            }
        }
    }

    private void switchInterlacePhase() {
        interlacePhase = !interlacePhase;
    }

    public void setCellColor(int x, int y, Color color) {
        if (Util.isWithinScreen(x, y)) {
            this.matrix[y][x].cellColor = color;
        }
    }

    public void setInterlaceEnabled(boolean interlaceEnabled) {
        this.interlaceEnabled = interlaceEnabled;
    }

    public static class Pixel {
        Color cellColor;

        Pixel() {
            this.cellColor = Color.NONE;
        }
    }
}
