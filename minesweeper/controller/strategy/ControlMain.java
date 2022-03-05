package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlMain implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.MAIN_MENU_START).checkTouch(x, y)) {
            game.startNewGame();
            Screen.set(Screen.BOARD);
        } else if (Cache.get(Button.ButtonID.MAIN_MENU_OPTIONS).checkTouch(x, y)) {
            Screen.set(Screen.OPTIONS);
        } else if (Cache.get(Button.ButtonID.MAIN_MENU_ABOUT).checkTouch(x, y)) {
            Options.aboutPageSelector.reset();
            Screen.set(Screen.ABOUT);
        } else if (Cache.get(Button.ButtonID.MAIN_MENU_RECORDS).checkTouch(x, y)) {
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
