package com.javarush.games.minesweeper;

import java.util.ArrayList;

public class Screen {
    private final ScreenType screenType;
    private static final ArrayList<Screen> screens = new ArrayList<>();

    public enum ScreenType {
        MAIN_MENU, GAME_OVER, SHOP, GAME_BOARD, OPTIONS, ABOUT, ITEM_HELP, SCORE, RECORDS
    }

    static {
        screens.add(new Screen(ScreenType.MAIN_MENU));
        screens.add(new Screen(ScreenType.GAME_BOARD));
        screens.add(new Screen(ScreenType.GAME_OVER));
        screens.add(new Screen(ScreenType.SHOP));
        screens.add(new Screen(ScreenType.OPTIONS));
        screens.add(new Screen(ScreenType.ABOUT));
        screens.add(new Screen(ScreenType.ITEM_HELP));
        screens.add(new Screen(ScreenType.SCORE));
        screens.add(new Screen(ScreenType.RECORDS));
    }

    private Screen(ScreenType screenType) {
        this.screenType = screenType;
    }

    public static void setType(ScreenType screenType) { // bringing active screen to the top of the list
        Screen screen = null;
        for (Screen s : screens) {
            if (s.screenType == screenType) {
                screen = s;
            }
        }
        screens.remove(screen);
        screens.add(0, screen);
    }

    public static ScreenType getType() { // returns current active screen type
        return screens.get(0).screenType;
    }
}
