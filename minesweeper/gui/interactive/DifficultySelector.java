package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Options;

public class DifficultySelector extends InteractiveObject {
    private static final int MAX_DIFFICULTY = 45;
    private static final int MIN_DIFFICULTY = 5;
    private static final int DIFFICULTY_STEP = 5;
    private static final int DEFAULT_WIDTH = 49;
    private static final int DEFAULT_DIFFICULTY_SETTING = 10;
    private int difficultySetting;
    private final Arrow difficultyDownArrow;
    private final Arrow difficultyUpArrow;
    private final Image[] bars = new Image[MAX_DIFFICULTY / 5];

    public DifficultySelector(int x, int y) {
        super(x, y);

        this.width = DEFAULT_WIDTH;
        difficultyDownArrow = Arrow.createLeftArrow(x, y);
        difficultyUpArrow = Arrow.createRightArrow(x + width - difficultyDownArrow.width, y);
        this.height = difficultyDownArrow.height;

        difficultySetting = DEFAULT_DIFFICULTY_SETTING;
        createBars();
    }

    private void createBars() {
        for (int i = 0; i < bars.length; i++) {
            int x = getBarHorizontalPosition(i);
            Image bar = new Image(ImageType.GUI_DIFFICULTY_BAR, x, this.y);
            bar.lockColors();

            if (i > 6) {
                bar.changeColor(Color.RED, 1);
            } else if (i > 4) {
                bar.changeColor(Color.ORANGE, 1);
            } else if (i > 2) {
                bar.changeColor(Color.YELLOW, 1);
            }

            bars[i] = bar;
        }
    }

    private int getBarHorizontalPosition(int i) {
        return (i * 4) + this.x + difficultyDownArrow.width + 2;
    }

    @Override
    public void draw() {
        drawBars();
        drawDifficultyArrows();
        printDifficultyName();
    }

    private void drawBars() {
        final int barsCount = difficultySetting / 5;

        for (int i = 0; i < barsCount; i++) {
            bars[i].draw();
        }
    }

    private void drawDifficultyArrows() {
        difficultyDownArrow.draw();
        difficultyUpArrow.draw();
    }

    private void printDifficultyName() {
        String difficultyName = Options.getDifficultyName(this.difficultySetting);
        Color nameColor = Theme.MAIN_MENU_QUOTE_FRONT.getColor();
        Printer.print(difficultyName, nameColor, x + width, y + height + 1, Printer.Align.RIGHT);
    }

    @Override
    public void onLeftClick() {
        if (difficultyDownArrow.covers(latestClickX, latestClickY)) {
            difficultyDownArrow.click(latestClickX, latestClickY);
            difficultyDown();
        } else if (difficultyUpArrow.covers(latestClickX, latestClickY)) {
            difficultyUpArrow.click(latestClickX, latestClickY);
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
