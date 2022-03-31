package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.item.Dice;

public class Results {
    private static final MinesweeperGame game = MinesweeperGame.getInstance();
    public static String totalScore;
    public static String cellScoreInfo;
    public static String minesScoreInfo;
    public static String diceScoreInfo;
    public static String moneyScoreInfo;
    public static String shieldScoreInfo;
    public static String diceScore;
    public static String diceAvgLuck;
    public static String timerScore;
    public static String diceRollsCount;
    public static String difficulty;
    private static boolean isResultVictory;

    public static void update() {
        MinesweeperGame game = MinesweeperGame.getInstance();
        isResultVictory = game.isResultVictory;
        totalScore = getTotalScore();
        cellScoreInfo = getCellScoreInfo();
        minesScoreInfo = getMinesScoreInfo();
        diceScoreInfo = getDiceScoreInfo();
        moneyScoreInfo = getMoneyScoreInfo();
        shieldScoreInfo = getShieldsScoreInfo();
        diceScore = getDiceScore();
        diceAvgLuck = getDiceAvgLuck();
        timerScore = getTimerScore();
        diceRollsCount = getDiceRollsCount();
        difficulty = getDifficulty();
    }

    private static String getTotalScore() {
        return Integer.toString(game.player.score.getTotalScore());
    }

    private static String getCellScoreInfo() {
        int countCells = game.boardManager.getField().countAllCells(Cell.Filter.SCORED);
        return countCells + "*" + Options.difficulty + " = " +
                countCells * Options.difficulty;
    }

    private static String getMinesScoreInfo() {
        if (!isResultVictory) return "не учтено";
        int countMines = game.boardManager.getField().countAllCells(Cell.Filter.MINED);
        return countMines + "*" + 20 * Options.difficulty + " = " +
                countMines * 20 * Options.difficulty;
    }

    private static String getDiceScoreInfo() {
        Dice dice = game.shop.dice;
        return dice.getAverageLuck() + " * " + dice.getRollsCount() + " * " + Options.difficulty + " = " +
                game.player.score.getDiceScore();
    }

    private static String getMoneyScoreInfo() {
        if (!isResultVictory) return "не учтено";
        return game.player.inventory.money + "*" + Options.difficulty + " = " +
                game.player.inventory.money * Options.difficulty;
    }

    private static String getShieldsScoreInfo() {
        int countShields = game.player.getBrokenShields();
        if (countShields == 0) return "";
        return countShields + "*-" + (150 * (Options.difficulty / 5)) + " = " +
                game.player.score.getLostScore();
    }

    private static String getDiceScore() {
        return Integer.toString(game.player.score.getDiceScore());
    }

    private static String getTimerScore() {
        return Integer.toString(game.player.score.getTimerScore());
    }

    private static String getDiceAvgLuck() {
        return Double.toString(game.shop.dice.getAverageLuck());
    }

    private static String getDiceRollsCount() {
        return Integer.toString(game.shop.dice.getRollsCount());
    }

    private static String getDifficulty() {
        return Integer.toString(Options.difficulty);
    }
}
