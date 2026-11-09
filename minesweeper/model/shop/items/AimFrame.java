package com.javarush.games.minesweeper.model.shop.items;

import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Drawable;
import com.javarush.games.minesweeper.model.board.field.Cell;

public class AimFrame implements Drawable {
    private int x;
    private int y;
    private final int padding;
    private final Image image;
    /**
     * Координата X клетки, на которую наведена рамка.
     */
    private int focusX;
    /**
     * Координата Y клетки, на которую наведена рамка.
     */
    private int focusY;

    public AimFrame(Cell initialFocusCell, int padding, ImageType imageType) {
        this.focusX = initialFocusCell.x;
        this.focusY = initialFocusCell.y;
        this.padding = padding;
        this.image = Image.cache.get(imageType);
        setPosition(getDestinationX(), getDestinationY());
    }

    @Override
    public void draw() {
        int moveSpeed = 5;
        if (x < getDestinationX()) x += moveSpeed;
        if (x > getDestinationX()) x -= moveSpeed;
        if (y < getDestinationY()) y += moveSpeed;
        if (y > getDestinationY()) y -= moveSpeed;
        image.draw(x, y);
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int getDestinationX() {
        return focusX * 10 + padding;
    }

    private int getDestinationY() {
        return focusY * 10 + padding;
    }

    public boolean isNotFocusedOnCell(Cell cell) {
        return focusX != cell.x || focusY != cell.y;
    }

    public void focusOnCell(Cell cell) {
        this.focusX = cell.x;
        this.focusY = cell.y;
    }
}
