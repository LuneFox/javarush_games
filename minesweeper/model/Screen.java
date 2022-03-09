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
 * Screen determines what the game is showing to the player right now.
 * Controls and views depend on what screen is at position 0.
 */

public enum Screen {
    ABOUT, BOARD, GAME_OVER, ITEM_HELP, MAIN, OPTIONS, RECORDS, SCORE, SHOP;
    private static final List<Screen> screens;
    private static final List<View> views;
    private static View pendingView;
    private static View currentView;

    static {
        screens = new LinkedList<>(Arrays.asList(Screen.values()));
        views = new ArrayList<>();
        ViewFactory factory = new ViewFactory();
        screens.forEach(screen -> views.add(factory.createView(screen)));
    }

    // Screen at index 0 is considered active
    public static void setActive(Screen screen) {
        // Put on "top"
        screens.remove(screen);
        screens.add(0, screen);
        // Pending view is a view linked to given screen
        views.stream().filter(view -> view.screen == screen).forEach(view -> pendingView = view);
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
        if (game.shop.showCase != null) {
            game.shop.showCase.header.setDisplayMoney(game.player.inventory.money);
        }
    }

    public static Screen getActive() {
        return screens.get(0);
    }

    public static boolean isActive(Screen screen) {
        return (screens.get(0) == screen);
    }
}
