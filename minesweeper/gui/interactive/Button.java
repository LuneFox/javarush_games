package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Effect;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageCreator;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.view.View;

public abstract class Button extends InteractiveObject {
    private static final int PRESS_ANIMATION_START_FRAME = 0;
    private static final int PRESS_ANIMATION_RELEASE_FRAME = 5;
    private static final int PRESS_ANIMATION_FINISH_FRAME = 7;
    private static final int DEFAULT_HEIGHT = 9;
    private static final int DEFAULT_MARGIN = 3;
    private static final int DEFAULT_OFFSET = 2;
    private static int pressAnimationCurrentFrame = PRESS_ANIMATION_FINISH_FRAME + 1;

    private final Image unpressedBody;
    private final Image pressedBody;
    private final Color labelColor = Color.WHITE;
    private String labelText;
    private int labelOffset;
    private boolean isPressed;

    public Button(int posX, int posY, String text, View view) {
        super(posX, posY);
        this.labelText = text;
        this.width = Printer.calculateWidth(text) + DEFAULT_MARGIN;
        this.height = DEFAULT_HEIGHT;
        this.labelOffset = DEFAULT_OFFSET;
        this.unpressedBody = createBody(posX, posY, Effect.SHADOW, Effect.STROKE);
        this.pressedBody = createBody(posX, posY, Effect.STROKE);
        this.linkView(view);
    }

    public Button(int posX, int posY, int width, int height, String text, View view) {
        super(posX, posY);
        this.labelText = text;
        this.width = width;
        this.height = height;
        this.labelOffset = ((width - Printer.calculateWidth(text)) / 2) + 1;
        this.unpressedBody = createBody(posX, posY, Effect.SHADOW, Effect.STROKE);
        this.pressedBody = createBody(posX, posY, Effect.STROKE);
        this.linkView(view);
    }

    private Image createBody(int posX, int posY, Effect... effects) {

        Image body = new Image(ImageType.GUI_BUTTON, posX, posY) {
            @Override
            public int[][] getMatrixFromStorage(ImageType imageType) {
                return ImageCreator.createFrame(Button.this.width, Button.this.height, effects);
            }
        };

        body.setColors(new Color[]{Color.NONE, Theme.BUTTON_BG.getColor(), Color.BLACK, Theme.BUTTON_BORDER.getColor()});
        return body;
    }

    public void draw() {
        if (pressAnimationCurrentFrame >= PRESS_ANIMATION_RELEASE_FRAME) {
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

    public void onLeftClick() {
        press();
    }

    public void replaceText(int width, String label) {
        int textLength = Printer.calculateWidth(label);
        this.labelText = label;
        this.labelOffset = (width == 0) ? DEFAULT_OFFSET : ((width - textLength) / 2) + 1;
    }

    private void press() {
        isPressed = true;
        pressAnimationCurrentFrame = PRESS_ANIMATION_START_FRAME;
    }

    private void release() {
        isPressed = false;
    }

    public static boolean isAnimationFinished() {
        return pressAnimationCurrentFrame > PRESS_ANIMATION_FINISH_FRAME;
    }

    public static void continueAnimation() {
        if (pressAnimationCurrentFrame <= PRESS_ANIMATION_FINISH_FRAME) {
            pressAnimationCurrentFrame++;
        }
    }

    public String getLabelText() {
        return labelText;
    }
}
