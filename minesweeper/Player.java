package com.javarush.games.minesweeper;

/**
 * Contains player data like score
 */

public class Player {
    public final MinesweeperGame game;
    public String topScoreTitle = "";
    public int topScore = 0;
    public int score;
    public int scoreLost;
    public int scoreDice;
    public int scoreCell;
    public int countMoves;

    public Player(MinesweeperGame game){
        this.game = game;
        reset();
    }

    public void reset(){
        score = 0;
        scoreLost = 0;
        scoreDice = 0;
        scoreCell = 0;
        countMoves = 0;
    }

    public void registerTopScore() {
        if (score > topScore) {
            topScore = score;
            topScoreTitle = Menu.DIFFICULTY_NAMES.get(game.difficulty / 5 - 1);
        }
    }
}
