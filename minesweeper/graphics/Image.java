package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Abstract class that allows drawing stuff using colored cells.
 */

public abstract class Image {
    final protected MinesweeperGame game;     // game instance to be drawn into
    private int drawX;
    private int drawY;                        // real position in pixels
    protected int[][] bitmapData;             // matrix of color numbers
    protected Color[] colors;                 // an array to match colors and numbers
    private int baseY;                        // initial position Y (anchor for animation)
    private float floatAnimationShift;        // difference between the anchor and current position
    private boolean floatAnimationGoesDown;

    protected Image(Bitmap bitmap, MinesweeperGame game, int drawX, int drawY) { // constructor with setting position at once
        this.colors = new Color[2];
        this.bitmapData = assignBitmap(bitmap);
        this.game = game;
        setPosition(drawX, drawY);
        floatAnimationShift = 0;
        floatAnimationGoesDown = true;
    }

    Image(Bitmap bitmap, MinesweeperGame game) { // constructor without setting position (for loading images in memory)
        this.colors = new Color[2];
        this.bitmapData = assignBitmap(bitmap);
        this.game = game;
    }

    public enum Mirror {
        HORIZONTAL, VERTICAL, NONE
    }

    public void draw() {
        draw(Mirror.NONE);
    }

    public void draw(Mirror mirror) {
        for (int innerY = 0; innerY < bitmapData.length; innerY++) {
            for (int innerX = 0; innerX < bitmapData[0].length; innerX++) {
                int pixel = bitmapData[innerY][innerX];
                boolean colorIsTransparent = (pixel == 0 || colors[pixel] == Color.NONE);
                if (colorIsTransparent) continue;

                switch (mirror) {
                    case HORIZONTAL:
                        game.display.setCellColor(
                                drawX + (bitmapData[0].length - 1 - innerX),
                                drawY + innerY,
                                colors[bitmapData[innerY][innerX]]
                        );
                        break;
                    case VERTICAL:
                        game.display.setCellColor(
                                drawX + innerX,
                                drawY + (bitmapData.length - 1 - innerY),
                                colors[bitmapData[innerY][innerX]]
                        );
                        break;
                    case NONE:
                    default:
                        game.display.setCellColor(
                                drawX + innerX,
                                drawY + innerY,
                                colors[bitmapData[innerY][innerX]]
                        );
                        break;
                }
            }
        }
    }

    public final void floatAnimation(double height, int x, int y) {
        this.setPosition(x, y);
        this.baseY = y;
        floatAnimationShift += (floatAnimationGoesDown ? 0.2 : -0.2);
        if (Math.abs(floatAnimationShift) > height) {
            floatAnimationGoesDown = !floatAnimationGoesDown;
        }
        this.drawY = (int) (baseY + floatAnimationShift);
        this.draw();
    }

    public final void replaceColor(Color color, int number) {
        try {
            this.colors[number] = color;
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    public final void setPosition(int drawX, int drawY) { // negative value = middle
        this.drawX = (drawX < 0) ? (50 - bitmapData[0].length / 2) : drawX;
        this.drawY = (drawY < 0) ? (50 - bitmapData.length / 2) : drawY;
    }

    public final void drawAt(int x, int y) {
        drawAt(x, y, Mirror.NONE);
    }

    public final void drawAt(int x, int y, Mirror mirror) {
        setPosition(x, y);
        draw(mirror);
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
        if (shadow) generateWindowShadow(window, sizeX, sizeY);
        if (frame) generateWindowFrame(window, sizeX, sizeY, shadow);
        return window;
    }

    private void generateWindowShadow(int[][] window, int sizeX, int sizeY) {
        for (int x = 0; x < sizeX; x++) {
            window[sizeY - 1][x] = (x == 0) ? 0 : 2;
        }
        for (int y = 0; y < sizeY; y++) {
            window[y][sizeX - 1] = (y == 0) ? 0 : 2;
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
