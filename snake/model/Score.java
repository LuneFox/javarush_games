package com.javarush.games.snake.model;

public class Score {
    private long value;

    public void reset() {
        value = 0;
    }

    public void add(long amount) {
        value += amount;
    }

    public void remove(long amount) {
        value -= amount;
    }

    public long get() {
        return value;
    }
}
