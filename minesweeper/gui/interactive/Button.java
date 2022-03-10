package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageCreator;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.DrawableObject;

/**
 * Creates buttons with text wrapped in frames.
 */

public class Button extends DrawableObject {

    public static int pressedTime = -2;           // this timer counts towards 0, then the press animation stops
    public static final int PRESS_DURATION = 5;   // for how long buttons stay pressed
    public static final int POST_PRESS_DELAY = -2;// for how long buttons display unpressed before moving to next screen

    private final Color textColor;
    private Image bodyUp;
    private Image bodyDown;
    private String text;
    private int textOffset;
    private boolean isPressed;

    public Button(int posX, int posY, int sizeX, int sizeY, String text) { // size 0 = auto size;
        super(posX, posY);
        int textLength = Printer.calculateWidth(text);
        this.text = text;
        this.width = (sizeX == 0) ? (textLength + 3) : sizeX;
        this.height = (sizeY == 0) ? 9 : sizeY;
        this.textOffset = (sizeX == 0) ? 2 : ((sizeX - textLength) / 2) + 1;
        this.textColor = Color.WHITE;
        createBodies(posX, posY);
    }

    public void draw() {
        // Is drawn as pressed only when pressed time > 0, otherwise is drawn normal
        if (pressedTime <= 0) isPressed = false;
        int drawX = (isPressed) ? x + 1 : x;
        int drawY = (isPressed) ? y + 1 : y;
        if (isPressed) {
            this.bodyDown.draw(drawX, drawY);
        } else {
            this.bodyUp.draw(drawX, drawY);
        }
        Printer.print(text, textColor, drawX + textOffset, drawY);
    }

    private void createBodies(int posX, int posY) {
        this.bodyUp = new Image(ImageType.MENU_BUTTON, posX, posY) {
            @Override
            public int[][] getMatrixFromStorage(ImageType imageType) {
                return ImageCreator.createWindow(Button.this.width, Button.this.height, true, true);
            }
        };

        this.bodyDown = new Image(ImageType.MENU_BUTTON, posX, posY) {
            @Override
            public int[][] getMatrixFromStorage(ImageType imageType) {
                return ImageCreator.createWindow(Button.this.width, Button.this.height, false, true);
            }
        };

        this.bodyUp.colors = this.bodyDown.colors = new Color[]{
                Color.NONE,
                Theme.BUTTON_BG.getColor(),
                Color.BLACK,
                Theme.BUTTON_BORDER.getColor()};
    }

    public void replaceText(int width, String label) {
        int textLength = Printer.calculateWidth(label);
        this.text = label;
        this.textOffset = (width == 0) ? 2 : ((width - textLength) / 2) + 1;
    }

    protected void onLeftTouch() {
        isPressed = true;
        pressedTime = PRESS_DURATION;
    }

    public String getText() {
        return text;
    }
}
