package com.javarush.games.minesweeper.view.graphics;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.DrawableObject;

/**
 * An image that is drawn using colors and the number matrix. Can be mirrored, recolored etc.
 */

public class Image extends DrawableObject {
    public static final int CENTER = -1;
    protected MinesweeperGame game = MinesweeperGame.getInstance();
    public int[][] matrix;  // matrix of color numbers
    public Color[] colors;  // an array to match colors and numbers

    public enum Mirror {
        HORIZONTAL, VERTICAL, NONE
    }

    public Image(VisualElement visualElement) { // constructor without setting position (for loading images in memory)
        this(visualElement, 0, 0);
    }

    public Image(VisualElement visualElement, int x, int y) { // constructor with setting position at once
        super(x, y);
        this.matrix = getMatrixFromStorage(visualElement);
        this.height = matrix.length;
        this.width = matrix[0].length;
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
                                x + (matrix[0].length - 1 - innerX),
                                y + innerY,
                                colors[matrix[innerY][innerX]]
                        );
                        break;
                    case VERTICAL:
                        game.display.setCellColor(
                                x + innerX,
                                y + (matrix.length - 1 - innerY),
                                colors[matrix[innerY][innerX]]
                        );
                        break;
                    case NONE:
                    default:
                        game.display.setCellColor(
                                x + innerX,
                                y + innerY,
                                colors[matrix[innerY][innerX]]
                        );
                        break;
                }
            }
        }
    }

    public final void draw(int x, int y, Mirror mirror) {
        setPosition(x, y);
        draw(mirror);
    }

    public final void draw(int x, int y) {
        draw(x, y, Mirror.NONE);
    }

    public void draw() {
        draw(Mirror.NONE);
    }

    public final void setPosition(int drawX, int drawY) { // negative value = middle
        this.x = (drawX < 0) ? (50 - matrix[0].length / 2) : drawX;
        this.y = (drawY < 0) ? (50 - matrix.length / 2) : drawY;
    }

    public final void replaceColor(Color color, int number) {
        try {
            this.colors[number] = color;
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    public int[][] getMatrixFromStorage(VisualElement visualElement) {
        ImageStorage storage = new ImageStorage(visualElement);
        colors = storage.getColors();
        return storage.getData();
    }
}
