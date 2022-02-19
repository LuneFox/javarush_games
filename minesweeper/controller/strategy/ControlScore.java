package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.graphics.Button;
import com.javarush.games.minesweeper.view.View;

public class ControlScore implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.CONFIRM).covers(x, y)) {
            View.board.refresh();
            View.gameOver.display(game.lastResultIsVictory, 0);
        }
    }

    @Override
    public void rightClick(int x, int y) {

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
    public void pressSpace() {
        View.gameOver.display(game.lastResultIsVictory, 0);
        Screen.set(Screen.GAME_OVER);
    }

    @Override
    public void pressEsc() {
        View.gameOver.display(game.lastResultIsVictory, 0);
        Screen.set(Screen.GAME_OVER);
    }

    @Override
    public void pressOther() {

    }
}
