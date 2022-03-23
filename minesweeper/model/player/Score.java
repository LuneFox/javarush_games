package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.model.board.Cell;

public class Score {
    private final MinesweeperGame game;
    private final Player player;
    private int lostScore;
    private int diceScore;
    private int timerScore;
    private int topScore;

    public Score(Player player) {
        this.game = MinesweeperGame.getInstance();
        this.player = player;
    }

    public void reset() {
        lostScore = 0;
        diceScore = 0;
        timerScore = 0;
    }

    public void registerTopScore() {
        if (getTotalScore() <= getTopScore()) return;
        setTopScore(getTotalScore());
        player.setTitle(Strings.DIFFICULTY_NAMES[Options.difficulty / 5 - 1]);
        PopUpMessage.show("Новый рекорд!");
    }

    public int getCurrentScore() {
        int score = game.boardManager.getField().countAllCells(Cell.Filter.SCORED) * Options.difficulty;
        return score + getDiceScore() + getTimerScore() + getLostScore();
    }

    public int getTotalScore() {
        return getCurrentScore() + getMinesScore() + getMoneyScore();
    }

    public int getMoneyScore() {
        if (!game.isResultVictory) return 0;
        return game.player.inventory.money * Options.difficulty;
    }

    public int getMinesScore() {
        if (!game.isResultVictory) return 0;
        int minesCount = game.boardManager.getField().countAllCells(Cell.Filter.MINED);
        return minesCount * 20 * Options.difficulty;
    }

    public void addTimerScore() {
        this.timerScore += game.boardManager.getTimer().getScore();
    }

    public void addDiceScore(int amount) {
        this.diceScore += amount;
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

    public int getTimerScore() {
        return timerScore;
    }

}
