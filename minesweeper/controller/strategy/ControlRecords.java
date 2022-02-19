package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.View;

public class ControlRecords implements ControlStrategy {
    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.BACK).covers(x, y)) {
            View.main.display();
        }
    }

    @Override
    public void pressEsc() {
        View.main.display();
    }

}
