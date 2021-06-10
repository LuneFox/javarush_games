package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Allows to draw buttons with text wrapped in frames.
 */

public class Button {
    private final MinesweeperGame game;
    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;
    private final Image body;
    private final Color textColor;
    private final String text;
    private int textOffset;

    public enum ButtonID {
        OPTIONS, ABOUT, START, CONFIRM, BACK, FORWARD, AGAIN, RETURN, CLOSE, RECORDS
    }

    public Button(MinesweeperGame game, int posX, int posY, int sizeX, int sizeY, String text) {
        int textLength = Printer.getWriter().calculateLengthInPixels(text);
        this.text = text;
        this.game = game;
        this.x1 = posX;
        this.y1 = posY;
        this.x2 = (sizeX == 0) ? (posX + textLength + 3) : (posX + sizeX);
        this.y2 = (sizeY == 0) ? (posY + 9) : (posY + sizeY);
        this.textOffset = (sizeX == 0) ? 2 : ((sizeX - textLength) / 2) + 1;
        this.textColor = Color.WHITE;
        this.body = new Image(Bitmap.MENU_BUTTON, game, posX, posY) {
            @Override
            protected int[][] assignBitmap(Bitmap bitmap) {
                return createWindowBitmap(x2 - x1, y2 - y1, true, true);
            }
        };
        this.body.colors = new Color[]{Color.NONE, Color.DARKRED, Color.BLACK, Color.SALMON};
        body.assignBitmap(Bitmap.MENU_BUTTON);
    }

    public Button(MinesweeperGame game, int posX, int posY, String text) {
        this(game, posX, posY, 0, 0, text); // auto size
    }

    public void draw() {
        this.body.draw();
        game.print(text, textColor, x1 + textOffset, y1, false);
    }

    public boolean isUnder(int x, int y) {
        return (x >= x1 && x <= x2 && y >= y1 && y <= y2);
    }
}
