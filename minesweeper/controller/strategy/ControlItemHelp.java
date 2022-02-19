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

    }

    @Override
    public void pressLeft() {

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
        View.board.display();
        View.shop.display();
    }

    @Override
    public void pressOther() {

    }
}
