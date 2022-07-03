package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.shop.items.Dice;

import java.util.HashMap;
import java.util.Map;

public class GameStatistics {
    private static MinesweeperGame game;
    private static final Map<String, String> table = new HashMap<>();

    public static void update() {
        table.put("result", getResult());
        table.put("total", getTotalScore());
        table.put("cells", getCellScoreInfo());
        table.put("mines", getMinesScoreInfo());
        table.put("money", getMoneyScoreInfo());
        table.put("shield", getShieldsScoreInfo());
        table.put("dice_multiply", getDiceScoreInfo());
        table.put("dice_luck", getDiceAvgLuck());
        table.put("dice_rolls", getDiceRollsCount());
        table.put("dice_total", getDiceScore());
        table.put("timer", getTimerScore());
        table.put("difficulty", getDifficulty());
    }

    public static String get(String key) {
        return table.getOrDefault(key, "не найдено");
    }

    private static String getResult() {
        return game.isWon() ? "<победа!>" : "<не повезло!>";
    }

    private static String getTotalScore() {
        return Integer.toString(game.getScore().getTotalScore());
    }

    private static String getCellScoreInfo() {
        long countCells = game.getAllCells(Cell::isScored).size();
        return countCells + "*" + Options.getDifficulty() + " = " +
                countCells * Options.getDifficulty();
    }

    private static String getMinesScoreInfo() {
        if (!game.isWon()) return "не учтено";
        long countMines = game.getAllCells(Cell::isMined).size();
        return countMines + "*" + 20 * Options.getDifficulty() + " = " +
                countMines * 20 * Options.getDifficulty();
    }

    private static String getDiceScoreInfo() {
        return getDice().getAverageLuck() + " * " + getDice().getRollsCount() + " * " + Options.getDifficulty() + " = " +
                getDiceScore();
    }

    private static String getMoneyScoreInfo() {
        if (!game.isWon()) return "не учтено";
        final int money = game.getPlayer().getMoneyBalance();
        return money + "*" + Options.getDifficulty() + " = " +
                money * Options.getDifficulty();
    }

    private static String getShieldsScoreInfo() {
        int count = game.getPlayer().getBrokenShields();
        if (count == 0) return "0";
        return count + "*-" + (150 * (Options.getDifficulty() / 5)) + " = " +
                game.getScore().getLostScore();
    }

    private static String getDiceScore() {
        return Integer.toString(game.getScore().getDiceScore());
    }

    private static String getTimerScore() {
        return Integer.toString(game.getScore().getTimerScore());
    }

    private static String getDiceAvgLuck() {
        return Double.toString(getDice().getAverageLuck());
    }

    private static String getDiceRollsCount() {
        return Integer.toString(getDice().getRollsCount());
    }

    private static String getDifficulty() {
        return Integer.toString(Options.getDifficulty());
    }

    private static Dice getDice() {
        return game.getShop().getDice();
    }

    public static void setGame(MinesweeperGame game) {
        GameStatistics.game = game;
    }
}
