package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.view.*;
import com.javarush.games.minesweeper.view.graphics.Button;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum Screen {
    MAIN, GAME_OVER, SHOP, BOARD, OPTIONS, ABOUT, ITEM_HELP, SCORE, RECORDS;

    public static final ViewAbout about = new ViewAbout();
    public static final ViewBoard board = new ViewBoard();
    public static final ViewGameOver gameOver = new ViewGameOver();
    public static final ViewItemHelp itemHelp = new ViewItemHelp();
    public static final ViewMain main = new ViewMain();
    public static final ViewOptions options = new ViewOptions();
    public static final ViewRecords records = new ViewRecords();
    public static final ViewScore score = new ViewScore();
    public static final ViewShop shop = new ViewShop();
    public static final View[] views = new View[]{about, board, gameOver, itemHelp, main, options, records, score, shop};
    private static final List<Screen> screens = new LinkedList<>(Arrays.asList(Screen.values()));
    private static View pendingView;
    private static View currentView = main;


    // Screen at index 0 is considered active
    public static void set(Screen screen) {
        screens.remove(screen);         // remove it from the list
        screens.add(0, screen);         // insert it at index 0
        // Pending view is a view that is associated with this screen
        Arrays.stream(views).filter(view -> view.screen == screen).forEach(view -> pendingView = view);
    }

    public static void updateView() {
        if (Button.pressedTime > Button.POST_PRESS_DELAY) Button.pressedTime--;
        if (Button.pressedTime <= Button.POST_PRESS_DELAY) { // when the button is done animating, apply pending view
            currentView = pendingView;
        }
        currentView.update(); // keeps updating previous view if the button isn't done animating
    }

    // Get active screen
    public static Screen get() {
        return screens.get(0);
    }

    // Check if a screen is active
    public static boolean is(Screen screen) {
        return (screens.get(0) == screen);
    }

    public static void setCurrentView(View currentView) {
        Screen.currentView = currentView;
    }
}
