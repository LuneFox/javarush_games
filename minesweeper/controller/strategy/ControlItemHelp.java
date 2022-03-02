package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlItemHelp implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_CONFIRM).tryToPress(x, y)) {
            Screen.set(Screen.SHOP);
        }
    }

    @Override
    public void pressEsc() {
        Screen.set(Screen.SHOP);
    }
}
