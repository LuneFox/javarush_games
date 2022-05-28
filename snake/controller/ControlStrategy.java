package com.javarush.games.snake.controller;


import com.javarush.games.snake.SnakeGame;

public interface ControlStrategy {
    SnakeGame game = SnakeGame.getInstance();

    default void leftClick(int x, int y) {
    }

    default void rightClick(int x, int y) {
    }

    default void pressUp() {
    }

    default void releaseUp() {
    }

    default void pressDown() {
    }

    default void releaseDown() {
    }

    default void pressRight() {
    }

    default void releaseRight() {
    }

    default void pressLeft() {
    }

    default void releaseLeft() {
    }

    default void pressSpace() {
    }

    default void pressEnter() {
    }

    default void pressEscape() {
    }

    default void pressPause() {
    }
}