package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlAbout implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_BACK).checkLeftTouch(x, y)) {
            Screen.set(Screen.MAIN);
        }
        Options.aboutPageSelector.checkLeftTouch(x, y);
    }

    @Override
    public void pressRight() {
        Options.aboutPageSelector.nextPage();
    }

    @Override
    public void pressLeft() {
        Options.aboutPageSelector.prevPage();
    }

    @Override
    public void pressEsc() {
        Screen.set(Screen.MAIN);
    }
}
