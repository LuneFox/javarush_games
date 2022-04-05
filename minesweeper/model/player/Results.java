package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.item.Dice;

import java.util.HashMap;
import java.util.Map;

public class Results {
    private static MinesweeperGame game;
    private static final Map<String, String> table = new HashMap<>();

    public static void update() {
        table.put("result", getResult());
        table.put("total", getTotalScore());
        table.put("cells", getCellScoreInfo());
        table.put("mines", getMinesScoreInfo());
        table.put("money", getMoneyScoreInfo());
        table.put("shield", getShieldsScoreInfo());
        table.put("dice", getDiceScoreInfo());
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
        return game.isResultVictory() ? "<победа!>" : "<не повезло!>";
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
        if (!game.isResultVictory()) return "не учтено";
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
        if (!game.isResultVictory()) return "не учтено";
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
