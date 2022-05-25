package com.javarush.games.game2048.controller;


public interface ControlStrategy {
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

    default void releaseSpace() {
    }

    default void pressEnter() {
    }

    default void releaseEnter() {
    }

    default void pressAnyOtherKey() {

    }
}