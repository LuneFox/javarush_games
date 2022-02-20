package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.View;

public class ControlGameOver implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.CLOSE).tryToPress(x, y)) {
            Screen.set(Screen.BOARD);
        } else if (View.BUTTONS_CACHE.get(Button.ButtonID.RETURN).tryToPress(x, y)) {
            Screen.set(Screen.MAIN);
        } else if (View.BUTTONS_CACHE.get(Button.ButtonID.AGAIN).tryToPress(x, y)) {
            game.createGame();
            Screen.set(Screen.BOARD);
        } else if (Screen.gameOver.scoreArea.covers(x, y)) {
            Screen.set(Screen.SCORE);
        }
    }

    @Override
    public void pressSpace() {
        Screen.set(Screen.SCORE);
    }

    @Override
    public void pressEsc() {
        Screen.set(Screen.BOARD);
    }

}
