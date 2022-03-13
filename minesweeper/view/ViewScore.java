package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.player.Score;

import static com.javarush.games.minesweeper.model.player.Score.Table.*;

public class ViewScore extends View {

    private final Button confirmButton = new Button(61, 88, 36, 9, "ясно", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.GAME_OVER);
        }
    };
    Image background = Image.cache.get(ImageType.WIN_MENU);
    PageSelector pageSelector = Score.Table.pageSelector.linkView(this);

    public ViewScore(Phase phase) {
        super(phase);
    }

    @Override
    public void update() {
        background.draw();
        pageSelector.draw();
        String minesScoreDetail = minesCount + "*" + 20 * difficulty + " = ";
        String luckDetail = diceAvgLuck + "*" + diceLuckyCells + "*" + difficulty + " = " + scoreDice;
        String moneyScoreDetail = moneyLeftOver + "*" + difficulty + " = ";
        String cellScoreDetail = cellsCount + "*" + Options.difficulty + " = ";
        String shieldScoreDetail = penaltyShields == 0 ? "" : penaltyShields + "*-" + (150 * (difficulty / 5)) + " = ";
        String youLost = "не учтено";

        switch (Score.Table.pageSelector.getCurrentPage()) {
            case 0:
                Printer.print("подробности счёта", Color.YELLOW, Printer.CENTER, 2);
                Printer.print("ячейки:\nкубик:\nмины:\nзолото:\nщиты:\nскорость:\n\nитого:", 3, 13);

                Printer.print(
                        (total +
                                "\n\n" + scoreTimer +
                                "\n" + (shieldScoreDetail + scoreLost) +
                                "\n" + (victory ? (moneyScoreDetail + scoreMoney) : youLost) +
                                "\n" + (victory ? (minesScoreDetail + scoreMines) : youLost) +
                                "\n" + scoreDice +
                                "\n" + cellScoreDetail + cellsCount * difficulty),
                        Options.developerMode ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 94, 13, true);
                break;
            case 1:
                Printer.print("очки кубика удачи", Color.YELLOW, Printer.CENTER, 2);
                Printer.print("средняя удача:\nзатронуто ячеек:\nбонус сложности:\n\nсуммарно:", 3, 13);
                Printer.print((luckDetail +
                                "\n\n\n" + difficulty +
                                "\n" + diceLuckyCells +
                                "\n" + diceAvgLuck),
                        Options.developerMode ? Color.RED : Color.LIGHTGOLDENRODYELLOW, 94, 13, true);
                break;
            default:
                break;
        }
        confirmButton.draw();
        super.update();
    }

}
