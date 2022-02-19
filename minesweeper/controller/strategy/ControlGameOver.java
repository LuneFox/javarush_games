package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.View;

public class ControlGameOver implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.CLOSE).covers(x, y)) {
            View.board.display();
        } else if (View.BUTTONS_CACHE.get(Button.ButtonID.RETURN).covers(x, y)) {
            View.main.display();
        } else if (View.BUTTONS_CACHE.get(Button.ButtonID.AGAIN).covers(x, y)) {
            game.createGame();
        } else if (View.gameOver.scoreArea.covers(x, y)) {
            View.score.display();
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
        View.score.display();
    }

    @Override
    public void pressEsc() {
        View.board.display();
    }

    @Override
    public void pressOther() {

    }
}
