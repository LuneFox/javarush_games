package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Screen;

import static com.javarush.games.minesweeper.Util.inside;

public class ControlShop implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (clickedOutsideShopWindow(x, y)) {
            Screen.set(Screen.BOARD);
            return;
        }
        game.shop.showCase.checkLeftTouch(x, y);
    }

    @Override
    public void rightClick(int x, int y) {
        game.shop.showCase.checkRightTouch(x, y);
    }

    @Override
    public void pressSpace() {
        Screen.set(Screen.BOARD);
    }

    @Override
    public void pressEsc() {
        Screen.set(Screen.BOARD);
    }

    private boolean clickedOutsideShopWindow(int x, int y) {
        boolean horizontal = inside(x, 0, 9) || inside(x, 90, 99);
        boolean vertical = inside(y, 0, 10) || inside(y, 91, 99);
        return (horizontal || vertical);
    }
}
