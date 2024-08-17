package com.javarush.games.ticktacktoe.controller;

/**
 * Интерфейс для управления игрой.
 * По умолчанию нажатия ничего не делают, но в реализации можно переназначить.
 *
 * @author LuneFox
 */
public interface ControlStrategy {
    /**
     * Клик мышью
     *
     * @param x     координата x
     * @param y     координата y
     * @param click левый или правый клик
     */
    default void click(int x, int y, Click click) {
    }
    /** Нажать вверх */
    default void pressUp() {
    }

    /** Отпустить вверх */
    default void releaseUp() {
    }

    /** Нажать вниз */
    default void pressDown() {
    }

    /** Отпустить вниз */
    default void releaseDown() {
    }

    /** Нажать вправо */
    default void pressRight() {
    }

    /** Отпустить вправо */
    default void releaseRight() {
    }

    /** Нажать влево */
    default void pressLeft() {
    }

    /** Отпустить влево */
    default void releaseLeft() {
    }

    /** Нажать пробел */
    default void pressSpace() {
    }

    /** Отпустить пробел */
    default void releaseSpace() {
    }

    /** Нажать Enter */
    default void pressEnter() {
    }

    /** Отпустить Enter */
    default void releaseEnter() {
    }
}