package com.javarush.games.snake.controller;


import com.javarush.games.snake.SnakeGame;

/** Действия текущего экрана; по умолчанию все пустые. */
public interface ControlStrategy {
    SnakeGame game = SnakeGame.getInstance();

    /** Обрабатывает левый клик; по умолчанию ничего не делает. */
    default void leftClick(int x, int y) {
    }

    /** Обрабатывает правый клик; по умолчанию ничего не делает. */
    default void rightClick(int x, int y) {
    }

    /** Обрабатывает нажатие вверх; по умолчанию ничего не делает. */
    default void pressUp() {
    }

    /** Обрабатывает отпускание вверх; по умолчанию ничего не делает. */
    default void releaseUp() {
    }

    /** Обрабатывает нажатие вниз; по умолчанию ничего не делает. */
    default void pressDown() {
    }

    /** Обрабатывает отпускание вниз; по умолчанию ничего не делает. */
    default void releaseDown() {
    }

    /** Обрабатывает нажатие вправо; по умолчанию ничего не делает. */
    default void pressRight() {
    }

    /** Обрабатывает отпускание вправо; по умолчанию ничего не делает. */
    default void releaseRight() {
    }

    /** Обрабатывает нажатие влево; по умолчанию ничего не делает. */
    default void pressLeft() {
    }

    /** Обрабатывает отпускание влево; по умолчанию ничего не делает. */
    default void releaseLeft() {
    }

    /** Обрабатывает пробел; по умолчанию ничего не делает. */
    default void pressSpace() {
    }

    /** Обрабатывает Enter; по умолчанию ничего не делает. */
    default void pressEnter() {
    }

    /** Обрабатывает Esc; по умолчанию ничего не делает. */
    default void pressEscape() {
    }

    /** Обрабатывает паузу; по умолчанию ничего не делает. */
    default void pressPause() {
    }
}
