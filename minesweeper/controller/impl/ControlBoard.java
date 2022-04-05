package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.Cell;

public class ControlBoard implements ControlStrategy {
    private static MinesweeperGame game;
    @Override
    public void leftClick(int x, int y) {
        Phase.getCurrentView().click(x, y, Controller.Click.LEFT);
        if (checkGameOver()) return;

        int gridX = x / 10;
        int gridY = y / 10;

        Cell cell = game.getCell(gridX, gridY);

        if (cell.isShop()) {
            Phase.setActive(Phase.SHOP);
            return;
        }

        if (!cell.isFlagged() || game.getShop().getScanner().isActivated()) {
            game.openCell(gridX, gridY);
        }
        game.getShop().checkExpiredItems();
    }

    @Override
    public void rightClick(int x, int y) {
        Phase.getCurrentView().click(x, y, Controller.Click.RIGHT);
        if (checkGameOver()) return;

        int gridX = x / 10;
        int gridY = y / 10;

        Cell cell = game.getCell(gridX, gridY);

        if (cell.isShop()) {
            PopUpMessage.show("двери магазина");
            return;
        }

        game.swapFlag(gridX, gridY);              // works only on closed tiles
        game.openSurrounding(gridX, gridY);  // works only on open tiles
        game.getShop().checkExpiredItems();
    }

    @Override
    @DeveloperOption
    public void pressLeft() {
        game.autoFlag();
    }

    @Override
    @DeveloperOption
    public void pressRight() {
        game.autoOpen();
    }

    @Override
    @DeveloperOption
    public void pressDown() {
        game.autoScan();
    }

    @Override
    @DeveloperOption
    public void pressUp() {
        game.skipEasy();
    }

    @Override
    public void pressSpace() {
        Phase.setActive(!game.isStopped() ? Phase.SHOP : Phase.GAME_OVER);
    }

    @Override
    public void pressEsc() {
        Phase.setActive(game.isStopped() ? Phase.GAME_OVER : Phase.MAIN);
    }

    @Override
    public void pressPause() {
        pressEsc();
    }

    @Override
    public void pressOther() {
        if (game.isStopped()) {
            Phase.setActive(Phase.GAME_OVER);
        }
    }

    private boolean checkGameOver() {
        if (game.isStopped()) {
            Phase.setActive(Phase.GAME_OVER);
            return true;
        }
        return false;
    }

    public static void setGame(MinesweeperGame game) {
        ControlBoard.game = game;
    }
}
