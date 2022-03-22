package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
import com.javarush.games.minesweeper.view.*;
import com.javarush.games.minesweeper.gui.interactive.Button;

import java.util.*;

/**
 * Phase determines what the game is showing to the player right now.
 * Controls and views depend on what phase is current (at position 0).
 */

public enum Phase {
    ABOUT, BOARD, GAME_OVER, ITEM_HELP, MAIN, OPTIONS, RECORDS, SCORE, SHOP;
    private static final List<Phase> PHASES;
    private static final Map<Phase, View> PHASE_VIEW_MAP;
    private static View pendingView;
    private static View currentView;

    static {
        PHASES = new LinkedList<>(Arrays.asList(Phase.values()));
        PHASE_VIEW_MAP = new HashMap<>();
        ViewFactory factory = new ViewFactory();
        PHASES.forEach(phase -> PHASE_VIEW_MAP.put(phase, factory.createView(phase)));
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
        MinesweeperGame game = MinesweeperGame.getInstance();
        game.display.setInterlaceEnabled(false);
        PageSelector.allSelectors.forEach(PageSelector::reset);
        game.player.inventory.displayMoney = game.player.inventory.money;
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
