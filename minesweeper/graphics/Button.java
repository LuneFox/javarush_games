package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Creates buttons with text wrapped in frames.
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
    private final int textOffset;

    public enum ButtonID {
        ABOUT(61, 76, 36, 9, "об игре"),
        AGAIN(57, 69, 0, 0, "снова"),
        BACK(61, 88, 36, 9, "назад"),
        CLOSE(73, 35, 0, 0, "x"),
        CONFIRM(61, 88, 36, 9, "ясно"),
        FORWARD(3, 88, 36, 9, "далее"),
        NEW_GAME(61, 88, 36, 9, "заново"),
        RECORDS(2, 88, 0, 0, "рекорды"),
        RETURN(15, 69, 0, 0, "меню"),
        START(61, 88, 36, 9, "старт"),
        OPTIONS(61, 64, 36, 9, "опции");

        public int posX;
        public int posY;
        public int sizeX;
        public int sizeY;
        public String label;

        ButtonID(int posX, int posY, int sizeX, int sizeY, String label) {
            this.posX = posX;
            this.posY = posY;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.label = label;
        }
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
        this.body.colors = new Color[]{
                Color.NONE,
                Theme.current.getColor(ThemeElement.BUTTON_BG),
                Color.BLACK,
                Theme.current.getColor(ThemeElement.BUTTON_BORDER)};
        body.assignBitmap(Bitmap.MENU_BUTTON);
    }

    public void draw() {
        this.body.draw();
        game.print(text, textColor, x1 + textOffset, y1);
    }

    public boolean covers(int x, int y) {
        return (x >= x1 && x <= x2 && y >= y1 && y <= y2);
    }
}
