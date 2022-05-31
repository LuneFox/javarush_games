package com.javarush.games.snake.model;

public class Options {
    private static boolean accelerationEnabled;

    static {
        accelerationEnabled = true;
    }

    public static boolean isAccelerationEnabled() {
        return accelerationEnabled;
    }

    public static void switchAccelerationEnabled() {
        accelerationEnabled = !accelerationEnabled;
    }
}
