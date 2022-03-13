package com.javarush.games.minesweeper.controller;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.Cell;

public class ControlBoard implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        Phase.getCurrentView().click(x, y, Controller.Click.LEFT);

        int gridX = x / 10;
        int gridY = y / 10;

        if (game.isStopped) {
            Phase.setActive(Phase.GAME_OVER);
            return;
        }
        Cell cell = game.field.get()[gridY][gridX];
        if (!cell.isFlagged || game.shop.scanner.isActivated()) {
            game.openCell(gridX, gridY);
        }
        game.shop.deactivateExpiredItems();
    }

    @Override
    public void rightClick(int x, int y) {
        Phase.getCurrentView().click(x, y, Controller.Click.RIGHT);

        int gridX = x / 10;
        int gridY = y / 10;

        if (game.isStopped) {
            Phase.setActive(Phase.GAME_OVER);
            return;
        }
        game.setFlag(gridX, gridY, true);              // works only on closed tiles
        game.openSurroundingCells(gridX, gridY);       // works only on open tiles
        game.shop.deactivateExpiredItems();
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
        game.skipEasyPart();
    }

    @Override
    public void pressSpace() {
        if (!game.isStopped) {
            Phase.setActive(Phase.SHOP);
        } else {
            Phase.setActive(Phase.GAME_OVER);
        }
    }

    @Override
    public void pressEsc() {
        if (game.isStopped) {
            Phase.setActive(Phase.GAME_OVER);
        } else {
            Phase.setActive(Phase.MAIN);
        }
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
}
