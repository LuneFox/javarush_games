package com.javarush.games.minesweeper.controller.strategy;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.view.graphics.Button;
import com.javarush.games.minesweeper.view.View;

public class ControlScore implements ControlStrategy {
    final private MinesweeperGame game = MinesweeperGame.getInstance();

    @Override
    public void leftClick(int x, int y) {
        if (View.BUTTONS_CACHE.get(Button.ButtonID.CONFIRM).tryToPress(x, y)) {
            Screen.board.update();
            Screen.gameOver.setShowDelay(0);
            Screen.set(Screen.GAME_OVER);
        }
    }

    @Override
    public void pressSpace() {
        Screen.board.update();
        Screen.gameOver.setShowDelay(0);
        Screen.set(Screen.GAME_OVER);
    }

    @Override
    public void pressEsc() {
        Screen.board.update();
        Screen.gameOver.setShowDelay(0);
        Screen.set(Screen.GAME_OVER);
    }
}
