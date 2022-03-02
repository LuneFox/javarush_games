package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;
import com.javarush.games.minesweeper.view.graphics.Theme;

public class ControlOptions implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_BACK).tryToPress(x, y)) {
            Screen.set(Screen.MAIN);
        } else if (Screen.options.difficultyDownArea.covers(x, y)) {
            game.changeDifficultySetting(false);
        } else if (Screen.options.difficultyUpArea.covers(x, y)) {
            game.changeDifficultySetting(true);
        } else if (Screen.options.autoBuyFlagsArea.covers(x, y)) {
            game.switchAutoBuyFlags();
        } else if (Screen.options.switchGameTimerArea.covers(x, y)) {
            game.switchTimerSetting();
        } else if (Screen.options.redThemeArea.covers(x, y)) {
            Theme.set(Theme.USSR);
        } else if (Screen.options.greenThemeArea.covers(x, y)) {
            Theme.set(Theme.MINT);
        } else if (Screen.options.blueThemeArea.covers(x, y)) {
            Theme.set(Theme.SKY);
        }
    }

    @Override
    public void pressRight() {
        game.changeDifficultySetting(true);
    }

    @Override
    public void pressLeft() {
        game.changeDifficultySetting(false);
    }

    @Override
    public void pressEsc() {
        Screen.set(Screen.MAIN);
    }
}
