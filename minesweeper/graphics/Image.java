package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Abstract class that allows drawing stuff using colored cells.
 */

public abstract class Image implements Drawable {
    protected MinesweeperGame game = MinesweeperGame.getInstance();     // game instance to be drawn into
    private int drawX;
    private int drawY;                        // real position in pixels
    protected int[][] bitmapData;             // matrix of color numbers
    protected Color[] colors;                 // an array to match colors and numbers
    private float floatAnimationShift;        // difference between the anchor and current position
    private boolean floatAnimationGoesDown;

    public enum Mirror {
        HORIZONTAL, VERTICAL, NONE
    }

    protected Image(Bitmap bitmap, int drawX, int drawY) { // constructor with setting position at once
        this.colors = new Color[2];
        this.bitmapData = assignBitmap(bitmap);
        setPosition(drawX, drawY);
        floatAnimationShift = 0;
        floatAnimationGoesDown = true;
    }

    Image(Bitmap bitmap) { // constructor without setting position (for loading images in memory)
        this.colors = new Color[2];
        this.bitmapData = assignBitmap(bitmap);
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

    public final void animateFloating(double height, int x, int y) {
        this.setPosition(x, y);
        floatAnimationShift += (floatAnimationGoesDown ? 0.2 : -0.2);
        if (Math.abs(floatAnimationShift) > height) {
            floatAnimationGoesDown = !floatAnimationGoesDown;
        }
        this.drawY = (int) (y + floatAnimationShift);
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
}
