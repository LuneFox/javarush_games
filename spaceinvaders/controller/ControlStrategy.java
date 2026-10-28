package com.javarush.games.spaceinvaders.controller;

/** Реакция на игровые клавиши; каждая реализуется по необходимости. */
public interface ControlStrategy {
    /** Нажата стрелка вверх. */
    default void upPressed() {
    }

    /** Отпущена стрелка вверх. */
    default void upReleased() {
    }

    /** Нажата стрелка вниз. */
    default void downPressed() {
    }

    /** Отпущена стрелка вниз. */
    default void downReleased() {
    }

    /** Нажата стрелка влево. */
    default void leftPressed() {
    }

    /** Отпущена стрелка влево. */
    default void leftReleased() {
    }

    /** Нажата стрелка вправо. */
    default void rightPressed() {
    }

    /** Отпущена стрелка вправо. */
    default void rightReleased() {
    }

    /** Нажат пробел. */
    default void spacePressed() {
    }

    /** Отпущен пробел. */
    default void spaceReleased() {
    }

    /** Нажата пауза. */
    default void pausePressed() {
    }

    /** Отпущена пауза. */
    default void pauseReleased() {
    }
}
