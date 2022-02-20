package com.javarush.games.minesweeper;

import com.javarush.games.minesweeper.view.*;
import com.javarush.games.minesweeper.view.graphics.Button;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum Screen {
    MAIN, GAME_OVER, SHOP, BOARD, OPTIONS, ABOUT, ITEM_HELP, SCORE, RECORDS;

    private static final List<Screen> screens = new LinkedList<>(Arrays.asList(Screen.values()));
    public static final ViewAbout about = new ViewAbout();
    public static final ViewBoard board = new ViewBoard();
    public static final ViewGameOver gameOver = new ViewGameOver();
    public static final ViewItemHelp itemHelp = new ViewItemHelp();
    public static final ViewMain main = new ViewMain();
    public static final ViewOptions options = new ViewOptions();
    public static final ViewRecords records = new ViewRecords();
    public static final ViewScore score = new ViewScore();
    public static final ViewShop shop = new ViewShop();
    public static final View[] VIEWS = new View[]{about, board, gameOver, itemHelp, main, options, records, score, shop};
    private static View pendingView;
    private static View currentView = pendingView;
    public static boolean evenFrame;    // helps to animate shaking elements

    // Screen at index 0 is considered active
    public static void set(Screen screen) {
        screens.remove(screen);         // remove it from the list
        screens.add(0, screen);         // insert it at index 0
        for (View view : VIEWS) {
            if (view.screen == screen) {
                if (currentView == null) currentView = view;
                pendingView = view;
            }
        }
    }

    public static void update() {
        // Show pending view only after the button was fully animated, controls are disabled before that
        if (Button.pressedTime <= Button.POST_PRESS_DELAY) {
            currentView = pendingView;
        }
        currentView.update();
        evenFrame = !evenFrame;
    }

    // Get active screen
    public static Screen get() {
        return screens.get(0);
    }

    // Check if a screen is active
    public static boolean is(Screen screen) {
        return (screens.get(0) == screen);
    }
}
