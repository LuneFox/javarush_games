package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;

public class Arrow extends InteractiveObject {
    private static final int PRESSED_DURATION = 2;
    private final boolean pointsRight;
    private final Image arrowImage;
    private int pressedCountDown;

    public Arrow(int x, int y, boolean pointsRight) {
        super(x, y);
        arrowImage = new Image(ImageType.GUI_ARROW, x, y);
        this.height = arrowImage.height;
        this.width = arrowImage.width;
        this.pointsRight = pointsRight;
    }

    @Override
    public void draw() {
        if (pressedCountDown <= 0) {
            arrowImage.setPosition(x, y);
        } else {
            // Draws itself pressed until countdown has finished
            pressedCountDown--;
            arrowImage.setPosition(pointsRight ? (x + 1) : (x - 1), y);
        }
        arrowImage.draw(pointsRight ? Image.Mirror.NONE : Image.Mirror.HORIZONTAL);
    }

    @Override
    public void onLeftClick() {
        pressedCountDown = PRESSED_DURATION;
    }
}
