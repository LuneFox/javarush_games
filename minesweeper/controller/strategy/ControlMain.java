package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.View;

public class ControlMain implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.START).tryToPress(x, y)) {
            game.createGame();
            Screen.set(Screen.BOARD);
        } else if (View.BUTTONS_CACHE.get(Button.ButtonID.OPTIONS).tryToPress(x, y)) {
            Screen.set(Screen.OPTIONS);
        } else if (View.BUTTONS_CACHE.get(Button.ButtonID.ABOUT).tryToPress(x, y)) {
            Screen.set(Screen.ABOUT);
        } else if (View.BUTTONS_CACHE.get(Button.ButtonID.RECORDS).tryToPress(x, y)) {
            Screen.set(Screen.RECORDS);
        }
    }

    @Override
    public void pressEsc() {
        if (!game.isStopped) {
            Screen.set(Screen.BOARD);
        }
    }

}
