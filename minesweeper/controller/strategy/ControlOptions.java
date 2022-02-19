package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Theme;
import com.javarush.games.minesweeper.view.View;

public class ControlOptions implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.BACK).covers(x, y)) {
            View.main.display();
        } else if (View.options.difficultyDownArea.covers(x, y)) {
            game.changeDifficultySetting(false);
        } else if (View.options.difficultyUpArea.covers(x, y)) {
            game.changeDifficultySetting(true);
        } else if (View.options.autoBuyFlagsArea.covers(x, y)) {
            game.switchAutoBuyFlags();
        } else if (View.options.switchGameTimerArea.covers(x, y)) {
            game.switchTimerSetting();
        } else if (View.options.redThemeArea.covers(x, y)) {
            Theme.setTheme(Theme.USSR);
        } else if (View.options.greenThemeArea.covers(x, y)) {
            Theme.setTheme(Theme.MINT);
        } else if (View.options.blueThemeArea.covers(x, y)) {
            Theme.setTheme(Theme.SKY);
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
        View.main.display();
    }
}
