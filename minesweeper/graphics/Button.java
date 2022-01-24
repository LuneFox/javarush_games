package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Creates buttons with text wrapped in frames.
 */

public class Button implements Drawable {

    public static int pressedTime;
    public static final int PRESS_DURATION = 5;

    private final MinesweeperGame game;
    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;
    private final Color textColor;
    private Image body;
    private String text;
    private int textOffset;
    private boolean isPressed;


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
        public int width;
        public int height;
        public String label;

        ButtonID(int posX, int posY, int width, int height, String label) {
            this.posX = posX;
            this.posY = posY;
            this.width = width;
            this.height = height;
            this.label = label;
        }
    }

    public Button(MinesweeperGame game, int posX, int posY, int sizeX, int sizeY, String text) {
        int textLength = Printer.calculateWidth(text);
        this.text = text;
        this.game = game;
        this.x1 = posX;
        this.y1 = posY;
        this.x2 = (sizeX == 0) ? (posX + textLength + 3) : (posX + sizeX);
        this.y2 = (sizeY == 0) ? (posY + 9) : (posY + sizeY);
        this.textOffset = (sizeX == 0) ? 2 : ((sizeX - textLength) / 2) + 1;
        this.textColor = Color.WHITE;
        setBody(posX, posY, true);
    }

    public void draw() {
        if (pressedTime == 0) isPressed = false;
        int drawX = (isPressed) ? x1 + 1 : x1;
        int drawY = (isPressed) ? y1 + 1 : y1;
        setBody(drawX, drawY, !isPressed);
        this.body.draw();
        Printer.print(text, textColor, drawX + textOffset, drawY);
    }

    public void setBody(int posX, int posY, boolean addShadow) {
        this.body = new Image(VisualElement.MENU_BUTTON, posX, posY) {
            @Override
            public int[][] getMatrix(VisualElement visualElement) {
                return ImageCreator.createWindow(x2 - x1, y2 - y1, addShadow, true);
            }
        };
        this.body.colors = new Color[]{
                Color.NONE,
                Theme.BUTTON_BG.getColor(),
                Color.BLACK,
                Theme.BUTTON_BORDER.getColor()};
        body.getMatrix(VisualElement.MENU_BUTTON);
    }

    public void replaceText(int width, String label) {
        int textLength = Printer.calculateWidth(label);
        this.text = label;
        this.textOffset = (width == 0) ? 2 : ((width - textLength) / 2) + 1;
    }

    public boolean covers(int x, int y) {
        boolean covers = (x >= x1 && x <= x2 && y >= y1 && y <= y2);
        if (covers) {
            isPressed = true;
            pressedTime = PRESS_DURATION;
        }
        return covers;
    }

    public String getText() {
        return text;
    }
}
