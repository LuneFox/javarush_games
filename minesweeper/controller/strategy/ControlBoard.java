package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.Cell;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.view.View;

public class ControlBoard implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (game.isStopped) {
            View.gameOver.display(game.lastResultIsVictory, 0);
            Screen.set(Screen.GAME_OVER);
            return;
        }
        Cell cell = game.field[y / 10][x / 10];
        if (!cell.isFlagged || game.shop.scanner.isActivated()) {
            game.openCell(x / 10, y / 10);
        }
        game.deactivateExpiredItems();
    }

    @Override
    public void rightClick(int x, int y) {
        if (game.isStopped) {
            View.gameOver.display(game.lastResultIsVictory, 0);
            Screen.set(Screen.GAME_OVER);
        } else { // only one will work - actions don't interfere
            game.setFlag(x / 10, y / 10, true);           // works only if tile is closed
            game.openRest(x / 10, y / 10);                // works only if tile is open
        }
        game.deactivateExpiredItems();
    }

    @Override
    public void pressUp() {

    }

    @Override
    public void pressDown() {

    }

    @Override
    public void pressRight() {

    }

    @Override
    public void pressLeft() {

    }

    @Override
    public void pressEnter() {

    }

    @Override
    public void pressPause() {

    }

    @Override
    public void pressSpace() {
        if (!game.isStopped) {
            View.shop.display();
        } else {
            View.gameOver.display(game.lastResultIsVictory, 0);
        }
    }

    @Override
    public void pressEsc() {
        if (game.isStopped) {
            View.gameOver.display(game.lastResultIsVictory, 0);
            Screen.set(Screen.GAME_OVER);
        } else {
            View.main.display();
        }
    }

    @Override
    public void pressOther() {
        if (game.isStopped) {
            View.gameOver.display(game.lastResultIsVictory, 0);
            Screen.set(Screen.GAME_OVER);
        }
    }
}
