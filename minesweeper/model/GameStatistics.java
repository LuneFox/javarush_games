package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.Text;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.shop.items.Dice;

import java.util.HashMap;
import java.util.Map;

/** Сводная статистика завершённой партии. */
public class GameStatistics {
    /** Ссылка на игру. */
    private static MinesweeperGame game;
    /** Таблица статистики. */
    private static final Map<String, String> table = new HashMap<>();

    /** Обновляет состояние. */
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

    /** Возвращает значение из кэша. */
    public static String get(String key) {
        return table.getOrDefault(key, Text.STATS_NOT_FOUND.getText());
    }

    /** Возвращает результат партии. */
    private static String getResult() {
        return game.isWon() ? Text.GAME_RESULT_WIN.getText() : Text.GAME_RESULT_LOSE.getText();
    }

    /** Возвращает итоговый счёт. */
    private static String getTotalScore() {
        return Integer.toString(game.getScore().getTotalScore());
    }

    /** Возвращает описание очков за клетки. */
    private static String getCellScoreInfo() {
        int countCells = game.countCells(Cell::isScored);
        return countCells + "*" + Options.getDifficulty() + " = " +
                countCells * Options.getDifficulty();
    }

    /** Возвращает описание очков за мины. */
    private static String getMinesScoreInfo() {
        if (!game.isWon()) return Text.STATS_NOT_COUNTED.getText();
        int countMines = game.countCells(Cell::isMined);
        return countMines + "*" + 20 * Options.getDifficulty() + " = " +
                countMines * 20 * Options.getDifficulty();
    }

    /** Возвращает описание очков кубика. */
    private static String getDiceScoreInfo() {
        return getDice().getAverageLuck() + " * " + getDice().getRollsCount() + " * " + Options.getDifficulty() + " = " +
                getDiceScore();
    }

    /** Возвращает описание очков за золото. */
    private static String getMoneyScoreInfo() {
        if (!game.isWon()) return Text.STATS_NOT_COUNTED.getText();
        final int money = game.getPlayer().getMoneyBalance();
        return money + "*" + Options.getDifficulty() + " = " +
                money * Options.getDifficulty();
    }

    /** Возвращает описание штрафа за щиты. */
    private static String getShieldsScoreInfo() {
        int count = game.getPlayer().getBrokenShields();
        if (count == 0) return "0";
        return count + "*-" + (150 * (Options.getDifficulty() / 5)) + " = " +
                game.getScore().getLostScore();
    }

    /** Возвращает очки кубика. */
    private static String getDiceScore() {
        return Integer.toString(game.getScore().getDiceScore());
    }

    /** Возвращает очки за время. */
    private static String getTimerScore() {
        return Integer.toString(game.getScore().getTimerScore());
    }

    /** Возвращает среднюю удачу кубика. */
    private static String getDiceAvgLuck() {
        return Double.toString(getDice().getAverageLuck());
    }

    /** Возвращает число затронутых кубиком клеток. */
    private static String getDiceRollsCount() {
        return Integer.toString(getDice().getRollsCount());
    }

    /** Возвращает сложность игры. */
    private static String getDifficulty() {
        return Integer.toString(Options.getDifficulty());
    }

    /** Возвращает предмет «Кубик». */
    private static Dice getDice() {
        return game.getShop().getDice();
    }

    /** Задаёт игру. */
    public static void setGame(MinesweeperGame game) {
        GameStatistics.game = game;
    }
}
