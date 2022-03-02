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

    public static class Table {
        public static int total;
        public static int minesCount;
        public static int scoredCells;
        public static int difficulty;
        public static int money;
        public static int shields;
        public static int luckyCells;
        public static int scoreDice;
        public static int scoreLost;
        public static int scoreTimer;
        public static int moneyScore;
        public static int minesScore;
        public static double luck;
        public static boolean victory;

        public void update() {
            MinesweeperGame game = MinesweeperGame.getInstance();
            total = game.player.score.getTotalScore();
            minesCount = game.field.countAllCells(Cell.Filter.MINED);
            scoredCells = game.field.countAllCells(Cell.Filter.SCORED);
            difficulty = game.difficulty;
            money = game.inventory.money;
            shields = game.player.getBrokenShields();
            luckyCells = game.shop.dice.totalCells;
            scoreDice = game.player.score.getDiceScore();
            scoreLost = game.player.score.getLostScore();
            scoreTimer = game.player.score.getTimerScore();
            moneyScore = game.player.score.getMoneyScore();
            minesScore = game.player.score.getMinesScore();
            luck = game.shop.dice.getAverageLuck();
            victory = game.isVictory;
        }
    }
}
