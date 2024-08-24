package com.javarush.games.ticktacktoe.controller;

/**
 * Клик мышкой
 *
 * @author LuneFox
 */
public enum Click {

    LEFT,
    RIGHT;

    /**
     * Утилитный метод.
     * Превращение точной координаты клика в координату игрового поля.
     *
     * @param mouseClickPoint координата клика мышкой
     * @return координата на игровом поле
     */
    public static int toBoard(int mouseClickPoint) {
        return (mouseClickPoint - 10) / 10;
    }
}


