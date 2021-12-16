package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;

/**
 * Global class that uses a text object to write anything on the screen.
 */

public class Printer {
    private static Text writer;

    public Printer() {
        writer = new Text(VisualElement.NONE);
        writer.loadAlphabet();
    }

    public void print(String input, Color color, int x, int y, boolean alignRight) {
        writer.write(input, color, x, y, alignRight);
    }

    public static Text getWriter() {
        return writer;
    }
}
