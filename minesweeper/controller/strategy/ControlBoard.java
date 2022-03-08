package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Screen;

public class ControlBoard implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        int gridX = x / 10;
        int gridY = y / 10;

        if (game.isStopped) {
            Screen.setActive(Screen.GAME_OVER);
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
        int gridX = x / 10;
        int gridY = y / 10;

        if (game.isStopped) {
            Screen.setActive(Screen.GAME_OVER);
            return;
        }
        game.setFlag(gridX, gridY, true);  // works only on closed tiles
        game.openSurroundingCells(gridX, gridY);       // works only on open tiles
        game.shop.deactivateExpiredItems();
    }

    @Override
    public void pressSpace() {
        if (!game.isStopped) {
            Screen.setActive(Screen.SHOP);
        } else {
            Screen.setActive(Screen.GAME_OVER);
        }
    }

    @Override
    public void pressEsc() {
        if (game.isStopped) {
            Screen.setActive(Screen.GAME_OVER);
        } else {
            Screen.setActive(Screen.MAIN);
        }
    }

    @Override
    public void pressOther() {
        if (game.isStopped) {
            Screen.setActive(Screen.GAME_OVER);
        }
    }
}
