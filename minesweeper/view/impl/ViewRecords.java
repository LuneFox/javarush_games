package com.javarush.games.minesweeper.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Printer.Align;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

import static com.javarush.engine.cell.Color.*;

public class ViewRecords extends View {

    private static final String[] RECORDS = new String[]{
            "<Лучшие игроки>",
            "Dim\nID 2700224", "43263",
            "Gans Electro\nID 3136750", "42030",
            "Pavlo Plynko\nID 28219", "37890",
            "Михаил Васильев\nID 2522974", "37125"
    };

    private final Button closeButton = new Button(88, 2, "x", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.MAIN);
        }
    };
    private static final Image PRIZE_CUP = new Image(ImageType.PICTURE_PRIZE_CUP);
    private static final Image BACKGROUND = Image.cache.get(ImageType.GUI_BACKGROUND);

    public ViewRecords(MinesweeperGame game) {
        super(game);
    }

    @Override
    public void update() {
        BACKGROUND.draw();
        Printer.print(RECORDS[0], Theme.LABEL.getColor(), Printer.CENTER, 2);
        closeButton.draw();
        drawPrizeCups();
        drawEntries();
    }

    private void drawPrizeCups() {
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                PRIZE_CUP.changeColor(GOLD, 1);
                PRIZE_CUP.changeColor(YELLOW, 2);
            } else if (i == 1) {
                PRIZE_CUP.changeColor(SILVER, 1);
                PRIZE_CUP.changeColor(WHITE, 2);
            } else {
                PRIZE_CUP.changeColor(DARKGOLDENROD, 1);
                PRIZE_CUP.changeColor(PALEGOLDENROD, 2);
            }
            PRIZE_CUP.draw(2, 18 + (20 * i));
        }
    }

    private void drawEntries() {
        Color[] colors = new Color[]{WHITE, GOLD, SILVER, PALEGOLDENROD, LIGHTGREY};
        int gap = 20;
        int firstLine = 17 - gap;
        int secondLine = firstLine + 9;
        int color = 0;
        int record = 0;
        for (int i = 1; i < 5; i++) {
            Printer.print(RECORDS[++record], colors[++color], 19, firstLine += gap);
            Printer.print(RECORDS[++record], colors[0], 98, secondLine += gap, Align.RIGHT);
        }
        super.update();
    }
}
