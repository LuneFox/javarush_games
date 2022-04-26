package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Theme configurator with all variable colors.
 */

public enum Theme {
    MAIN_MENU_BG(new Color[]{
            Color.BROWN,
            Color.MEDIUMSEAGREEN,
            Color.ROYALBLUE,
            Color.SIENNA
    }),
    MAIN_MENU_BORDER(new Color[]{
            Color.DARKRED,
            Color.DARKGREEN,
            Color.STEELBLUE,
            Color.SADDLEBROWN
    }),
    MAIN_MENU_QUOTE_FRONT(new Color[]{
            Color.SALMON,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE,
            Color.NAVAJOWHITE
    }),
    TEXT_SHADOW(new Color[]{
            Color.DARKRED,
            Color.FORESTGREEN,
            Color.DIMGRAY,
            Color.SADDLEBROWN
    }),
    MAIN_MENU_LABEL(new Color[]{
            Color.DARKRED,
            Color.FORESTGREEN,
            Color.CORNFLOWERBLUE,
            Color.PERU
    }),
    BUTTON_BG(new Color[]{
            Color.DARKRED,
            Color.DARKGREEN,
            Color.STEELBLUE,
            Color.DIMGREY
    }),
    BUTTON_BORDER(new Color[]{
            Color.SALMON,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE,
            Color.DARKGREY
    }),
    LABEL(new Color[]{
            Color.YELLOW,
            Color.YELLOW,
            Color.YELLOW,
            Color.KHAKI
    }),
    SHOP_HEADER_FOOTER(new Color[]{
            Color.MAROON,
            Color.MEDIUMSEAGREEN,
            Color.ROYALBLUE,
            Color.SIENNA
    }),
    SHOP_BORDER(new Color[]{
            Color.SALMON,
            Color.DARKGREEN,
            Color.DODGERBLUE,
            Color.DARKGREY
    }),
    SHOP_BG(new Color[]{
            Color.BROWN,
            Color.DARKSLATEGRAY,
            Color.CORNFLOWERBLUE,
            Color.SANDYBROWN
    }),
    SHOP_TITLE(new Color[]{
            Color.SALMON,
            Color.SLATEGRAY,
            Color.LIGHTSKYBLUE,
            Color.LEMONCHIFFON
    }),
    SHOP_SCORE(new Color[]{
            Color.SALMON,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE,
            Color.GAINSBORO
    }),
    SHOP_MOVES(new Color[]{
            Color.INDIANRED,
            Color.PALEGREEN,
            Color.CORNFLOWERBLUE,
            Color.LIGHTGREY
    }),
    SHOP_ITEM_BG(new Color[]{
            Color.DARKSALMON,
            Color.SEAGREEN,
            Color.STEELBLUE,
            Color.SILVER
    }),
    SHOP_ITEM_FRAME_AVAILABLE(new Color[]{
            Color.ANTIQUEWHITE,
            Color.LIGHTGREEN,
            Color.LIGHTBLUE,
            Color.ANTIQUEWHITE,
    }),
    SHOP_SIGN_NO(new Color[]{
            Color.ANTIQUEWHITE,
            Color.PINK,
            Color.PINK,
            Color.ANTIQUEWHITE
    }),
    CELL_LIGHT(new Color[]{
            Color.BLANCHEDALMOND,
            Color.HONEYDEW,
            Color.LIGHTBLUE,
            Color.IVORY,
    }),
    CELL_BG_DOWN(new Color[]{
            Color.SANDYBROWN,
            Color.DARKSEAGREEN,
            Color.CORNFLOWERBLUE,
            Color.TAN
    }),
    CELL_BG_UP(new Color[]{
            Color.BURLYWOOD,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE,
            Color.BISQUE,
    }),
    CELL_SHADOW(new Color[]{
            Color.SADDLEBROWN,
            Color.SEAGREEN,
            Color.STEELBLUE,
            Color.PERU
    }),
    CELL_SCANNED(new Color[]{
            Color.ORANGE,
            Color.MEDIUMSPRINGGREEN,
            Color.DEEPSKYBLUE,
            Color.ORANGE,
    }),
    FLAG_LIGHT(new Color[]{
            Color.RED,
            Color.LIMEGREEN,
            Color.DARKTURQUOISE,
            Color.MEDIUMPURPLE
    }),
    FLAG_DARK(new Color[]{
            Color.DARKRED,
            Color.FORESTGREEN,
            Color.DARKCYAN,
            Color.SLATEBLUE
    }),
    ;

    private static final int THEMES_COUNT = MAIN_MENU_BG.colors.length;
    private static final Map<Integer, String> NAMES;
    private static int currentNumber;
    private final Color[] colors;

    static {
        NAMES = new HashMap<>();
        NAMES.put(0, "СССР");
        NAMES.put(1, "МЯТА");
        NAMES.put(2, "НЕБО");
        NAMES.put(3, "ДЮНА");
        currentNumber = 3;
    }

    Theme(Color[] colors) {
        this.colors = colors;
    }

    public Color getColor() {
        if (this.colors.length != THEMES_COUNT) return Color.WHITE;
        return this.colors[currentNumber];
    }

    public static void set(int themeNumber, MinesweeperGame game) {
        Theme.currentNumber = themeNumber;
        Image.updateColors();
        game.updateOpenedCellsVisuals();
    }

    public static int getCurrentNumber() {
        return currentNumber;
    }

    public static String getCurrentName() {
        return NAMES.get(currentNumber);
    }
}
