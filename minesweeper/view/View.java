package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Screen;

/**
 * Draws stuff on the screen.
 */

public class View {
    protected MinesweeperGame game = MinesweeperGame.getInstance();
    public Screen screen;

    public View(Screen screen) {
        this.screen = screen;
    }

    public void update() {
        PopUpMessage.drawMessage();
    }
}













































