package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.graphics.Button;
import com.javarush.games.minesweeper.view.View;

public class ControlRecords implements ControlStrategy {
    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.BACK).covers(x, y)) {
            View.main.display();
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
    public void pressSpace() {

    }

    @Override
    public void pressEsc() {
        View.main.display();
    }

    @Override
    public void pressOther() {

    }
}
