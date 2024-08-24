package com.javarush.games.ticktacktoe.model.gameobjects;

/**
 * Сторона игрока
 *
 * @author LuneFox
 */
public enum Side {

    BLACK, WHITE;

    /**
     * Выдаёт противоположный цвет
     *
     * @param side входной цвет
     * @return противоположный цвет
     */
    public static Side flip(Side side) {
        return side == WHITE ? BLACK : WHITE;
    }
}
