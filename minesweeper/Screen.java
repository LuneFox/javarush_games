package com.javarush.games.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that keeps track of what screen is currently being used.
 * Screen at the top of the list (at position 0) decides what happens in the game.
 */

public class Screen { // TODO: Make screen an ENUM, don't need inner enum, there's a finite number of screens
    private final ScreenType screenType;
    private static final ArrayList<Screen> screens = new ArrayList<>();

    public enum ScreenType {
        MAIN_MENU, GAME_OVER, SHOP, BOARD, OPTIONS, ABOUT, ITEM_HELP, SCORE, RECORDS
    }

    static {
        screens.addAll(Arrays.asList(
                new Screen(ScreenType.MAIN_MENU),
                new Screen(ScreenType.BOARD),
                new Screen(ScreenType.GAME_OVER),
                new Screen(ScreenType.SHOP),
                new Screen(ScreenType.OPTIONS),
                new Screen(ScreenType.ABOUT),
                new Screen(ScreenType.ITEM_HELP),
                new Screen(ScreenType.SCORE),
                new Screen(ScreenType.RECORDS)
        ));
    }

    private Screen(ScreenType screenType) {
        this.screenType = screenType;
    }

    public static void setType(ScreenType screenType) { // bringing active screen to the top of the list
        Screen screen = null;
        for (Screen s : screens) if (s.screenType == screenType) screen = s;
        screens.remove(screen);
        screens.add(0, screen);
    }

    public static ScreenType getType() { // returns current active screen type
        return screens.get(0).screenType;
    }
}
