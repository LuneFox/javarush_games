package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.image.Mirror;
import com.javarush.games.minesweeper.model.InteractiveObject;

/** Анимированная стрелка-кнопка. */
public class Arrow extends InteractiveObject {
    /** Длительность нажатия. */
    private static final int PRESSED_DURATION = 2;
    /** Изображение стрелки. */
    private final Image arrowImage;
    /** Направление. */
    private Direction direction;
    /** Оставшееся время нажатия. */
    private int pressedCountDown;

    /** Направление стрелки. */
    private enum Direction {
        /** Влево. */
        LEFT,
        /** Вправо. */
        RIGHT
    }

    /** Создаёт левую стрелку. */
    public static Arrow createLeftArrow(int x, int y) {
        Arrow arrow = new Arrow(x, y);
        arrow.direction = Direction.LEFT;
        return arrow;
    }

    /** Создаёт правую стрелку. */
    public static Arrow createRightArrow(int x, int y) {
        Arrow arrow = new Arrow(x, y);
        arrow.direction = Direction.RIGHT;
        return arrow;
    }

    /** Создаёт экземпляр Arrow. */
    private Arrow(int x, int y) {
        super(x, y);
        arrowImage = new Image(ImageType.GUI_ARROW, x, y);
        this.height = arrowImage.height;
        this.width = arrowImage.width;
    }

    /** Запускает анимацию. */
    public void animate() {
        pressedCountDown = PRESSED_DURATION;
    }

    /** Рисует объект на экране. */
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

    /** Задаёт базовую позицию. */
    private void setBasePosition() {
        arrowImage.setPosition(x, y);
    }

    /** Задаёт сдвинутую позицию. */
    private void setShiftedPosition() {
        arrowImage.setPosition(direction == Direction.RIGHT ? (x + 1) : (x - 1), y);
    }

    /** Обрабатывает левый клик. */
    @Override
    public void onLeftClick() {
        animate();
    }
}
