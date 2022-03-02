package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.graphics.Cache;

public class ControlGameOver implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (Cache.get(Button.ButtonID.GAME_OVER_HIDE).tryToPress(x, y)) {
            Screen.set(Screen.BOARD);
        } else if (Cache.get(Button.ButtonID.GAME_OVER_RETURN).tryToPress(x, y)) {
            Screen.set(Screen.MAIN);
        } else if (Cache.get(Button.ButtonID.GAME_OVER_AGAIN).tryToPress(x, y)) {
            game.startNewGame();
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
