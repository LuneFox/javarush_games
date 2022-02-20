package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.View;

public class ControlRecords implements ControlStrategy {
    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.BACK).tryToPress(x, y)) {
            Screen.set(Screen.MAIN);
        }
    }

    @Override
    public void pressEsc() {
        Screen.set(Screen.MAIN);
    }

}
