package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Util;

public class Score {
    private final MinesweeperGame game;
    private final Player player;
    private int lostScore;   // lost to shield damage
    private int diceScore;   // score from dice bonus
    private int timerScore;  // score from time bonus
    private int topScore;    // top score

    public Score(Player player) {
        this.game = MinesweeperGame.getInstance();
        this.player = player;
    }

    public int getCurrentScore() {
        int score = game.field.countAllCells(Cell.Filter.SCORED) * game.difficulty;
        return score + getDiceScore() + getTimerScore() + getLostScore();
    }

    public int getMoneyScore() {
        if (!game.isVictory) return 0;
        return game.inventory.money * game.difficulty;
    }

    public int getMinesScore() {
        if (!game.isVictory) return 0;
        int minesCount = game.field.countAllCells(Cell.Filter.MINED);
        return minesCount * 20 * game.difficulty;
    }

    public int getTotalScore() {
        return getCurrentScore() + getMinesScore() + getMoneyScore();
    }

    public void registerTopScore() {
        if (getTotalScore() > getTopScore()) {
            setTopScore(getTotalScore());
            player.setTitle(Strings.DIFFICULTY_NAMES[Util.getDifficultyIndex(game.difficulty)]);
        }
    }

    public void reset() {
        lostScore = 0;
        diceScore = 0;
        timerScore = 0;
    }

    // Common getters and setters

    public int getTopScore() {
        return topScore;
    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }

    public int getLostScore() {
        return lostScore;
    }

    public void setLostScore(int lostScore) {
        this.lostScore = lostScore;
    }

    public int getDiceScore() {
        return diceScore;
    }

    public void setDiceScore(int diceScore) {
        this.diceScore = diceScore;
    }

    public int getTimerScore() {
        return timerScore;
    }

    public void setTimerScore(int timerScore) {
        this.timerScore = timerScore;
    }
}
