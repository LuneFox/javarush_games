package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.games.minesweeper.gui.image.ImageID;
import com.javarush.games.minesweeper.model.DrawableObject;
import com.javarush.games.minesweeper.gui.image.Image;

public class Arrow extends DrawableObject {
    private static final int PRESSED_DURATION = 2;
    private final boolean pointsRight;
    private final Image arrowImage;
    private int pressedCountDown;

    public Arrow(int x, int y, boolean pointsRight) {
        super(x, y);
        arrowImage = new Image(ImageID.MENU_ARROW, x, y);
        this.height = arrowImage.height;
        this.width = arrowImage.width;
        this.pointsRight = pointsRight;
    }

    @Override
    public void draw() {
        // Draws itself pressed until countdown has finished
        if (pressedCountDown > 0) {
            if (pointsRight) {
                arrowImage.draw(x + 1, y, Image.Mirror.NONE);
            } else {
                arrowImage.draw(x - 1, y, Image.Mirror.HORIZONTAL);
            }
            pressedCountDown--;
        } else {
            if (pointsRight) {
                arrowImage.draw(x, y, Image.Mirror.NONE);
            } else {
                arrowImage.draw(x, y, Image.Mirror.HORIZONTAL);
            }
        }
    }

    @Override
    protected void onLeftTouch() {
        pressedCountDown = PRESSED_DURATION;
    }
}
