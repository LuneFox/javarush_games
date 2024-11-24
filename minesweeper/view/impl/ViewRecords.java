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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.javarush.engine.cell.Color.*;

public class ViewRecords extends View {

    private static final String TITLE = "<Лучшие игроки>";
    private static final Map<Integer, List<String>> RECORDS;
    private static final Image PRIZE_CUP = new Image(ImageType.PICTURE_PRIZE_CUP);
    private static final Image BACKGROUND = Image.cache.get(ImageType.GUI_BACKGROUND);
    private static final int GAP_BETWEEN_ENTRIES = 20;
    private final Button closeButton = new Button(88, 2, "x", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.MAIN);
        }
    };

    static {
        RECORDS = new HashMap<>();
        // Position, name, JavaRush ID, score
        RECORDS.put(1, Arrays.asList("Dim", "2700224", "43263"));
        RECORDS.put(2, Arrays.asList("Gans Electro", "3136750", "42030"));
        RECORDS.put(3, Arrays.asList("Pavlo Plynko", "28219", "37890"));
        RECORDS.put(4, Arrays.asList("Михаил Васильев", "2700224", "37125"));
    }

    public ViewRecords(MinesweeperGame game) {
        super(game);
    }

    @Override
    public void update() {
        BACKGROUND.draw();
        Printer.print(TITLE, Theme.LABEL.getColor(), Printer.CENTER, 2);
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
            PRIZE_CUP.draw(2, 18 + (GAP_BETWEEN_ENTRIES * i));
        }
    }

    private void drawEntries() {
        Color[] colors = new Color[]{WHITE, GOLD, SILVER, PALEGOLDENROD, LIGHTGREY};
        int topLine = 17 - GAP_BETWEEN_ENTRIES;
        int bottomLine = topLine + 9;
        int currentColor = 0;
        for (int i = 1; i <= RECORDS.size(); i++) {
            // Name (top line, left)
            Printer.print(RECORDS.get(i).get(0), colors[++currentColor], 19, topLine += GAP_BETWEEN_ENTRIES);
            // JavaRush ID (bottom line, left)
            Printer.print("\nID " + RECORDS.get(i).get(1), colors[currentColor], 19, topLine);
            // Score (bottom line, right)
            Printer.print(RECORDS.get(i).get(2), colors[0], 98, bottomLine += GAP_BETWEEN_ENTRIES, Align.RIGHT);
        }
        super.update();
    }
}
