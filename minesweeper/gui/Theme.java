package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.ImageManager;

import java.util.HashMap;
import java.util.Map;

/** Цветовые схемы интерфейса и поля. */
public enum Theme {
    /** Цветовая схема MAIN_MENU_BG. */
    MAIN_MENU_BG(new Color[]{
            Color.BROWN,
            Color.MEDIUMSEAGREEN,
            Color.ROYALBLUE,
            Color.SIENNA
    }),
    /** Цветовая схема MAIN_MENU_BORDER. */
    MAIN_MENU_BORDER(new Color[]{
            Color.DARKRED,
            Color.DARKGREEN,
            Color.STEELBLUE,
            Color.SADDLEBROWN
    }),
    /** Цветовая схема MAIN_MENU_QUOTE_FRONT. */
    MAIN_MENU_QUOTE_FRONT(new Color[]{
            Color.SALMON,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE,
            Color.NAVAJOWHITE
    }),
    /** Цветовая схема TEXT_SHADOW. */
    TEXT_SHADOW(new Color[]{
            Color.DARKRED,
            Color.FORESTGREEN,
            Color.DIMGRAY,
            Color.SADDLEBROWN
    }),
    /** Цветовая схема MAIN_MENU_LABEL. */
    MAIN_MENU_LABEL(new Color[]{
            Color.DARKRED,
            Color.FORESTGREEN,
            Color.CORNFLOWERBLUE,
            Color.PERU
    }),
    /** Цветовая схема BUTTON_BG. */
    BUTTON_BG(new Color[]{
            Color.DARKRED,
            Color.DARKGREEN,
            Color.STEELBLUE,
            Color.DIMGREY
    }),
    /** Цветовая схема BUTTON_BORDER. */
    BUTTON_BORDER(new Color[]{
            Color.SALMON,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE,
            Color.DARKGREY
    }),
    /** Цветовая схема LABEL. */
    LABEL(new Color[]{
            Color.YELLOW,
            Color.YELLOW,
            Color.YELLOW,
            Color.KHAKI
    }),
    /** Цветовая схема SHOP_HEADER_FOOTER. */
    SHOP_HEADER_FOOTER(new Color[]{
            Color.MAROON,
            Color.MEDIUMSEAGREEN,
            Color.ROYALBLUE,
            Color.SIENNA
    }),
    /** Цветовая схема SHOP_BORDER. */
    SHOP_BORDER(new Color[]{
            Color.SALMON,
            Color.DARKGREEN,
            Color.DODGERBLUE,
            Color.DARKGREY
    }),
    /** Цветовая схема SHOP_BG. */
    SHOP_BG(new Color[]{
            Color.BROWN,
            Color.DARKSLATEGRAY,
            Color.CORNFLOWERBLUE,
            Color.SANDYBROWN
    }),
    /** Цветовая схема SHOP_TITLE. */
    SHOP_TITLE(new Color[]{
            Color.SALMON,
            Color.SLATEGRAY,
            Color.LIGHTSKYBLUE,
            Color.LEMONCHIFFON
    }),
    /** Цветовая схема SHOP_SCORE. */
    SHOP_SCORE(new Color[]{
            Color.SALMON,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE,
            Color.GAINSBORO
    }),
    /** Цветовая схема SHOP_MOVES. */
    SHOP_MOVES(new Color[]{
            Color.INDIANRED,
            Color.PALEGREEN,
            Color.CORNFLOWERBLUE,
            Color.LIGHTGREY
    }),
    /** Цветовая схема SHOP_ITEM_BG. */
    SHOP_ITEM_BG(new Color[]{
            Color.DARKSALMON,
            Color.SEAGREEN,
            Color.STEELBLUE,
            Color.SILVER
    }),
    /** Цветовая схема SHOP_ITEM_FRAME_AVAILABLE. */
    SHOP_ITEM_FRAME_AVAILABLE(new Color[]{
            Color.ANTIQUEWHITE,
            Color.LIGHTGREEN,
            Color.LIGHTBLUE,
            Color.ANTIQUEWHITE,
    }),
    /** Цветовая схема SHOP_SIGN_NO. */
    SHOP_SIGN_NO(new Color[]{
            Color.ANTIQUEWHITE,
            Color.PINK,
            Color.PINK,
            Color.ANTIQUEWHITE
    }),
    /** Цветовая схема CELL_LIGHT. */
    CELL_LIGHT(new Color[]{
            Color.BLANCHEDALMOND,
            Color.HONEYDEW,
            Color.LIGHTBLUE,
            Color.IVORY,
    }),
    /** Цветовая схема CELL_BG_DOWN. */
    CELL_BG_DOWN(new Color[]{
            Color.SANDYBROWN,
            Color.DARKSEAGREEN,
            Color.CORNFLOWERBLUE,
            Color.TAN
    }),
    /** Цветовая схема CELL_BG_UP. */
    CELL_BG_UP(new Color[]{
            Color.BURLYWOOD,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE,
            Color.BISQUE,
    }),
    /** Цветовая схема CELL_SHADOW. */
    CELL_SHADOW(new Color[]{
            Color.SADDLEBROWN,
            Color.SEAGREEN,
            Color.STEELBLUE,
            Color.PERU
    }),
    /** Цветовая схема CELL_SCANNED. */
    CELL_SCANNED(new Color[]{
            Color.ORANGE,
            Color.MEDIUMSPRINGGREEN,
            Color.DEEPSKYBLUE,
            Color.ORANGE,
    }),
    /** Цветовая схема FLAG_LIGHT. */
    FLAG_LIGHT(new Color[]{
            Color.RED,
            Color.LIMEGREEN,
            Color.DARKTURQUOISE,
            Color.MEDIUMPURPLE
    }),
    /** Цветовая схема FLAG_DARK. */
    FLAG_DARK(new Color[]{
            Color.DARKRED,
            Color.FORESTGREEN,
            Color.DARKCYAN,
            Color.SLATEBLUE
    }),
    ;

    /** Количество тем. */
    private static final int THEMES_COUNT = MAIN_MENU_BG.colors.length;
    /** Названия тем. */
    private static final Map<Integer, String> NAMES;
    /** Номер текущей темы. */
    private static int currentNumber;
    /** Палитра изображения. */
    private final Color[] colors;

    static {
        NAMES = new HashMap<>();
        NAMES.put(0, "СССР");
        NAMES.put(1, "МЯТА");
        NAMES.put(2, "НЕБО");
        NAMES.put(3, "ДЮНА");
        currentNumber = 3;
    }

    /** Создаёт экземпляр Theme. */
    Theme(Color[] colors) {
        this.colors = colors;
    }

    /** Возвращает цвет темы. */
    public Color getColor() {
        if (this.colors.length != THEMES_COUNT) return Color.WHITE;
        return this.colors[currentNumber];
    }

    /** Задаёт значение. */
    public static void set(int themeNumber, MinesweeperGame game) {
        Theme.currentNumber = themeNumber;
        ImageManager.reloadAllImagesColors();
        game.refreshAllCellsGraphics();
    }

    /** Возвращает номер текущей темы. */
    public static int getCurrentNumber() {
        return currentNumber;
    }

    /** Возвращает название текущей темы. */
    public static String getCurrentName() {
        return NAMES.get(currentNumber);
    }
}
