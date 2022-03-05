package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlOptions implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_BACK).checkLeftTouch(x, y)) {
            Screen.set(Screen.MAIN);
        }
        Options.difficultySelector.checkLeftTouch(x, y);
        Options.autoBuyFlagsSelector.checkLeftTouch(x, y);
        Options.timerEnabledSelector.checkLeftTouch(x, y);
        Options.themeSelector.checkLeftTouch(x, y);
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
