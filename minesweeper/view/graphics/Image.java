package com.javarush.games.minesweeper.view.graphics;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * An image that is drawn using colors and the number matrix. Can be mirrored, recolored etc.
 */

public class Image implements Drawable {
    protected MinesweeperGame game = MinesweeperGame.getInstance();     // game instance to be drawn into
    private int drawX;
    private int drawY;                        // real position in pixels
    public int[][] matrix;                 // matrix of color numbers
    public Color[] colors;                 // an array to match colors and numbers
    private float floatAnimationShift;        // difference between the anchor and current position
    private boolean floatAnimationGoesDown;

    public enum Mirror {
        HORIZONTAL, VERTICAL, NONE
    }

    public Image(VisualElement visualElement, int drawX, int drawY) { // constructor with setting position at once
        this.matrix = getMatrix(visualElement);
        setPosition(drawX, drawY);
        floatAnimationShift = 0;
        floatAnimationGoesDown = true;
    }

    Image(VisualElement visualElement) { // constructor without setting position (for loading images in memory)
        this.matrix = getMatrix(visualElement);
    }

    public void draw() {
        draw(Mirror.NONE);
    }

    public void draw(Mirror mirror) {
        for (int innerY = 0; innerY < matrix.length; innerY++) {
            for (int innerX = 0; innerX < matrix[0].length; innerX++) {
                int pixel = matrix[innerY][innerX];
                boolean colorIsTransparent = (pixel == 0 || colors[pixel] == Color.NONE);
                if (colorIsTransparent) continue;

                switch (mirror) {
                    case HORIZONTAL:
                        game.display.setCellColor(
                                drawX + (matrix[0].length - 1 - innerX),
                                drawY + innerY,
                                colors[matrix[innerY][innerX]]
                        );
                        break;
                    case VERTICAL:
                        game.display.setCellColor(
                                drawX + innerX,
                                drawY + (matrix.length - 1 - innerY),
                                colors[matrix[innerY][innerX]]
                        );
                        break;
                    case NONE:
                    default:
                        game.display.setCellColor(
                                drawX + innerX,
                                drawY + innerY,
                                colors[matrix[innerY][innerX]]
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
        this.drawX = (drawX < 0) ? (50 - matrix[0].length / 2) : drawX;
        this.drawY = (drawY < 0) ? (50 - matrix.length / 2) : drawY;
    }

    public final void drawAt(int x, int y) {
        drawAt(x, y, Mirror.NONE);
    }

    public final void drawAt(int x, int y, Mirror mirror) {
        setPosition(x, y);
        draw(mirror);
    }

    public int[][] getMatrix(VisualElement visualElement) {
        ImageStorage storage = new ImageStorage(visualElement);
        colors = storage.getColors();
        return storage.getData();
    }
}
