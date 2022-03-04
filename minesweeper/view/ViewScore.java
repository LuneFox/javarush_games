package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.view.graphics.*;

import static com.javarush.games.minesweeper.model.player.Score.Table.minesCount;
import static com.javarush.games.minesweeper.model.player.Score.Table.difficulty;
import static com.javarush.games.minesweeper.model.player.Score.Table.money;
import static com.javarush.games.minesweeper.model.player.Score.Table.scoredCells;
import static com.javarush.games.minesweeper.model.player.Score.Table.luck;
import static com.javarush.games.minesweeper.model.player.Score.Table.luckyCells;
import static com.javarush.games.minesweeper.model.player.Score.Table.shields;
import static com.javarush.games.minesweeper.model.player.Score.Table.scoreTimer;
import static com.javarush.games.minesweeper.model.player.Score.Table.scoreLost;
import static com.javarush.games.minesweeper.model.player.Score.Table.moneyScore;
import static com.javarush.games.minesweeper.model.player.Score.Table.minesScore;
import static com.javarush.games.minesweeper.model.player.Score.Table.scoreDice;
import static com.javarush.games.minesweeper.model.player.Score.Table.victory;

/**
 * Shows score details in the end of the game.
 */

public final class ViewScore extends View {
    public ViewScore() {
        this.screen = Screen.SCORE;
    }

    @Override
    public void update() {
        super.update();
        Cache.get(VisualElement.WIN_MENU).draw();
        String minesScoreDetail = minesCount + "*" + 20 * difficulty + " = ";
        String moneyScoreDetail = money + "*" + difficulty + " = ";
        String cellScoreDetail = scoredCells + "*" + game.difficulty + " = ";
        String luckDetail = luck + "*" + luckyCells + "*" + difficulty + " = ";
        String shieldScoreDetail = shields == 0 ? "" : shields + "*-" + (150 * (difficulty / 5)) + " = ";
        String youLost = "не учтено";

        Printer.print("подробности счёта", Color.YELLOW, -1, 2);

        Printer.print("ячейки:\nкуб:\nмины:\nзолото:\nщиты:\nскорость:\n\nитого:", 3, 13);
        Printer.print(
                (game.player.score.getTotalScore() +
                        "\n\n" + scoreTimer +
                        "\n" + (shieldScoreDetail + scoreLost) +
                        "\n" + (victory ? (moneyScoreDetail + moneyScore) : youLost) +
                        "\n" + (victory ? (minesScoreDetail + minesScore) : youLost) +
                        "\n" + (scoreDice > 0 ? (luckDetail + scoreDice) : "0") +
                        "\n" + cellScoreDetail + scoredCells * difficulty),
                Color.YELLOW, 94, 13, true);

        Cache.get(Button.ButtonID.GENERAL_CONFIRM).draw();
    }
}
