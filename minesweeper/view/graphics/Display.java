package com.javarush.games.minesweeper.view.graphics;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.utility.Util;

/**
 * Logical display to flatten layers and increase the drawing speed, allows making effects.
 */

public class Display implements Drawable {
    private final Game game = MinesweeperGame.getInstance();
    private final Pixel[][] matrix;
    private boolean interlacePhase;
    private boolean interlaceEnabled;

    public void setInterlaceEnabled(boolean interlaceEnabled) {
        this.interlaceEnabled = interlaceEnabled;
    }

    public Display() {
        this.matrix = new Pixel[100][100];
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
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
            for (int y = 0; y < matrix.length; y += 2) {
                for (int x = 0; x < matrix[0].length; x++) {
                    game.setCellColor(x, y, matrix[y][x].cellColor);
                }
            }
        } else {
            // Phase 2
            for (int y = 1; y < matrix.length; y += 2) {
                for (int x = 0; x < matrix[0].length; x++) {
                    game.setCellColor(x, y, matrix[y][x].cellColor);
                }
            }
        }
        // Switch phase
        interlacePhase = !interlacePhase;
    }

    private void simpleDraw() {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                game.setCellColor(x, y, matrix[y][x].cellColor);
            }
        }
    }

    // OVERRIDES

    public void setCellColor(int x, int y, Color color) {
        if (Util.isWithinScreen(x, y)) this.matrix[y][x].cellColor = color;
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
