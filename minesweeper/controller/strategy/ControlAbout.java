package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.View;

public class ControlAbout implements ControlStrategy {

    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.BACK).covers(x, y)) {
            View.main.display();
        } else if (View.about.prevPageArrowArea.covers(x, y)) {
            View.about.prevPage();
            View.options.animateLeftArrow();
        } else if (View.about.nextPageArrowArea.covers(x, y)) {
            View.about.nextPage();
            View.options.animateRightArrow();
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
        View.options.changeDifficulty(false);
        View.options.animateLeftArrow();
    }

    @Override
    public void pressLeft() {
        View.about.prevPage();
        View.options.animateLeftArrow();
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
