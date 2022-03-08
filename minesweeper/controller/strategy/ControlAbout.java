package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlAbout implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_CLOSE).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.MAIN);
        }
        Screen.about.pageSelector.checkLeftTouch(x, y);
    }

    @Override
    public void pressRight() {
        Screen.about.pageSelector.nextPage();
    }

    @Override
    public void pressLeft() {
        Screen.about.pageSelector.prevPage();
    }

    @Override
    public void pressEsc() {
        Screen.setActive(Screen.MAIN);
    }
}
