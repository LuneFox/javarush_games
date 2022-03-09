package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.Phase;

/**
 * Draws stuff on the screen.
 */

public class View {
    protected MinesweeperGame game = MinesweeperGame.getInstance();
    public Phase phase;

    public View(Phase phase) {
        this.phase = phase;
    }

    public void update() {
        PopUpMessage.drawMessage();
    }
}













































