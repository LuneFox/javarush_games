package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.item.Dice;

public class Results {
    private static MinesweeperGame game;
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
        isResultVictory = game.isResultVictory();
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
        return Integer.toString(game.getScore().getTotalScore());
    }

    private static String getCellScoreInfo() {
        int countCells = game.countAllCells(Cell.Filter.SCORED);
        return countCells + "*" + Options.difficulty + " = " +
                countCells * Options.difficulty;
    }

    private static String getMinesScoreInfo() {
        if (!isResultVictory) return "не учтено";
        int countMines = game.countAllCells(Cell.Filter.MINED);
        return countMines + "*" + 20 * Options.difficulty + " = " +
                countMines * 20 * Options.difficulty;
    }

    private static String getDiceScoreInfo() {
        Dice dice = game.getShop().getDice();
        return dice.getAverageLuck() + " * " + dice.getRollsCount() + " * " + Options.difficulty + " = " +
                getDiceScore();
    }

    private static String getMoneyScoreInfo() {
        if (!isResultVictory) return "не учтено";
        final int money = game.getInventory().getMoney();
        return money + "*" + Options.difficulty + " = " +
                money * Options.difficulty;
    }

    private static String getShieldsScoreInfo() {
        int count = game.countBrokenShields();
        if (count == 0) return "";
        return count + "*-" + (150 * (Options.difficulty / 5)) + " = " +
                game.getScore().getLostScore();
    }

    private static String getDiceScore() {
        return Integer.toString(game.getScore().getDiceScore());
    }

    private static String getTimerScore() {
        return Integer.toString(game.getScore().getTimerScore());
    }

    private static String getDiceAvgLuck() {
        return Double.toString(game.getShop().getDice().getAverageLuck());
    }

    private static String getDiceRollsCount() {
        return Integer.toString(game.getShop().getDice().getRollsCount());
    }

    private static String getDifficulty() {
        return Integer.toString(Options.difficulty);
    }

    public static void setGame(MinesweeperGame game) {
        Results.game = game;
    }
}
