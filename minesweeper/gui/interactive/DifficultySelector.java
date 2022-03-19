package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.gui.image.Image;

public class DifficultySelector extends InteractiveObject {
    private static final int MAX_DIFFICULTY = 45;
    private static final int MIN_DIFFICULTY = 5;
    private static final int DIFFICULTY_STEP = 5;
    private int difficultySetting;
    private final Arrow difficultyDownArrow;
    private final Arrow difficultyUpArrow;
    private final Image[] bars = new Image[MAX_DIFFICULTY / 5];

    public DifficultySelector(int x, int y) {
        super(x, y);
        difficultySetting = 10;
        this.width = 49;
        difficultyDownArrow = new Arrow(x, y, false);
        difficultyUpArrow = new Arrow(x + width - difficultyDownArrow.width, y, true);
        this.height = difficultyDownArrow.height;

        for (int i = 0; i < bars.length; i++) {
            Image bar = new Image(ImageType.GUI_DIFFICULTY_BAR, (i * 4) + this.x + difficultyDownArrow.width + 2, this.y);
            bar.restrictColorUpdate(true);
            if (i > 6) bar.replaceColor(Color.RED, 1);
            else if (i > 4) bar.replaceColor(Color.ORANGE, 1);
            else if (i > 2) bar.replaceColor(Color.YELLOW, 1);
            bars[i] = bar;
        }
    }

    @Override
    public void draw() {
        // Draw bars
        for (int i = 0; i < difficultySetting / 5; i++)
            bars[i].draw();

        // Draw arrows
        difficultyDownArrow.draw();
        difficultyUpArrow.draw();

        // Draw difficulty name
        String difficultyName = Strings.DIFFICULTY_NAMES[(difficultySetting / 5) - 1];
        Color nameColor = Theme.MAIN_MENU_QUOTE_FRONT.getColor();
        Printer.print(difficultyName, nameColor, x + width - difficultyUpArrow.width, y + height + 1, true);
    }

    @Override
    public void onLeftClick() {
        if (difficultyDownArrow.tryClick(latestClickX, latestClickY)) {
            difficultyDown();
        } else if (difficultyUpArrow.tryClick(latestClickX, latestClickY)) {
            difficultyUp();
        }
    }

    public void difficultyUp() {
        difficultyUpArrow.onLeftClick();
        if (difficultySetting < MAX_DIFFICULTY)
            difficultySetting += DIFFICULTY_STEP;
    }

    public void difficultyDown() {
        difficultyDownArrow.onLeftClick();
        if (difficultySetting > MIN_DIFFICULTY)
            difficultySetting -= DIFFICULTY_STEP;
    }

    public int getDifficultySetting() {
        return difficultySetting;
    }
}
