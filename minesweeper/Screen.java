package com.javarush.games.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Screen {
    MAIN_MENU, GAME_OVER, SHOP, BOARD, OPTIONS, ABOUT, ITEM_HELP, SCORE, RECORDS;

    private static final List<Screen> screens = new ArrayList<>(Arrays.asList(Screen.values()));

    // Screen at index 0 is considered active
    public static void set(Screen screen) {
        screens.remove(screen);         // remove it from the list
        screens.add(0, screen);    // insert it at index 0
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
