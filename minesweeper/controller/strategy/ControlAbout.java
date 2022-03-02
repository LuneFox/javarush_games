package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlAbout implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_BACK).tryToPress(x, y)) {
            Screen.set(Screen.MAIN);
        } else if (Screen.about.prevPageArrowArea.covers(x, y)) {
            Screen.about.prevPage();
        } else if (Screen.about.nextPageArrowArea.covers(x, y)) {
            Screen.about.nextPage();
        }
    }

    @Override
    public void pressRight() {
        Screen.about.nextPage();
    }

    @Override
    public void pressLeft() {
        Screen.about.prevPage();
    }

    @Override
    public void pressEsc() {
        Screen.set(Screen.MAIN);
    }
}
