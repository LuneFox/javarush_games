package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.InteractiveObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Draws stuff on the screen.
 */

public abstract class View {
    protected static final MinesweeperGame game = MinesweeperGame.getInstance();
    private static int gameOverShowDelay;
    private final List<InteractiveObject> linkedObjects;

    public View() {
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

    public void linkObject(InteractiveObject interactiveObject) {
        linkedObjects.add(interactiveObject);
    }

    public static int getGameOverShowDelay() {
        return gameOverShowDelay;
    }

    public static void setGameOverShowDelay(int gameOverShowDelay) {
        View.gameOverShowDelay = gameOverShowDelay;
    }
}