package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.PageSelector;
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
    private static Map<Phase, View> viewMap;
    private static View pendingView;
    private static View currentView;
    private static List<Phase> PHASES;
    private static final int ACTIVE_PHASE_POSITION = 0;

    public static void setUp(MinesweeperGame game) {
        Phase.game = game;
        PHASES = new LinkedList<>(Arrays.asList(Phase.values()));
        viewMap = new HashMap<>();
        ViewFactory viewFactory = new ViewFactory(game);
        PHASES.forEach(phase -> viewMap.put(phase, viewFactory.createView(phase)));
    }

    public static void setActive(Phase phase) {
        PHASES.remove(phase);
        PHASES.add(ACTIVE_PHASE_POSITION, phase);
        pendingView = viewMap.get(phase);
    }

    public static void updateView() {
        changeCurrentViewIfNeeded();
        currentView.update();
    }

    private static void changeCurrentViewIfNeeded() {
        if (Button.isAnimationFinished() && isAnotherViewPending()) {
            changeCurrentView();
        } else {
            Button.continueAnimation();
        }
    }

    private static boolean isAnotherViewPending() {
        return pendingView != currentView;
    }

    private static void changeCurrentView() {
        currentView = pendingView;
        onViewChange();
    }

    private static void onViewChange() {
        game.setDisplayInterlace(false);
        game.getPlayer().getInventory().skipMoneyAnimation();
        PageSelector.allSelectors.forEach(PageSelector::setDefaultPage);
    }

    public static Phase getActive() {
        return PHASES.get(ACTIVE_PHASE_POSITION);
    }

    public static boolean isActive(Phase phase) {
        return (PHASES.get(ACTIVE_PHASE_POSITION) == phase);
    }

    public static View getCurrentView() {
        return currentView;
    }
}
