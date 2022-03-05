package com.javarush.games.minesweeper.model.options;

import com.javarush.games.minesweeper.model.DrawableObject;
import com.javarush.games.minesweeper.view.graphics.Image;
import com.javarush.games.minesweeper.view.graphics.VisualElement;

public class MenuArrow extends DrawableObject {
    private static final int PRESSED_DURATION = 2;
    private final boolean pointsRight;
    private final Image arrowImage;
    private int pressedCountDown;

    public MenuArrow(int x, int y, boolean pointsRight) {
        super(x, y);
        arrowImage = new Image(VisualElement.MENU_ARROW, x, y);
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
    protected void onTouch() {
        pressedCountDown = PRESSED_DURATION;
    }
}
