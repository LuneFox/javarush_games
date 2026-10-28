package com.javarush.games.spaceinvaders.model;

/** Состояние игровой сессии. */
public enum GameStatus {
    /** Бой идёт. */
    RUNNING,

    /** Бой приостановлен игроком. */
    PAUSED,

    /** Раунд закончен, идёт финальная анимация. */
    OVER
}
