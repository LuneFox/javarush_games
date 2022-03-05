package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlRecords implements ControlStrategy {
    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_BACK).checkLeftTouch(x, y)) {
            Screen.set(Screen.MAIN);
        }
    }

    @Override
    public void pressEsc() {
        Screen.set(Screen.MAIN);
    }

}
