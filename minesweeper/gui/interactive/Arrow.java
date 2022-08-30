package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.image.Mirror;
import com.javarush.games.minesweeper.model.InteractiveObject;

public class Arrow extends InteractiveObject {
    private static final int PRESSED_DURATION = 2;
    private final Image arrowImage;
    private Direction direction;
    private int pressedCountDown;

    private enum Direction{
        LEFT, RIGHT
    }

    public static Arrow createLeftArrow(int x, int y) {
        Arrow arrow = new Arrow(x, y);
        arrow.direction = Direction.LEFT;
        return arrow;
    }

    public static Arrow createRightArrow(int x, int y) {
        Arrow arrow = new Arrow(x, y);
        arrow.direction = Direction.RIGHT;
        return arrow;
    }

    private Arrow(int x, int y) {
        super(x, y);
        arrowImage = new Image(ImageType.GUI_ARROW, x, y);
        this.height = arrowImage.height;
        this.width = arrowImage.width;
    }

    public void animate() {
        pressedCountDown = PRESSED_DURATION;
    }

    @Override
    public void draw() {
        if (pressedCountDown > 0) {
            pressedCountDown--;
            setShiftedPosition();
        } else {
            setBasePosition();
        }
        arrowImage.draw(direction == Direction.RIGHT ? Mirror.NONE : Mirror.HORIZONTAL);
    }

    private void setBasePosition() {
        arrowImage.setPosition(x, y);
    }

    private void setShiftedPosition() {
        arrowImage.setPosition(direction == Direction.RIGHT ? (x + 1) : (x - 1), y);
    }

    @Override
    public void onLeftClick() {
        animate();
    }
}
