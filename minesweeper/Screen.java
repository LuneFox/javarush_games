package com.javarush.games.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Screen {
    MAIN_MENU, GAME_OVER, SHOP, BOARD, OPTIONS, ABOUT, ITEM_HELP, SCORE, RECORDS;

    private static final List<Screen> screens = new ArrayList<>(Arrays.asList(Screen.values()));

    public static void set(Screen screen){
        screens.remove(screen);
        screens.add(0, screen);
    }

    public static Screen get(){
        return screens.get(0);
    }
}
