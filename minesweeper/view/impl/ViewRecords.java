package com.javarush.games.minesweeper.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.view.View;

public class ViewRecords extends View {

    final String[] RECORDS = new String[]{
            "<Лучшие игроки>",
            "Dim\nID 2700224", "43263",
            "Pavlo Plynko\nID 28219", "37890",
            "Михаил Васильев\nID 2522974", "37125"
    };

    private final Button closeButton = new Button(88, 2, 0, 0, "x", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.MAIN);
        }
    };
    final Image prizeCup = new Image(ImageType.PICTURE_PRIZE_CUP);
    final Image background = Image.cache.get(ImageType.GUI_BACKGROUND);

    @Override
    public void update() {
        background.draw();
        Printer.print(RECORDS[0], Color.YELLOW, Printer.CENTER, 2);
        closeButton.draw();
        drawPrizeCups();
        drawEntries();
    }

    private void drawPrizeCups() {
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                prizeCup.replaceColor(Color.GOLD, 1);
                prizeCup.replaceColor(Color.YELLOW, 2);
            } else if (i == 1) {
                prizeCup.replaceColor(Color.SILVER, 1);
                prizeCup.replaceColor(Color.WHITE, 2);
            } else {
                prizeCup.replaceColor(Color.DARKGOLDENROD, 1);
                prizeCup.replaceColor(Color.PALEGOLDENROD, 2);
            }
            prizeCup.draw(2, 18 + (30 * i));
        }
    }

    private void drawEntries() {
        Color[] colors = new Color[]{Color.WHITE, Color.GOLD, Color.SILVER, Color.PALEGOLDENROD};
        Printer.print(RECORDS[1], colors[1], 19, 17);
        Printer.print(RECORDS[2], colors[0], 94, 26, true);
        Printer.print(RECORDS[3], colors[2], 19, 47);
        Printer.print(RECORDS[4], colors[0], 94, 56, true);
        Printer.print(RECORDS[5], colors[3], 19, 77);
        Printer.print(RECORDS[6], colors[0], 94, 86, true);
        super.update();
    }
}
