package com.javarush.games.minesweeper;

import java.util.ArrayList;

public class Screen {
    private ScreenType screenType;
    private static ArrayList<Screen> screens = new ArrayList<>();

    public enum ScreenType {
        MAIN_MENU, GAME_OVER, SHOP, GAME_BOARD, OPTIONS, ABOUT, ITEM_HELP, SCORE_DETAIL, RECORDS
    }

    static {
        screens.add(new Screen(ScreenType.MAIN_MENU));
        screens.add(new Screen(ScreenType.GAME_BOARD));
        screens.add(new Screen(ScreenType.GAME_OVER));
        screens.add(new Screen(ScreenType.SHOP));
        screens.add(new Screen(ScreenType.OPTIONS));
        screens.add(new Screen(ScreenType.ABOUT));
        screens.add(new Screen(ScreenType.ITEM_HELP));
        screens.add(new Screen(ScreenType.SCORE_DETAIL));
        screens.add(new Screen(ScreenType.RECORDS));
    }

    public static void set(ScreenType screenType) { // bringing active screen to the top of the list
        Screen screen = null;
        for (Screen s : screens) {
            if (s.screenType == screenType) {
                screen = s;
            }
        }
        screens.remove(screen);
        screens.add(0, screen);
    }

    public static ScreenType get() { // returns current active screen type
        return screens.get(0).screenType;
    }

    private Screen(ScreenType screenType) {
        this.screenType = screenType;
    }
}
