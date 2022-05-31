package com.javarush.games.snake.model;

public class Score {
    private static long score;

    public static void reset() {
        score = 0;
    }

    public static void add(long amount) {
        score += amount;
    }

    public static void remove(long amount) {
        score -= amount;
    }

    public static long get() {
        return score;
    }
}
