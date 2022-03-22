package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.Cell;

public class ControlBoard implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        Phase.getCurrentView().click(x, y, Controller.Click.LEFT);
        if (checkGameOver()) return;

        int gridX = x / 10;
        int gridY = y / 10;
        Cell cell = game.fieldManager.getField().get()[gridY][gridX];
        if (!cell.isFlagged || game.shop.scanner.isActivated()) {
            game.fieldManager.openCell(gridX, gridY);
        }
        game.shop.deactivateExpiredItems();
    }

    @Override
    public void rightClick(int x, int y) {
        Phase.getCurrentView().click(x, y, Controller.Click.RIGHT);
        if (checkGameOver()) return;

        int gridX = x / 10;
        int gridY = y / 10;
        game.fieldManager.setFlag(gridX, gridY, true);              // works only on closed tiles
        game.fieldManager.openSurroundingCells(gridX, gridY);       // works only on open tiles
        game.shop.deactivateExpiredItems();
    }

    @Override
    @DeveloperOption
    public void pressLeft() {
        game.fieldManager.autoFlag();
    }

    @Override
    @DeveloperOption
    public void pressRight() {
        game.fieldManager.autoOpen();
    }

    @Override
    @DeveloperOption
    public void pressDown() {
        game.fieldManager.autoScan();
    }

    @Override
    @DeveloperOption
    public void pressUp() {
        game.fieldManager.skipEasyPart();
    }

    @Override
    public void pressSpace() {
        Phase.setActive(!game.isStopped ? Phase.SHOP : Phase.GAME_OVER);
    }

    @Override
    public void pressEsc() {
        Phase.setActive(game.isStopped ? Phase.GAME_OVER : Phase.MAIN);
    }

    @Override
    public void pressPause() {
        pressEsc();
    }

    @Override
    public void pressOther() {
        if (game.isStopped) {
            Phase.setActive(Phase.GAME_OVER);
        }
    }

    private boolean checkGameOver() {
        if (game.isStopped) {
            Phase.setActive(Phase.GAME_OVER);
            return true;
        }
        return false;
    }
}
