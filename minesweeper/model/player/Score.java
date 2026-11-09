package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;

/** Подсчёт очков и лучшего результата. */
public class Score {
    /** Ссылка на игру. */
    private static MinesweeperGame game;
    /** Игрок. */
    private final Player player;
    /** Штраф. */
    private int lostScore;
    /** Очки кубика. */
    private int diceScore;
    /** Очки за время. */
    private int timerScore;
    /** Лучший счёт. */
    private int topScore;

    /** Создаёт экземпляр Score. */
    public Score(Player player) {
        this.player = player;
    }

    /** Сбрасывает состояние в начальное. */
    public void reset() {
        lostScore = 0;
        diceScore = 0;
        timerScore = 0;
    }

    /** Регистрирует новый рекорд. */
    public void registerTopScore() {
        int totalScore = getTotalScore();
        if (totalScore <= topScore) return;
        setTopScore(totalScore);
        player.setTitle(Options.getDifficultyName(Options.getDifficulty()));
        game.showMessage("Новый рекорд!");
    }

    /** Возвращает текущий счёт. */
    public int getCurrentScore() {
        int score = game.countCells(Cell::isScored) * Options.getDifficulty();
        return score + getDiceScore() + getTimerScore() + getLostScore();
    }

    /** Возвращает итоговый счёт. */
    public int getTotalScore() {
        return getCurrentScore() + getMinesScore() + getMoneyScore();
    }

    /** Возвращает очки за оставшееся золото. */
    public int getMoneyScore() {
        if (!game.isWon()) return 0;
        return game.getPlayer().getMoneyBalance() * Options.getDifficulty();
    }

    /** Возвращает очки за закрытые мины. */
    public int getMinesScore() {
        if (!game.isWon()) return 0;
        int minesCount = game.countCells(Cell::isMined);
        return minesCount * 20 * Options.getDifficulty();
    }

    /** Добавляет очки за время. */
    public void addTimerScore() {
        this.timerScore += game.getTimerBonus();
    }

    /** Добавляет очки кубика. */
    public void addDiceScore(int amount) {
        this.diceScore += amount;
    }

    /*
     * Обычные геттеры и сеттеры
     */

    /** Возвращает лучший счёт. */
    public int getTopScore() {
        return topScore;
    }

    /** Задаёт лучший счёт. */
    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }

    /** Возвращает штраф. */
    public int getLostScore() {
        return lostScore;
    }

    /** Задаёт штраф. */
    public void setLostScore(int lostScore) {
        this.lostScore = lostScore;
    }

    /** Возвращает очки кубика. */
    public int getDiceScore() {
        return diceScore;
    }

    /** Возвращает очки за время. */
    public int getTimerScore() {
        return timerScore;
    }

    /** Задаёт игру. */
    public static void setGame(MinesweeperGame game) {
        Score.game = game;
    }
}
