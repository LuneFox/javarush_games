package com.javarush.games.minesweeper.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.Display;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.GameStatistics;
import com.javarush.games.minesweeper.view.View;

public class ViewScore extends View {
    public static final PageSelector pageSelector = new PageSelector(30, 89, 40, 2);
    private static final int PADDING_TOP = 15;
    private static final int PADDING_RIGHT = Display.SIZE - 2;
    private static final int PADDING_LEFT = 3;

    private final Button closeButton = new Button(88, 2, "x", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.GAME_OVER);
        }
    };
    final Image background = Image.cache.get(ImageType.GUI_BACKGROUND);

    public ViewScore(MinesweeperGame game) {
        super(game);
        linkObject(pageSelector);
    }

    @Override
    public void update() {
        background.draw();
        pageSelector.draw();

        if (pageSelector.getCurrentPage() == 0) {
            printTotalScoreDetails();
        } else if (pageSelector.getCurrentPage() == 1) {
            printDiceScoreDetails();
        }

        closeButton.draw();
        super.update();
    }

    private void printTotalScoreDetails() {
        printPage("<детализация>",
                "ячейки:\n" +
                        "кубик:\n" +
                        "мины:\n" +
                        "золото:\n" +
                        "щиты:\n" +
                        "скорость:\n\n" +
                        "итого:",
                GameStatistics.get("cells") + "\n" +
                        GameStatistics.get("dice_total") + "\n" +
                        GameStatistics.get("mines") + "\n" +
                        GameStatistics.get("money") + "\n" +
                        GameStatistics.get("shield") + "\n" +
                        GameStatistics.get("timer") + "\n\n" +
                        GameStatistics.get("total")
        );
    }

    private void printDiceScoreDetails() {
        printPage("<очки кубика>",
                "средняя удача:\n" +
                        "затронуто ячеек:\n" +
                        "бонус сложности:\n\n" +
                        "в общем счёте:",
                GameStatistics.get("dice_luck") + "\n" +
                        GameStatistics.get("dice_rolls") + "\n" +
                        GameStatistics.get("difficulty") + "\n\n\n" +
                        GameStatistics.get("dice_multiply")
        );
    }

    private void printPage(String title, String leftText, String rightText) {
        Printer.print(title,
                Theme.LABEL.getColor(),
                Printer.CENTER,
                2);

        Printer.print(leftText,
                PADDING_LEFT,
                PADDING_TOP);

        Printer.print(rightText,
                Options.isDeveloperModeEnabled() ? Color.RED : Color.LIGHTGOLDENRODYELLOW,
                PADDING_RIGHT,
                PADDING_TOP,
                Printer.Align.RIGHT);
    }
}
