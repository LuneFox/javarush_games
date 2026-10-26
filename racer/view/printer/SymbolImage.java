package com.javarush.games.racer.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.view.Display;

/**
 * One drawable glyph: a reference to a {@link Symbol}'s bitmap plus a drawing position.
 * The colour table is static, so every image shares one tint (the target {@link Display} is static
 * too); {@link #changeColor} mutates that shared entry and callers must set the colour before each
 * draw, which is why the {@link Printer} changes it per character.
 */
public class SymbolImage {
    private static Display display;
    private static final Color[] colors = new Color[]{Color.NONE, Color.WHITE};
    private int x;
    private int y;
    private final int[][] matrix;

    /**
     * Registers the off-screen display that all glyph images draw into; called once during game
     * initialisation.
     *
     * @param display target buffer
     */
    public static void setDisplay(Display display) {
        SymbolImage.display = display;
    }

    /**
     * Creates an image for a glyph, keeping a reference to (not a copy of) its bitmap.
     *
     * @param type symbol providing the bitmap
     */
    SymbolImage(Symbol type) {
        this.matrix = type.getMatrix();
    }

    /**
     * Draws the glyph with its top-left corner at the given cell, overwriting the non-transparent
     * pixels of the display buffer.
     *
     * @param x column of the top-left corner
     * @param y row of the top-left corner
     */
    void draw(int x, int y) {
        setPosition(x, y);
        draw();
    }

    /**
     * Stores the drawing origin used by {@link #draw()}.
     *
     * @param x column of the top-left corner
     * @param y row of the top-left corner
     */
    private void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Rasterises the whole bitmap at the stored position, skipping transparent pixels. Bitmap values
     * index the shared colour table directly, so only the font's values 0 and 1 are supported.
     */
    private void draw() {
        for (int j = 0; j < getHeight(); j++) {
            for (int i = 0; i < getWidth(); i++) {

                int pixel = matrix[j][i];
                if (isPixelTransparent(pixel)) continue;

                int drawX = x + i;
                int drawY = y + j;

                display.drawPixel(drawX, drawY, colors[pixel]);
            }
        }
    }

    /**
     * Returns the glyph height in cells.
     *
     * @return number of bitmap rows
     */
    int getHeight(){
        return matrix.length;
    }

    /**
     * Returns the glyph width in cells, taken from the first bitmap row.
     *
     * @return number of bitmap columns
     */
    int getWidth(){
        return matrix[0].length;
    }

    /**
     * Tells whether a bitmap value should leave the display untouched.
     *
     * @param pixel bitmap value to test
     * @return true for 0, or for a colour-table entry equal to {@link Color#NONE}
     */
    private boolean isPixelTransparent(int pixel) {
        final int TRANSPARENT = 0;
        return (pixel == TRANSPARENT || colors[pixel] == Color.NONE);
    }

    /**
     * Changes the fill colour used by every glyph image, because the colour table is static. The
     * try/catch is effectively dead code, since the shared table always has index 1; it would only
     * print an out-of-bounds error to stderr instead of propagating it.
     *
     * @param color new fill colour for index 1 of the shared table
     */
    void changeColor(Color color) {
        try {
            colors[1] = color;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Could not change color! " + e.getMessage());
        }
    }
}
