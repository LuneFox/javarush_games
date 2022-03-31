package com.javarush.games.moonlander.controller;

public interface ControlStrategy {
    default void leftClick(int x, int y) {
    }

    default void rightClick(int x, int y) {
    }

    default void pressUp() {
    }

    default void pressDown() {
    }

    default void pressRight() {
    }

    default void pressLeft() {
    }

    default void pressEnter() {
    }

    default void pressPause() {
    }

    default void pressSpace() {
    }

    default void pressEsc() {
    }

    default void pressOther() {
    }
}