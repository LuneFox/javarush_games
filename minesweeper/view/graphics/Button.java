package com.javarush.games.minesweeper.view.graphics;

import com.javarush.engine.cell.Color;

/**
 * Creates buttons with text wrapped in frames.
 */

public class Button implements Drawable {

    public static int pressedTime = -2;           // this timer counts towards 0, then the press animation stops
    public static final int PRESS_DURATION = 5;   // for how long buttons stay pressed
    public static final int POST_PRESS_DELAY = -2;// for how long buttons display unpressed before moving to next screen

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
        GENERAL_BACK(61, 88, 36, 9, "назад"),
        GENERAL_CONFIRM(61, 88, 36, 9, "ясно"),

        MAIN_MENU_START(61, 88, 36, 9, "старт"),
        MAIN_MENU_NEW_RESTART(61, 88, 36, 9, "заново"),
        MAIN_MENU_OPTIONS(61, 64, 36, 9, "опции"),
        MAIN_MENU_ABOUT(61, 76, 36, 9, "об игре"),
        MAIN_MENU_RECORDS(2, 88, 0, 0, "рекорды"),

        GAME_OVER_HIDE(73, 35, 0, 0, "x"),
        GAME_OVER_AGAIN(57, 69, 0, 0, "снова"),
        GAME_OVER_RETURN(15, 69, 0, 0, "меню");

        public final int posX;
        public final int posY;
        public final int width;
        public final int height;
        public final String label;

        ButtonID(int posX, int posY, int width, int height, String label) {
            this.posX = posX;
            this.posY = posY;
            this.width = width;
            this.height = height;
            this.label = label;
        }
    }

    public Button(int posX, int posY, int sizeX, int sizeY, String text) {
        int textLength = Printer.calculateWidth(text);
        this.text = text;
        this.x1 = posX;
        this.y1 = posY;
        this.x2 = (sizeX == 0) ? (posX + textLength + 3) : (posX + sizeX);
        this.y2 = (sizeY == 0) ? (posY + 9) : (posY + sizeY);
        this.textOffset = (sizeX == 0) ? 2 : ((sizeX - textLength) / 2) + 1;
        this.textColor = Color.WHITE;
        setBody(posX, posY, true);
    }

    public void draw() {
        // Is drawn as pressed only when pressed time > 0, otherwise is drawn normal
        if (pressedTime <= 0) isPressed = false;
        int drawX = (isPressed) ? x1 + 1 : x1;
        int drawY = (isPressed) ? y1 + 1 : y1;
        setBody(drawX, drawY, !isPressed);
        this.body.draw();
        Printer.print(text, textColor, drawX + textOffset, drawY);
    }

    public void setBody(int posX, int posY, boolean addShadow) {
        this.body = new Image(VisualElement.MENU_BUTTON, posX, posY) {
            @Override
            public int[][] getMatrixFromStorage(VisualElement visualElement) {
                return ImageCreator.createWindow(x2 - x1, y2 - y1, addShadow, true);
            }
        };
        this.body.colors = new Color[]{
                Color.NONE,
                Theme.BUTTON_BG.getColor(),
                Color.BLACK,
                Theme.BUTTON_BORDER.getColor()};
        body.getMatrixFromStorage(VisualElement.MENU_BUTTON);
    }

    public void replaceText(int width, String label) {
        int textLength = Printer.calculateWidth(label);
        this.text = label;
        this.textOffset = (width == 0) ? 2 : ((width - textLength) / 2) + 1;
    }

    public boolean tryToPress(int x, int y) {
        boolean covers = (x >= x1 && x <= x2 && y >= y1 && y <= y2);
        if (covers) press();
        return covers;
    }

    private void press() {
        isPressed = true;
        pressedTime = PRESS_DURATION;
    }

    public String getText() {
        return text;
    }
}
