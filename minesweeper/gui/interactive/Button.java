package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageCreator;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.view.View;

/**
 * Creates buttons with text wrapped in frames.
 */

public abstract class Button extends InteractiveObject {
    // while pressedTime counts from this value to 0, the button remains pressed
    public static final int PRESS_DURATION = 5;

    // while pressedTime counts from 0 to this value (negative), the button appears unpressed before moving to the next screen
    public static final int POST_PRESS_DELAY = -2;

    // this timer counts down, then at 0 the button is released
    public static int pressedTimeCounter = POST_PRESS_DELAY;

    private static final int DEFAULT_HEIGHT = 9;
    private static final int DEFAULT_MARGIN = 3;
    private static final int DEFAULT_TEXT_OFFSET = 2;

    private final Image unpressedBody;
    private final Image pressedBody;
    private final Color labelColor = Color.WHITE;
    private String labelText;
    private int labelOffset;
    private boolean isPressed;

    public Button(int posX, int posY, String text, View view) {
        super(posX, posY, view);
        this.labelText = text;
        this.width = Printer.calculateWidth(text) + DEFAULT_MARGIN;
        this.height = DEFAULT_HEIGHT;
        this.labelOffset = DEFAULT_TEXT_OFFSET;
        this.unpressedBody = createBody(posX, posY, true);
        this.pressedBody = createBody(posX, posY, false);
    }

    public Button(int posX, int posY, int width, int height, String text, View view) {
        super(posX, posY, view);
        this.labelText = text;
        this.width = width;
        this.height = height;
        this.labelOffset = ((width - Printer.calculateWidth(text)) / 2) + 1;
        this.unpressedBody = createBody(posX, posY, true);
        this.pressedBody = createBody(posX, posY, false);
    }

    private Image createBody(int posX, int posY, boolean addShadow) {
        Image body = new Image(ImageType.GUI_BUTTON, posX, posY) {
            @Override
            public int[][] getMatrixFromStorage(ImageType imageType) {
                return ImageCreator.createFrame(Button.this.width, Button.this.height, addShadow, true);
            }
        };

        body.colors = new Color[]{
                Color.NONE,                     // transparent
                Theme.BUTTON_BG.getColor(),     // background
                Color.BLACK,                    // shadow
                Theme.BUTTON_BORDER.getColor()  // border
        };

        return body;
    }

    public void draw() {
        if (pressedTimeCounter <= 0) {
            release();
        }

        int drawX = (isPressed) ? (x + 1) : x;
        int drawY = (isPressed) ? (y + 1) : y;

        drawStateDependentBody(drawX, drawY);
        printLabel(drawX, drawY);
    }

    private void drawStateDependentBody(int drawX, int drawY) {
        if (isPressed) {
            this.pressedBody.draw(drawX, drawY);
        } else {
            this.unpressedBody.draw(drawX, drawY);
        }
    }

    private void printLabel(int drawX, int drawY) {
        Printer.print(labelText, labelColor, drawX + labelOffset, drawY);
    }

    public void replaceText(int width, String label) {
        int textLength = Printer.calculateWidth(label);
        this.labelText = label;
        this.labelOffset = (width == 0) ? 2 : ((width - textLength) / 2) + 1;
    }

    public void onLeftClick() {
        press();
    }

    private void press() {
        isPressed = true;
        pressedTimeCounter = PRESS_DURATION;
    }

    private void release() {
        isPressed = false;
    }

    public String getLabelText() {
        return labelText;
    }
}
