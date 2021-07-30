package com.javarush.games.minesweeper;

public class Util {
    public static int getDifficultyIndex(int difficultyIndex) {
        return (difficultyIndex / 5 - 1);
    }
}
