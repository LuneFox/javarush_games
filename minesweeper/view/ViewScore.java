package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Button;

/**
 * Shows score details in the end of the game.
 */

public final class ViewScore extends View {
    public ViewScore(MinesweeperGame game) {
        this.game = game;
        this.screen = Screen.SCORE;
    }

    @Override
    public void display() {
        super.display();
        IMAGES.get(Bitmap.WIN_MENU).draw();

        int minesCount = game.countAllCells(Util.Filter.MINED);
        int scoredCells = game.countAllCells(Util.Filter.SCORED);
        int difficulty = game.difficulty;
        int money = game.inventory.money;
        int shields = game.player.getBrokenShields();
        int luckyCells = game.shop.dice.totalCells;
        int scoreDice = game.player.score.getDiceScore();
        int scoreLost = game.player.score.getLostScore();
        int scoreTimer = game.player.score.getTimerScore();
        int moneyScore = game.player.score.getMoneyScore();
        int minesScore = game.player.score.getMinesScore();
        double luck = game.shop.dice.getAverageLuck();
        boolean victory = game.lastResultIsVictory;

        String minesScoreDetail = minesCount + "*" + 20 * difficulty + " = ";
        String moneyScoreDetail = money + "*" + difficulty + " = ";
        String cellScoreDetail = scoredCells + "*" + game.difficulty + " = ";
        String luckDetail = luck + "*" + luckyCells + "*" + difficulty + " = ";
        String shieldScoreDetail = shields == 0 ? "" : shields + "*-" + (150 * (difficulty / 5)) + " = ";
        String youLost = "не учтено";

        game.print("подробно", Color.YELLOW, 29, 2);

        game.print("ячейки:\nкуб:\nмины:\nзолото:\nщиты:\nскорость:\n\nитого:", 3, 13);
        game.print(
                (game.player.score.getTotalScore() +
                        "\n\n" + scoreTimer +
                        "\n" + (shieldScoreDetail + scoreLost) +
                        "\n" + (victory ? (moneyScoreDetail + moneyScore) : youLost) +
                        "\n" + (victory ? (minesScoreDetail + minesScore) : youLost) +
                        "\n" + (scoreDice > 0 ? (luckDetail + scoreDice) : "0") +
                        "\n" + cellScoreDetail + scoredCells * difficulty),
                Color.YELLOW, 94, 13, true);

        BUTTONS.get(Button.ButtonID.CONFIRM).draw();
    }
}
