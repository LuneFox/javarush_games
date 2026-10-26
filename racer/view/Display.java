package com.javarush.games.racer.view;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

/**
 * Off-screen 100x100 buffer of {@link Color} values that stands in for the engine's cell grid.
 * Game objects write individual cells through {@link #drawPixel}; {@link #draw()} flushes the whole
 * buffer to the engine once per frame. Cell coordinates use x growing right and y growing down,
 * and {@link #SIZE} is hard-coded, duplicating the dimensions of {@code RacerGame}.
 */
public class Display {
    /** Width and height of the screen in cells; mirrors {@code RacerGame.WIDTH} and {@code HEIGHT}. */
    public static final int SIZE = 100;
    private final Game game;
    private final Pixel[][] matrix;

    /**
     * Binds the buffer to the game that will receive the flushed cells and allocates a fresh
     * all-transparent pixel matrix.
     *
     * @param game engine game instance used by {@link #draw()}
     */
    public Display(Game game) {
        this.game = game;
        this.matrix = createPixelMatrix();
    }

    /**
     * Tells whether a cell coordinate lies on the 100x100 screen.
     *
     * @param x cell column to test
     * @param y cell row to test
     * @return true when both coordinates are in the range 0..99
     */
    public static boolean isWithinScreen(int x, int y) {
        return (x >= 0) && (y >= 0) && (x < SIZE) && (y < SIZE);
    }

    /**
     * Allocates a fresh {@code SIZE x SIZE} pixel matrix in which the first index is y (row) and
     * the second is x (column), with every cell starting transparent.
     *
     * @return a fully allocated matrix of transparent pixels
     */
    private Pixel[][] createPixelMatrix() {
        Pixel[][] result = new Pixel[SIZE][SIZE];
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                result[y][x] = new Pixel();
            }
        }
        return result;
    }

    /**
     * Pushes all 10,000 buffered cells to the engine with {@code game.setCellColor}, repainting the
     * entire screen every frame rather than only the cells that changed.
     */
    public void draw() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                game.setCellColor(x, y, matrix[y][x].cellColor);
            }
        }
    }

    /**
     * Writes one cell of the buffer, silently ignoring coordinates outside the screen and
     * {@link Color#NONE}, which is treated as transparent. The buffer keeps its previous content
     * when the write is rejected.
     *
     * @param x cell column, 0 at the left edge
     * @param y cell row, 0 at the top edge
     * @param color colour to store in the cell, or {@link Color#NONE} to leave it unchanged
     */
    public void drawPixel(int x, int y, Color color) {
        if (isWithinScreen(x, y) && isNotTransparentColor(color)) {
            this.matrix[y][x].cellColor = color;
        }
    }

    /**
     * Checks that a colour should actually be stored in a cell.
     *
     * @param color colour to test
     * @return true unless the colour is {@link Color#NONE}
     */
    private boolean isNotTransparentColor(Color color) {
        return color != Color.NONE;
    }

    /**
     * One cell of the off-screen buffer. The matrix layout is {@code matrix[y][x]}, and a new
     * pixel starts transparent ({@link Color#NONE}).
     */
    public static class Pixel {

        Color cellColor;

        /**
         * Creates a transparent pixel.
         */
        Pixel() {
            this.cellColor = Color.NONE;
        }

    }
}
