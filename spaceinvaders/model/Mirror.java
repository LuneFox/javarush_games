package com.javarush.games.spaceinvaders.model;

/** Отражение спрайта по горизонтали. */
public enum Mirror {
    HORIZONTAL, NONE;

    /** Возвращает отражение для взгляда в заданную сторону. */
    public static Mirror of(Direction facing) {
        return (facing == Direction.RIGHT) ? NONE : HORIZONTAL;
    }
}
