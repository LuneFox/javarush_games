package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.model.options.PageSelector;
import com.javarush.games.minesweeper.view.graphics.*;

import static com.javarush.games.minesweeper.model.player.Score.Table.minesCount;
import static com.javarush.games.minesweeper.model.player.Score.Table.difficulty;
import static com.javarush.games.minesweeper.model.player.Score.Table.money;
import static com.javarush.games.minesweeper.model.player.Score.Table.scoredCells;
import static com.javarush.games.minesweeper.model.player.Score.Table.avgLuck;
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
    public PageSelector pageSelector;

    public ViewScore() {
        this.screen = Screen.SCORE;
        this.pageSelector = new PageSelector(10, 89, 40, 2);
    }

    @Override
    public void update() {
        super.update();
        Cache.get(VisualElement.WIN_MENU).draw();
        pageSelector.draw();

        String minesScoreDetail = minesCount + "*" + 20 * difficulty + " = ";
        String luckDetail = avgLuck + "*" + luckyCells + "*" + difficulty + " = " + scoreDice;
        String moneyScoreDetail = money + "*" + difficulty + " = ";
        String cellScoreDetail = scoredCells + "*" + Options.difficulty + " = ";
        String shieldScoreDetail = shields == 0 ? "" : shields + "*-" + (150 * (difficulty / 5)) + " = ";
        String youLost = "не учтено";

        switch (pageSelector.getCurrentPage()) {
            case 0:
                Printer.print("подробности счёта", Color.YELLOW, Printer.CENTER, 2);
                Printer.print("ячейки:\nкубик:\nмины:\nзолото:\nщиты:\nскорость:\n\nитого:", 3, 13);
                Printer.print(
                        (game.player.score.getTotalScore() +
                                "\n\n" + scoreTimer +
                                "\n" + (shieldScoreDetail + scoreLost) +
                                "\n" + (victory ? (moneyScoreDetail + moneyScore) : youLost) +
                                "\n" + (victory ? (minesScoreDetail + minesScore) : youLost) +
                                "\n" + scoreDice +
                                "\n" + cellScoreDetail + scoredCells * difficulty),
                        Color.YELLOW, 94, 13, true);
                break;
            case 1:
                Printer.print("очки кубика удачи", Color.YELLOW, Printer.CENTER, 2);
                Printer.print("средняя удача:\nзатронуто ячеек:\nбонус сложности:\n\nсуммарно:", 3, 13);
                Printer.print((luckDetail +
                                "\n\n\n" + difficulty +
                                "\n" + luckyCells +
                                "\n" + avgLuck),
                        Color.YELLOW, 94, 13, true);
                break;
            default:
                break;
        }
        Cache.get(Button.ButtonID.GENERAL_CONFIRM).draw();
    }
}
