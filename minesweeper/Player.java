package com.javarush.games.minesweeper;

/**
 * Contains player data like score
 */

public class Player {
    public final MinesweeperGame game;
    public String topScoreTitle = "";
    public int topScore = 0;
    public int scoreLost;
    public int scoreDice;
    public int scoreTimer;
    public int countMoves;
    public int countShields;

    public Player(MinesweeperGame game) {
        this.game = game;
        reset();
    }

    public void reset() {
        scoreLost = 0;
        scoreDice = 0;
        countMoves = 0;
        countShields = 0;
        scoreTimer = 0;
    }

    public void registerTopScore() {
        if (getCurrentScore() > topScore) {
            topScore = getTotalScore();
            topScoreTitle = Strings.DIFFICULTY_NAMES[Util.getDifficultyIndex(game.difficulty)];
        }
    }

    public int getCurrentScore() {
        int score = game.countAllCells(Util.Filter.OPEN_NOT_MINED) * game.difficulty;
        return score + scoreDice + scoreTimer + scoreLost;
    }

    public int getMoneyScore() {
        if (!game.lastResultIsVictory) return 0;
        return game.inventory.money * game.difficulty;
    }

    public int getMinesScore() {
        if (!game.lastResultIsVictory) return 0;
        int minesCount = game.countAllCells(Util.Filter.MINED);
        return minesCount * 20 * game.difficulty;
    }

    public int getTotalScore() {
        return getCurrentScore() + getMinesScore() + getMoneyScore();
    }
}
