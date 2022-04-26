package com.javarush.games.minesweeper.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.Results;
import com.javarush.games.minesweeper.view.View;

public class ViewScore extends View {

    private final static int PADDING_TOP = 15;
    private final static int PADDING_RIGHT = 100 - 7;
    private final static int PADDING_LEFT = 3;

    public static final PageSelector pageSelector = new PageSelector(30, 89, 40, 2);
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

                        Results.get("total") +
                                "\n" +
                                "\n" + Results.get("timer") +
                                "\n" + Results.get("shield") +
                                "\n" + Results.get("money") +
                                "\n" + Results.get("mines") +
                                "\n" + Results.get("dice_total") +
                                "\n" + Results.get("cells"));
                break;

            case 1:
                printPage("<очки кубика>",

                        "средняя удача:\n" +
                                "затронуто ячеек:\n" +
                                "бонус сложности:\n" +
                                "\n" +
                                "в общем счёте:",

                        Results.get("dice") +
                                "\n" +
                                "\n" +
                                "\n" + Results.get("difficulty") +
                                "\n" + Results.get("dice_rolls") +
                                "\n" + Results.get("dice_luck"));
                break;

            default:
                break;
        }
        closeButton.draw();
        super.update();
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
                Options.developerMode ? Color.RED : Color.LIGHTGOLDENRODYELLOW,
                PADDING_RIGHT,
                PADDING_TOP,
                true);
    }

}
