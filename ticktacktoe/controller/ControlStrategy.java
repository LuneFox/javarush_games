package com.javarush.games.ticktacktoe.controller;


public interface ControlStrategy {
    default void click(int x, int y, Click click) {
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
}