package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * Draws stuff on the screen.
 */

public abstract class View {
    private static int gameOverShowDelay;
    protected MinesweeperGame game = MinesweeperGame.getInstance();
    public Phase phase;
    public List<InteractiveObject> linkedObjects;  // list of objects that can be interacted

    public View(Phase phase) {
        this.phase = phase;
        this.linkedObjects = new ArrayList<>();
    }

    public void update() {
        PopUpMessage.drawMessage();
    }

    public void click(int x, int y, Controller.Click click) {
        for (InteractiveObject object : linkedObjects) {
            if (object.tryClick(x, y, click)) {
                return; // click only the first one if they accidentally got layered
            }
        }
    }

    public static int getGameOverShowDelay() {
        return gameOverShowDelay;
    }

    public static void setGameOverShowDelay(int gameOverShowDelay) {
        View.gameOverShowDelay = gameOverShowDelay;
    }
}