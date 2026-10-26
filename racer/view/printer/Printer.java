package com.javarush.games.racer.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.view.Display;

/**
 * Renders text with the hand-made pixel font into the game's {@link Display}.
 * Everything is static, and the outline state lives in a static field: a {@code '<'} or {@code '>'}
 * character toggles outlining instead of being drawn, so the effect leaks into later calls. Input is
 * lowercased before glyph lookup, lines are split on {@code \n}, and centering is computed from the
 * width of the whole string.
 */
public class Printer {
    private static final SymbolCache CACHE = new SymbolCache();
    private static final Color STROKE_COLOR = Color.SADDLEBROWN;
    private static final int CHAR_SPACING = 1;
    private static boolean isStrokeEnabled;

    /**
     * Prints white, left-aligned text with its top-left corner at the given cell.
     *
     * @param text text to draw; {@code <} and {@code >} are outline toggles, not glyphs
     * @param x column of the left edge
     * @param y row of the first line
     */
    public static void print(String text, int x, int y) {
        print(text, Color.WHITE, x, y, TextAlign.LEFT);
    }

    /**
     * Prints left-aligned text in the given colour with its top-left corner at the given cell.
     *
     * @param text text to draw; {@code <} and {@code >} are outline toggles, not glyphs
     * @param color fill colour
     * @param x column of the left edge
     * @param y row of the first line
     */
    public static void print(String text, Color color, int x, int y) {
        print(text, color, x, y, TextAlign.LEFT);
    }

    /**
     * Prints text at the requested position and alignment, splitting it into lines on {@code \n}.
     * CENTER ignores the given x and uses one left edge computed from the width of the whole string
     * (line breaks included), so lines of different lengths are not individually centered; RIGHT
     * rewinds the caret for every line instead. Characters are lowercased before lookup, and
     * {@code <}/{@code >} toggle outline mode.
     *
     * @param text text to draw, possibly containing line breaks and outline markup
     * @param color fill colour
     * @param drawX requested left edge, ignored when the alignment is CENTER
     * @param drawY row of the first line
     * @param align horizontal alignment of each line
     */
    public static void print(String text, Color color, int drawX, int drawY, TextAlign align) {
        drawX = align != TextAlign.CENTER ? drawX : (Display.SIZE / 2) - (calculateWidth(text) / 2);

        PrinterCaret caret = new PrinterCaret(drawX, drawY, align);
        String[] lines = text.split("\n");

        for (String line : lines) {

            caret.gotoStartPosition(line);
            char[] sequence = line.toLowerCase().toCharArray();

            for (char c : sequence) {
                drawSymbol(c, color, caret.x, caret.y);
                caret.gotoNextSymbol(c);
            }

            caret.gotoNewLine();
        }
    }

    /**
     * Measures text in cells by summing each glyph width plus one cell of character spacing.
     * Markup characters contribute nothing, but the last character still includes its trailing
     * spacing, and a newline contributes the newline glyph's width. A character missing from the
     * font yields a null image and therefore a {@link NullPointerException}.
     *
     * @param s text to measure; mixed case is accepted
     * @return approximate width in cells, including inter-character spacing
     */
    static int calculateWidth(String s) {
        int result = 0;
        SymbolImage image;
        char[] chars = s.toLowerCase().toCharArray();

        for (char c : chars) {
            if (charIsStrokeMarkup(c)) continue;
            image = CACHE.get(c);
            result += image.getWidth() + CHAR_SPACING;
        }

        return result;
    }

    /**
     * Draws one glyph, or flips the static outline flag when the character is markup. The outline is
     * stamped first, then the fill colour is set; the colour must be changed for every character
     * because {@link SymbolImage} shares it across all glyph instances.
     *
     * @param c lowercased character to draw or outline markup to toggle
     * @param color fill colour
     * @param x column of the glyph's top-left cell
     * @param y row of the glyph's top-left cell
     */
    private static void drawSymbol(char c, Color color, int x, int y) {
        if (charIsStrokeMarkup(c)) {
            isStrokeEnabled = !isStrokeEnabled;
            return;
        }

        SymbolImage image = CACHE.get(c);
        if (isStrokeEnabled) stroke(x, y, image);
        image.changeColor(color);
        image.draw(x, y);
    }

    /**
     * Tells whether a character is outline markup rather than a drawable glyph.
     *
     * @param c character to test
     * @return true for {@code <} and {@code >}
     */
    private static boolean charIsStrokeMarkup(char c) {
        return (c == '<') || (c == '>');
    }

    /**
     * Stamps the glyph in the stroke colour at the four orthogonal neighbours of its intended
     * position, producing a one-cell halo around the later fill. The shared colour is left changed,
     * so the caller must set the fill colour afterwards (and does).
     *
     * @param x column of the glyph's top-left cell
     * @param y row of the glyph's top-left cell
     * @param image glyph to stamp four times
     */
    private static void stroke(int x, int y, SymbolImage image) {
        image.changeColor(STROKE_COLOR);
        image.draw(x - 1, y);
        image.draw(x + 1, y);
        image.draw(x, y - 1);
        image.draw(x, y + 1);
    }
}
