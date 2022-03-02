package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlMain implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.MAIN_MENU_START).tryToPress(x, y)) {
            game.startNewGame();
            Screen.set(Screen.BOARD);
        } else if (Cache.get(Button.ButtonID.MAIN_MENU_OPTIONS).tryToPress(x, y)) {
            Screen.set(Screen.OPTIONS);
        } else if (Cache.get(Button.ButtonID.MAIN_MENU_ABOUT).tryToPress(x, y)) {
            Screen.set(Screen.ABOUT);
        } else if (Cache.get(Button.ButtonID.MAIN_MENU_RECORDS).tryToPress(x, y)) {
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
