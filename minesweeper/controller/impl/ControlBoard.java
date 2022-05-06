package com.javarush.games.minesweeper.controller.impl;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.ControlStrategy;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.Cell;

public class ControlBoard implements ControlStrategy {
    private static MinesweeperGame game;

    @Override
    public void leftClick(int x, int y) {
        Phase.leftClickOnCurrentView(x, y);

        if (game.isStopped()) {
            Phase.setActive(Phase.GAME_OVER);
            return;
        }

        Cell cell = game.getCellByCoordinates(x, y);

        if (cell.isShop()) {
            Phase.setActive(Phase.SHOP);
            return;
        }

        if (game.isScannerOrBombActivated()) {
            game.useItem(cell);
            game.checkExpiredItems();
            return;
        }

        game.open(cell);

        game.checkExpiredItems();
    }

    @Override
    public void rightClick(int x, int y) {
        Phase.rightClickOnCurrentView(x, y);

        if (game.isStopped()) {
            Phase.setActive(Phase.GAME_OVER);

            return;
        }

        Cell cell = game.getCellByCoordinates(x, y);

        if (cell.isShop()) {
            PopUpMessage.show("двери магазина");
            return;
        }

        game.swapFlag(cell);         // works only on closed cells
        game.openSurrounding(cell);  // works only on open cells

        game.checkExpiredItems();
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
        game.autoSolve();
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

    public static void setGame(MinesweeperGame game) {
        ControlBoard.game = game;
    }
}
