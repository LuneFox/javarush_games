package com.javarush.games.snake.model;

/** Счёт текущей игры. */
public class Score {
    private long value;

    /** Сбрасывает счёт. */
    public void reset() {
        value = 0;
    }

    /** Начисляет очки. */
    public void add(long amount) {
        value += amount;
    }

    /** Списывает очки. */
    public void remove(long amount) {
        value -= amount;
    }

    /** Возвращает текущий счёт. */
    public long get() {
        return value;
    }
}
