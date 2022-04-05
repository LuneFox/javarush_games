package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.model.InteractiveObject;

/**
 * Logical display to flatten layers and increase the drawing speed, allows making effects.
 */

public class Display extends InteractiveObject {
    private final Pixel[][] matrix;
    private boolean interlacePhase;
    private boolean interlaceEnabled;
    
    public Display() {
        this.x = 0;
        this.y = 0;
        this.height = 100;
        this.width = 100;
        this.matrix = new Pixel[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][x] = new Pixel();
            }
        }
    }

    public void draw() {
        if (interlaceEnabled) {
            interlacedDraw();
        } else {
            simpleDraw();
        }
    }

    private void interlacedDraw() {
        if (interlacePhase) {
            // Phase 1
            for (int y = 0; y < height; y += 2) {
                for (int x = 0; x < width; x++) {
                    game.setCellColor(x, y, matrix[y][x].cellColor);
                }
            }
        } else {
            // Phase 2
            for (int y = 1; y < height; y += 2) {
                for (int x = 0; x < width; x++) {
                    game.setCellColor(x, y, matrix[y][x].cellColor);
                }
            }
        }
        // Switch phase
        interlacePhase = !interlacePhase;
    }

    private void simpleDraw() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                game.setCellColor(x, y, matrix[y][x].cellColor);
            }
        }
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
