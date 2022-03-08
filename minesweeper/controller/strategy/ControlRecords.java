package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlRecords implements ControlStrategy {
    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GENERAL_CLOSE).checkLeftTouch(x, y)) {
            Screen.setActive(Screen.MAIN);
        }
    }

    @Override
    public void pressEsc() {
        Screen.setActive(Screen.MAIN);
    }

}
