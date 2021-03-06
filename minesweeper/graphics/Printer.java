package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Global helper that draws any text on the screen.
 */

public class Printer {
    private static Text writer;

    public Printer(MinesweeperGame game) {
        writer = new Text(Bitmap.NONE, game);
        writer.loadAlphabet();
    }

    public void print(String input, Color color, int x, int y, boolean alignRight) {
        writer.write(input, color, x, y, alignRight);
    }

    public static Text getWriter() {
        return writer;
    }
}
