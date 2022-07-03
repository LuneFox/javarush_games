package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;

public class Score {
    private static MinesweeperGame game;
    private final Player player;
    private int lostScore;
    private int diceScore;
    private int timerScore;
    private int topScore;

    public Score(Player player) {
        this.player = player;
    }

    public void reset() {
        lostScore = 0;
        diceScore = 0;
        timerScore = 0;
    }

    public void registerTopScore() {
        int totalScore = getTotalScore();
        if (totalScore <= topScore) return;
        setTopScore(totalScore);
        player.setTitle(Options.getDifficultyName(Options.getDifficulty()));
        PopUpMessage.show("Новый рекорд!");
    }

    public int getCurrentScore() {
        int score = game.getAllCells(Cell::isScored).size() * Options.getDifficulty();
        return score + getDiceScore() + getTimerScore() + getLostScore();
    }

    public int getTotalScore() {
        return getCurrentScore() + getMinesScore() + getMoneyScore();
    }

    public int getMoneyScore() {
        if (!game.isWon()) return 0;
        return game.getPlayer().getMoneyBalance() * Options.getDifficulty();
    }

    public int getMinesScore() {
        if (!game.isWon()) return 0;
        int minesCount = game.getAllCells(Cell::isMined).size();
        return minesCount * 20 * Options.getDifficulty();
    }

    public void addTimerScore() {
        this.timerScore += game.getTimerBonus();
    }

    public void addDiceScore(int amount) {
        this.diceScore += amount;
    }

    /*
     * Plain getters and setters
     */

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

    public int getTimerScore() {
        return timerScore;
    }

    public static void setGame(MinesweeperGame game) {
        Score.game = game;
    }
}
