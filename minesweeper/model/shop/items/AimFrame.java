package com.javarush.games.minesweeper.model.shop.items;

import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Drawable;
import com.javarush.games.minesweeper.model.board.field.Cell;

/** Рамка прицела для сканера и бомбы. */
public class AimFrame implements Drawable {
    /** Координата X. */
    private int x;
    /** Координата Y. */
    private int y;
    /** Отступ рамки. */
    private final int padding;
    /** Изображение. */
    private final Image image;
    /**
     * Координата X клетки, на которую наведена рамка.
     */
    private int focusX;
    /**
     * Координата Y клетки, на которую наведена рамка.
     */
    private int focusY;

    /** Создаёт экземпляр AimFrame. */
    public AimFrame(Cell initialFocusCell, int padding, ImageType imageType) {
        this.focusX = initialFocusCell.x;
        this.focusY = initialFocusCell.y;
        this.padding = padding;
        this.image = Image.cache.get(imageType);
        setPosition(getDestinationX(), getDestinationY());
    }

    /** Рисует объект на экране. */
    @Override
    public void draw() {
        int moveSpeed = 5;
        if (x < getDestinationX()) x += moveSpeed;
        if (x > getDestinationX()) x -= moveSpeed;
        if (y < getDestinationY()) y += moveSpeed;
        if (y > getDestinationY()) y -= moveSpeed;
        image.draw(x, y);
    }

    /** Задаёт позицию. */
    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Возвращает координату X цели. */
    private int getDestinationX() {
        return focusX * Cell.PIXEL_SIZE + padding;
    }

    /** Возвращает координату Y цели. */
    private int getDestinationY() {
        return focusY * Cell.PIXEL_SIZE + padding;
    }

    /** Сообщает, наведена ли рамка на другую клетку. */
    public boolean isNotFocusedOnCell(Cell cell) {
        return focusX != cell.x || focusY != cell.y;
    }

    /** Наводит рамку на клетку. */
    public void focusOnCell(Cell cell) {
        this.focusX = cell.x;
        this.focusY = cell.y;
    }
}
