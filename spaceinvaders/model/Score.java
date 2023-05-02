package com.javarush.games.spaceinvaders.model;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;

public class Score {
    private static int score;
    private static int topScore;

    static {
        reset();
    }

    public static void reset() {
        score = 0;
    }

    public static void add(int amount) {
        score += amount * SpaceInvadersGame.getStage();
        updateTopScore();
    }

    private static void updateTopScore() {
        topScore = Math.max(score, topScore);
    }

    public static int getScore() {
        return score;
    }

    public static int getTopScore() {
        return topScore;
    }
}
