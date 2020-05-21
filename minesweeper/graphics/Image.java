package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Abstract class that allows drawing stuff using colored cells.
 */

public abstract class Image {
    final protected MinesweeperGame GAME;                // game instance to be drawn into
    private int drawX;
    private int drawY;                        // real position in pixels
    protected int[][] bitmapData;             // matrix of color numbers
    protected Color[] colors;                 // an array to match colors and numbers

    protected Image(Bitmap bitmap, MinesweeperGame game, int drawX, int drawY) { // constructor with setting position at once
        this.colors = new Color[2];
        this.bitmapData = assignBitmap(bitmap);
        this.GAME = game;
        setPosition(drawX, drawY);
    }

    Image(Bitmap bitmap, MinesweeperGame game) { // constructor without setting position (for loading images in memory)
        this.colors = new Color[2];
        this.bitmapData = assignBitmap(bitmap);
        this.GAME = game;
    }

    public void draw() {
        for (int innerY = 0; innerY < bitmapData.length; innerY++) {
            for (int innerX = 0; innerX < bitmapData[0].length; innerX++) {
                if (bitmapData[innerY][innerX] == 0 || colors[bitmapData[innerY][innerX]] == Color.NONE) {
                    continue;
                } // transparent color
                try {
                    GAME.DISPLAY.setCellColor(
                            drawX + innerX,
                            drawY + innerY,
                            colors[bitmapData[innerY][innerX]]
                    );
                } catch (IndexOutOfBoundsException e) {
                    return;
                }
            }
        }
    }

    public final void draw(boolean mirror) { // true reflects X, false reflects Y
        for (int innerY = 0; innerY < bitmapData.length; innerY++) {
            for (int innerX = 0; innerX < bitmapData[0].length; innerX++) {
                if (bitmapData[innerY][innerX] == 0 || colors[bitmapData[innerY][innerX]] == Color.NONE) {
                    continue;
                } // transparent color
                try {
                    if (mirror) {
                        GAME.DISPLAY.setCellColor(
                                drawX + (bitmapData[0].length - 1 - innerX), // flip horizontally
                                drawY + innerY,
                                colors[bitmapData[innerY][innerX]]
                        );
                    } else {
                        GAME.DISPLAY.setCellColor(
                                drawX + innerX,
                                drawY + (bitmapData.length - 1 - innerY),     // flip vertically
                                colors[bitmapData[innerY][innerX]]
                        );
                    }
                } catch (IndexOutOfBoundsException e) {
                    return;
                }
            }
        }
    }

    public final void replaceColor(Color color, int number) {
        try {
            this.colors[number] = color;
        } catch (IndexOutOfBoundsException ignored) {

        }
    }

    public final void setPosition(int drawX, int drawY) {
        if (drawX < 0) { // align X center
            this.drawX = 50 - bitmapData[0].length / 2;
        } else {         // put at position
            this.drawX = drawX;
        }
        if (drawY < 0) { // align Y center
            this.drawY = 50 - bitmapData.length / 2;
        } else {         // put at position
            this.drawY = drawY;
        }
    }

    protected abstract int[][] assignBitmap(Bitmap bitmap); // subclasses' individual pictures go here


    // IMAGE GENERATION

    final int[][] createBitmapFromStrings(String... strings) {
        int sizeX = strings[0].length();
        int sizeY = strings.length;
        int[][] result = new int[sizeY][sizeX];
        for (int y = 0; y < sizeY; y++) {
            char[] row = strings[y].toCharArray();
            for (int x = 0; x < sizeX; x++) {
                result[y][x] = row[x] - 48; // - 48 to convert chars into ints
            }
        }
        return result;
    }


    // WINDOW GENERATION

    final int[][] createWindowBitmap(int sizeX, int sizeY, boolean shadow, boolean frame) {
        if (shadow) { // if shadow is ON, image is 1 px taller and wider
            sizeX++;
            sizeY++;
        }

        int[][] window = new int[sizeY][sizeX];
        generateWindowBackground(window, sizeX, sizeY);

        if (shadow) {
            generateWindowShadow(window, sizeX, sizeY);
        }

        if (frame) {
            generateWindowFrame(window, sizeX, sizeY, shadow);
        }

        return window;
    }

    private void generateWindowShadow(int[][] window, int sizeX, int sizeY) {
        for (int x = 0; x < sizeX; x++) {
            if (x == 0) {
                window[sizeY - 1][x] = 0;
            } else {
                window[sizeY - 1][x] = 2;
            }
        }
        for (int y = 0; y < sizeY; y++) {
            if (y == 0) {
                window[y][sizeX - 1] = 0;
            } else {
                window[y][sizeX - 1] = 2;
            }
        }
    }

    private void generateWindowFrame(int[][] window, int sizeX, int sizeY, boolean shadow) {
        if (shadow) { // if shadow is drawn, shrink the drawing zone back to normal window
            sizeX--;
            sizeY--;
        }
        for (int x = 0; x < sizeX; x++) {
            window[0][x] = 3;
            window[sizeY - 1][x] = 3;
        }
        for (int y = 0; y < sizeY; y++) {
            window[y][0] = 3;
            window[y][sizeX - 1] = 3;
        }
    }

    private void generateWindowBackground(int[][] window, int sizeX, int sizeY) {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                window[y][x] = 1;
            }
        }
    }
}
