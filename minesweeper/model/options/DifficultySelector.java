package com.javarush.games.minesweeper.model.options;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.DrawableObject;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.view.graphics.Image;
import com.javarush.games.minesweeper.view.graphics.Printer;
import com.javarush.games.minesweeper.view.graphics.Theme;
import com.javarush.games.minesweeper.view.graphics.VisualElement;

import java.util.LinkedList;

public class DifficultySelector extends DrawableObject {
    private int difficultySetting;
    private final MenuArrow difficultyDownArrow;
    private final MenuArrow difficultyUpArrow;

    public DifficultySelector(int x, int y) {
        super(x, y);
        difficultySetting = 10;
        this.width = 49;
        difficultyDownArrow = new MenuArrow(x, y, false);
        difficultyUpArrow = new MenuArrow(x + width - difficultyDownArrow.width, y, true);
        this.height = difficultyDownArrow.height;
    }

    @Override
    public void draw() {
        drawDifficultyBars();
        difficultyDownArrow.draw();
        difficultyUpArrow.draw();
        String difficultyName = Strings.DIFFICULTY_NAMES[(difficultySetting / 5) - 1];
        Color nameColor = Theme.MAIN_MENU_QUOTE_FRONT.getColor();
        Printer.print(difficultyName, nameColor, x + width - difficultyUpArrow.width, y + height + 1, true);
    }

    private void drawDifficultyBars() {
        LinkedList<Image> bars = new LinkedList<>();
        for (int i = 0; i < difficultySetting / 5; i++) {
            Image bar = new Image(VisualElement.MENU_DIFFICULTY_BAR,
                    (i * 4) + this.x + difficultyDownArrow.width + 2, this.y);
            if (i > 1) bar.replaceColor(Color.YELLOW, 1);
            if (i > 3) bar.replaceColor(Color.ORANGE, 1);
            if (i > 5) bar.replaceColor(Color.RED, 1);
            bars.add(bar);
        }
        bars.forEach(Image::draw);
    }

    @Override
    protected void onTouch() {
        if (difficultyDownArrow.checkTouch(lastClickX, lastClickY)) {
            difficultyDown();
        } else if (difficultyUpArrow.checkTouch(lastClickX, lastClickY)) {
            difficultyUp();
        }
    }

    public void difficultyUp() {
        difficultyUpArrow.onTouch();
        if (difficultySetting < 45)
            difficultySetting += 5;
    }

    public void difficultyDown() {
        difficultyDownArrow.onTouch();
        if (difficultySetting > 5)
            difficultySetting -= 5;
    }

    public int getDifficultySetting() {
        return difficultySetting;
    }
}
