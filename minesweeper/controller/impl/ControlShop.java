package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.model.Phase;

import static com.javarush.games.minesweeper.Util.inside;

public class ControlShop implements ControlStrategy {
    private static MinesweeperGame game;

    @Override
    public void leftClick(int x, int y) {
        Phase.getCurrentView().click(x, y, Controller.Click.LEFT);

        if (clickedOutsideShopWindow(x, y)) {
            Phase.setActive(Phase.BOARD);
        }
    }

    @Override
    public void pressSpace() {
        Phase.setActive(Phase.BOARD);
    }

    @Override
    public void pressEsc() {
        Phase.setActive(Phase.BOARD);
    }

    @Override
    @DeveloperOption
    public void pressUp() {
        game.getInventory().cheatMoreMoney();
    }

    @Override
    @DeveloperOption
    public void pressDown() {
        game.getShop().cheatMoreTools();
    }

    private boolean clickedOutsideShopWindow(int x, int y) {
        boolean horizontal = inside(x, 0, 9) || inside(x, 90, 99);
        boolean vertical = inside(y, 0, 10) || inside(y, 91, 99);
        return (horizontal || vertical);
    }

    public static void setGame(MinesweeperGame game) {
        ControlShop.game = game;
    }
}
