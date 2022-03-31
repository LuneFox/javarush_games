package com.javarush.games.minesweeper.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.player.Results;
import com.javarush.games.minesweeper.view.View;

public class ViewScore extends View {

    private final static int PADDING_TOP = 15;
    private final static int PADDING_RIGHT = 100 - 7;
    private final static int PADDING_LEFT = 3;

    public static final PageSelector pageSelector = new PageSelector(30, 89, 40, 2);
    private final Button closeButton = new Button(88, 2, 0, 0, "x", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.GAME_OVER);
        }
    };
    final Image background = Image.cache.get(ImageType.GUI_BACKGROUND);

    public ViewScore() {
        linkObject(pageSelector);
    }

    @Override
    public void update() {
        background.draw();
        pageSelector.draw();

        switch (pageSelector.getCurrentPage()) {
            case 0:
                printPage("<детализация>",

                        "ячейки:\n" +
                                "кубик:\n" +
                                "мины:\n" +
                                "золото:\n" +
                                "щиты:\n" +
                                "скорость:\n" +
                                "\n" +
                                "итого:",

                        Results.totalScore +
                                "\n" +
                                "\n" + Results.timerScore +
                                "\n" + Results.shieldScoreInfo +
                                "\n" + Results.moneyScoreInfo +
                                "\n" + Results.minesScoreInfo +
                                "\n" + Results.diceScore +
                                "\n" + Results.cellScoreInfo);
                break;

            case 1:
                printPage("<очки кубика>",

                        "средняя удача:\n" +
                                "затронуто ячеек:\n" +
                                "бонус сложности:\n" +
                                "\n" +
                                "в общем счёте:",

                        Results.diceScoreInfo +
                                "\n" +
                                "\n" +
                                "\n" + Results.difficulty +
                                "\n" + Results.diceRollsCount +
                                "\n" + Results.diceAvgLuck);
                break;

            default:
                break;
        }
        closeButton.draw();
        super.update();
    }

    private void printPage(String title, String leftText, String rightText) {
        Printer.print(title,
                Color.YELLOW,
                Printer.CENTER,
                2);

        Printer.print(leftText,
                PADDING_LEFT,
                PADDING_TOP);
        
        Printer.print(rightText,
                Options.developerMode ? Color.RED : Color.LIGHTGOLDENRODYELLOW,
                PADDING_RIGHT,
                PADDING_TOP,
                true);
    }

}
