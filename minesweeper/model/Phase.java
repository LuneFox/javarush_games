package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.view.View;
import com.javarush.games.minesweeper.view.ViewFactory;

import java.util.*;

/**
 * Phase determines what the game is showing to the player right now.
 * Controls and views depend on what phase is current (at position 0).
 */

public enum Phase {
    ABOUT, BOARD, GAME_OVER, ITEM_HELP, MAIN, OPTIONS, RECORDS, SCORE, SHOP;
    private static MinesweeperGame game;

    private static List<Phase> PHASES;
    private static Map<Phase, View> PHASE_VIEW_MAP;
    private static View pendingView;
    private static View currentView;

    public static void setUp(MinesweeperGame game) {
        Phase.game = game;
        PHASES = new LinkedList<>(Arrays.asList(Phase.values()));
        PHASE_VIEW_MAP = new HashMap<>();
        ViewFactory viewFactory = new ViewFactory(game);
        PHASES.forEach(phase -> PHASE_VIEW_MAP.put(phase, viewFactory.createView(phase)));
    }

    // Phase at index 0 is considered active
    public static void setActive(Phase phase) {
        PHASES.remove(phase);
        PHASES.add(0, phase);
        pendingView = PHASE_VIEW_MAP.get(phase);
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
        game.setDisplayInterlace(false);
        PageSelector.allSelectors.forEach(PageSelector::setDefaultPage);
        final Inventory inventory = game.getPlayer().getInventory();
        inventory.snapDisplayMoney();
    }

    public static Phase getActive() {
        return PHASES.get(0);
    }

    public static View getCurrentView() {
        return currentView;
    }

    public static boolean isActive(Phase phase) {
        return (PHASES.get(0) == phase);
    }
}
