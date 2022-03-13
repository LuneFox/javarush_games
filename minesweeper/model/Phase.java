package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
import com.javarush.games.minesweeper.view.*;
import com.javarush.games.minesweeper.gui.interactive.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Phase determines what the game is showing to the player right now.
 * Controls and views depend on what phase is current (at position 0).
 */

public enum Phase {
    ABOUT, BOARD, GAME_OVER, ITEM_HELP, MAIN, OPTIONS, RECORDS, SCORE, SHOP;
    private static final List<Phase> phases;
    private static final List<View> views;
    private static View pendingView;
    private static View currentView;

    static {
        phases = new LinkedList<>(Arrays.asList(Phase.values()));
        views = new ArrayList<>();
        ViewFactory factory = new ViewFactory();
        phases.forEach(screen -> views.add(factory.createView(screen)));
    }

    // Phase at index 0 is considered active
    public static void setActive(Phase phase) {
        phases.remove(phase);                         // place phase at position 0 = make active
        phases.add(0, phase);
        views.stream()                                // search views
                .filter(view -> view.phase == phase)  // find view for this phase
                .forEach(view -> pendingView = view); // register this view as pending
    }

    public static void updateView() {
        // Give time for buttons to animate before changing views
        if (Button.pressedTime <= Button.POST_PRESS_DELAY) {
            if (currentView != pendingView) {
                onViewChange();
            }
            currentView = pendingView;
        } else {
            Button.pressedTime--;
        }
        currentView.update();
    }

    private static void onViewChange() {
        // Things that happen right before the pending view is applied
        MinesweeperGame game = MinesweeperGame.getInstance();
        game.display.setInterlaceEnabled(false);
        PageSelector.allSelectors.forEach(PageSelector::reset);
        game.player.inventory.displayMoney = game.player.inventory.money;
    }

    public static Phase getActive() {
        return phases.get(0);
    }

    public static View getCurrentView() {
        return currentView;
    }

    public static boolean isActive(Phase phase) {
        return (phases.get(0) == phase);
    }
}