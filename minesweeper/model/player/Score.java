package com.javarush.games.minesweeper.model.player;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
import com.javarush.games.minesweeper.model.board.Dice;
import com.javarush.games.minesweeper.model.board.Field;

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

    public void addScore(Cell cell) {
        int randomNumber = game.getRandomNumber(6) + 1;
        Dice dice = game.boardManager.getDice();
        dice.setImage(randomNumber, dice.appearCell.x, dice.appearCell.y);

        if (cell.isMined()) return;
        if (game.shop.luckyDice.isActivated()) {
            player.score.setDiceScore(player.score.getDiceScore() + Options.difficulty * randomNumber);
            dice.totalCells++;
            dice.totalBonus += randomNumber;
        }
        game.setScore(player.score.getCurrentScore());
    }

    public int getCurrentScore() {
        int score = game.boardManager.getField().countAllCells(Cell.Filter.SCORED) * Options.difficulty;
        return score + getDiceScore() + getTimerScore() + getLostScore();
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

    public int getTotalScore() {
        return getCurrentScore() + getMinesScore() + getMoneyScore();
    }

    public void registerTopScore() {
        if (getTotalScore() > getTopScore()) {
            setTopScore(getTotalScore());
            player.setTitle(Strings.DIFFICULTY_NAMES[Options.difficulty / 5 - 1]);
            PopUpMessage.show("Новый рекорд!");
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

    public void addTimerScore() {
        this.timerScore += game.timer.getScore();
    }

    public static class Table {
        public static PageSelector pageSelector = new PageSelector(30, 89, 40, 2);
        public static int total;
        public static int minesCount;
        public static int cellsCount;
        public static int difficulty;
        public static int moneyLeftOver;
        public static int penaltyShields;
        public static int diceLuckyCells;
        public static int scoreDice;
        public static int scoreLost;
        public static int scoreTimer;
        public static int scoreMoney;
        public static int scoreMines;
        public static double diceAvgLuck;
        public static boolean victory;

        public static void update() {
            MinesweeperGame game = MinesweeperGame.getInstance();
            Score score = game.player.score;

            scoreDice = score.getDiceScore();
            scoreLost = score.getLostScore();
            scoreTimer = score.getTimerScore();
            scoreMoney = score.getMoneyScore();
            scoreMines = score.getMinesScore();
            total = score.getTotalScore();

            victory = game.isResultVictory;
            difficulty = Options.difficulty;

            Field field = game.boardManager.getField();
            Dice dice = game.boardManager.getDice();
            minesCount = field.countAllCells(Cell.Filter.MINED);
            cellsCount = field.countAllCells(Cell.Filter.SCORED);
            penaltyShields = game.player.getBrokenShields();
            diceLuckyCells = dice.totalCells;
            diceAvgLuck = dice.getAverageLuck();
            moneyLeftOver = game.player.inventory.money;
        }
    }
}
