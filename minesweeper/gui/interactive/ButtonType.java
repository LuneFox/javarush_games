package com.javarush.games.minesweeper.gui.interactive;

public enum ButtonType {
    GENERAL_CONFIRM(61, 88, 36, 9, "ясно"),
    GENERAL_CLOSE(88, 2, 0, 0, "x"),

    MAIN_MENU_START(61, 88, 36, 9, "старт"),
    MAIN_MENU_NEW_RESTART(61, 88, 36, 9, "заново"),
    MAIN_MENU_OPTIONS(61, 64, 36, 9, "опции"),
    MAIN_MENU_ABOUT(61, 76, 36, 9, "об игре"),
    MAIN_MENU_RECORDS(2, 88, 0, 0, "рекорды"),

    GAME_OVER_HIDE(73, 35, 0, 0, "x"),
    GAME_OVER_QUESTION(17, 56, 0, 0, "?"),
    GAME_OVER_AGAIN(57, 69, 0, 0, "снова"),
    GAME_OVER_RETURN(15, 69, 0, 0, "меню");

    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final String label;

    ButtonType(int x, int y, int width, int height, String label) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
    }
}
