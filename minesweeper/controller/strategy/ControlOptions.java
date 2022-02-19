package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Theme;
import com.javarush.games.minesweeper.view.View;

public class ControlOptions implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.BACK).covers(x, y)) {
            View.main.display();
        } else if (View.options.difficultyDownArea.covers(x, y)) {
            View.options.changeDifficulty(false);
            View.options.animateLeftArrow();
        } else if (View.options.difficultyUpArea.covers(x, y)) {
            View.options.changeDifficulty(true);
            View.options.animateRightArrow();
        } else if (View.options.autoBuyFlagsArea.covers(x, y)) {
            View.options.switchAutoBuyFlags();
        } else if (View.options.switchGameTimerArea.covers(x, y)) {
            View.options.switchGameTimer();
        } else if (View.options.redThemeArea.covers(x, y)) {
            Theme.setTheme(Theme.USSR);
        } else if (View.options.greenThemeArea.covers(x, y)) {
            Theme.setTheme(Theme.MINT);
        } else if (View.options.blueThemeArea.covers(x, y)) {
            Theme.setTheme(Theme.SKY);
        }
    }

    @Override
    public void rightClick(int x, int y) {

    }

    @Override
    public void pressUp() {

    }

    @Override
    public void pressDown() {

    }

    @Override
    public void pressRight() {
        View.options.changeDifficulty(true);
        View.options.animateRightArrow();
    }

    @Override
    public void pressLeft() {
        View.options.changeDifficulty(false);
        View.options.animateLeftArrow();
    }

    @Override
    public void pressEnter() {

    }

    @Override
    public void pressPause() {

    }

    @Override
    public void pressSpace() {

    }

    @Override
    public void pressEsc() {
        View.main.display();
    }

    @Override
    public void pressOther() {

    }
}
