package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.View;

public class ControlMain implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.START).covers(x, y)) {
            game.createGame();
        } else if (View.BUTTONS_CACHE.get(Button.ButtonID.OPTIONS).covers(x, y)) {
            View.options.display();
        } else if (View.BUTTONS_CACHE.get(Button.ButtonID.ABOUT).covers(x, y)) {
            View.about.display();
        } else if (View.BUTTONS_CACHE.get(Button.ButtonID.RECORDS).covers(x, y)) {
            View.records.display();
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
        if (!game.isStopped) {
            View.board.display();
        }
    }

    @Override
    public void pressOther() {

    }
}
