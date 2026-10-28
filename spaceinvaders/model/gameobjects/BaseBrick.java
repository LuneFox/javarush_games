package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.games.spaceinvaders.view.shapes.BaseBrickShape;

/** Разрушаемый кирпич базы с орлом. */
public class BaseBrick extends GameObject {
    /** Смещение кирпича по X относительно базы. */
    private final int offsetX;
    /** Смещение кирпича по Y относительно базы. */
    private final int offsetY;

    /** Создаёт кирпич базы со смещением от её угла. */
    public BaseBrick(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        setStaticView(BaseBrickShape.BRICK);
    }

    /** Ставит кирпич относительно текущего положения базы. */
    public void updatePosition(double baseX, double baseY) {
        setPosition(baseX + offsetX, baseY + offsetY);
    }
}
