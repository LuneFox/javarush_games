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
        this.screenType = Screen.ScreenType.SCORE;
    }

    @Override
    public void display() {
        super.display();
        IMAGES.get(Bitmap.WINDOW_MENU).draw();

        int minesCount = game.countAllCells(Util.Filter.MINED);
        int scoredCells = game.countAllCells(Util.Filter.SCORED);

        String minesScoreDetail = minesCount + "*" + 20 * game.difficulty + " = ";
        String moneyScoreDetail = game.inventory.money + "*" + game.difficulty + " = ";
        String cellScoreDetail = scoredCells + "*" + game.difficulty + " = ";
        String shieldScoreDetail = game.player.countShields == 0 ? "" :
                game.player.countShields + "*-" + (150 * (game.difficulty / 5)) + " = ";
        String youLost = "вы проиграли";

        game.print("подробно", Color.YELLOW, 29, 2);

        game.print("ячейки:\nкубик:\nзолото:\nмины:\nщиты:\nскорость:\n\nитого:", 3, 13);
        game.print(
                (game.player.getTotalScore() +
                        "\n\n" + game.player.scoreTimer +
                        "\n" + (shieldScoreDetail + game.player.scoreLost) +
                        "\n" + (game.lastResultIsVictory ? (minesScoreDetail + game.player.getMinesScore()) : youLost) +
                        "\n" + (game.lastResultIsVictory ? (moneyScoreDetail + game.player.getMoneyScore()) : youLost) +
                        "\n" + game.player.scoreDice +
                        "\n" + cellScoreDetail + scoredCells * game.difficulty),
                Color.YELLOW, 94, 13, true);

        BUTTONS.get(Button.ButtonID.CONFIRM).draw();
    }
}
