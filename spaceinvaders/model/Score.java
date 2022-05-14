package com.javarush.games.spaceinvaders.model;

import java.util.Date;

public class Score {
    private static int score;
    public static Date startTime;

    static {
        reset();
    }

    public static void reset() {
        score = 0;
        startTime = new Date();
    }

    public static void add(int amount) {
        score += amount * getMultiplier();
    }

    public static int get() {
        return score;
    }

    private static int getMultiplier() {
        long millis = new Date().getTime() - startTime.getTime();
        int seconds = (int) millis / 1000;
        return Math.max(100 - seconds, 0);
    }
}
