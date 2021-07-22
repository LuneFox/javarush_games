package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Button;

public final class ViewScore extends View {
    public ViewScore(MinesweeperGame game) {
        this.game = game;
        this.screenType = Screen.ScreenType.SCORE;
    }

    @Override
    public void display(){
        super.display();
        IMAGES.get(Bitmap.WINDOW_MENU).draw();
        int minesCount = game.countAllCells(MinesweeperGame.Filter.MINED);
        int minesScore = minesCount * 20 * game.difficulty;
        String minesScoreDetail = 20 * game.difficulty + "*" + minesCount + " = ";
        int moneyScore = game.inventory.money * game.difficulty;
        String moneyScoreDetail = game.inventory.money + "*" + game.difficulty + " = ";
        String cellScoreDetail = game.player.openedCells + "*" + game.difficulty + " = ";

        String youLost = "вы проиграли";
        game.print("подробно", Color.YELLOW, 29, 2);
        game.print("ячейки:\nкубик:\nзолото:\nмины:\nщиты:\nскорость:\n\nитого:", 3, 13);
        game.print(
                game.player.score +
                        "\n\n" + game.player.scoreTimer +
                        "\n" + (game.player.scoreLost) +
                        "\n" + (game.lastResultIsVictory ? (minesScoreDetail + minesScore) : youLost) +
                        "\n" + (game.lastResultIsVictory ? (moneyScoreDetail + moneyScore) : youLost) +
                        "\n" + game.player.scoreDice +
                        "\n" + cellScoreDetail + game.player.scoreCell,
                Color.YELLOW, 94, 13, true);

        BUTTONS.get(Button.ButtonID.CONFIRM).draw();
    }
}
