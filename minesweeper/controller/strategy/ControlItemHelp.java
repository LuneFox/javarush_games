package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.View;

public class ControlItemHelp implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.CONFIRM).covers(x, y)) {
            View.board.refresh();
            View.shop.display();
        }
    }

    @Override
    public void pressEsc() {
        View.board.display();
        View.shop.display();
    }
}
