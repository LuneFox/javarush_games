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
    public static void setActive(Screen screen) {
        // Put on "top"
        screens.remove(screen);
        screens.add(0, screen);
        // Pending view is a view linked to given screen
        Arrays.stream(views)
                .filter(view -> view.screen == screen)
                .forEach(view -> pendingView = view);
    }

    public static void updateView() {
        // Give time for buttons to animate before changing views
        if (Button.pressedTime <= Button.POST_PRESS_DELAY) {
            currentView = pendingView;
        } else {
            Button.pressedTime--;
        }

        currentView.update();
    }

    public static Screen getActive() {
        return screens.get(0);
    }

    public static boolean isActive(Screen screen) {
        return (screens.get(0) == screen);
    }
}
