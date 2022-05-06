package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.Click;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.InteractiveObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Draws stuff on the screen.
 */

public abstract class View {
    protected final MinesweeperGame game;
    private static int gameOverShowDelay;
    private final List<InteractiveObject> linkedObjects;

    public View(MinesweeperGame game) {
        this.linkedObjects = new ArrayList<>();
        this.game = game;
    }

    public void update() {
        PopUpMessage.drawInstance();
    }

    public void click(int x, int y, Click click) {
        for (InteractiveObject object : linkedObjects) {
            if (object.covers(x, y)) {
                object.click(x, y, click);
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

    public static void decreaseGameOverShowDelay() {
        View.gameOverShowDelay--;
    }
}