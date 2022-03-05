package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;
import com.javarush.games.minesweeper.view.graphics.Theme;

public class ControlOptions implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_BACK).checkTouch(x, y)) {
            Screen.set(Screen.MAIN);
        }
        Options.difficultySelector.checkTouch(x, y);
        Options.autoBuyFlagsSelector.checkTouch(x, y);
        Options.timerEnabledSelector.checkTouch(x, y);
        Options.themeSelector.checkTouch(x, y);
    }

    @Override
    public void pressRight() {
        Options.difficultySelector.difficultyUp();
    }

    @Override
    public void pressLeft() {
        Options.difficultySelector.difficultyDown();
    }

    @Override
    public void pressEsc() {
        Screen.set(Screen.MAIN);
    }
}
