package com.javarush.games.minesweeper.view;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.GameObject;
import com.javarush.games.minesweeper.model.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * Draws stuff on the screen.
 */

public abstract class View {
    protected MinesweeperGame game = MinesweeperGame.getInstance();
    public Phase phase;
    public List<GameObject> linkedObjects;  // list of objects that can be interacted

    public View(Phase phase) {
        this.phase = phase;
        this.linkedObjects = new ArrayList<>();
    }

    public void update() {
        PopUpMessage.drawMessage();
    }

    public void click(int x, int y, Controller.Click click) {
        for (GameObject object : linkedObjects) {
            if (object.tryClick(x, y, click)) {
                return; // click only the first one if they accidentally got layered
            }
        }
    }
}