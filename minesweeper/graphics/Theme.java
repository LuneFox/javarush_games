package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;

import java.util.HashMap;

public class Theme {
    public static Theme current = new Theme(0);
    private static int currentNumber;

    private HashMap<ThemeElement, Color> pattern = new HashMap<>();

    public Theme(int number) {
        currentNumber = number;
        switch (number) {
            case 0:
                pattern.put(ThemeElement.MAIN_MENU_BG, Color.BROWN);
                pattern.put(ThemeElement.MAIN_MENU_BORDER, Color.DARKRED);
                pattern.put(ThemeElement.MAIN_MENU_QUOTE_FRONT, Color.SALMON);
                pattern.put(ThemeElement.MAIN_MENU_QUOTE_BACK, Color.DARKRED);
                pattern.put(ThemeElement.MAIN_MENU_VERSION, Color.DARKRED);
                pattern.put(ThemeElement.BUTTON_BACKGROUND, Color.DARKRED);
                pattern.put(ThemeElement.BUTTON_BORDER, Color.SALMON);
                pattern.put(ThemeElement.LABEL, Color.YELLOW);
                pattern.put(ThemeElement.SHOP_TITLE, Color.SALMON);
                pattern.put(ThemeElement.SHOP_HEADER_FOOTER, Color.MAROON);
                pattern.put(ThemeElement.SHOP_BG, Color.BROWN);
                pattern.put(ThemeElement.SHOP_BORDER, Color.SALMON);
                pattern.put(ThemeElement.SHOP_SCORE, Color.SALMON);
                pattern.put(ThemeElement.SHOP_MOVES, Color.INDIANRED);
                pattern.put(ThemeElement.SHOP_ITEM_BG, Color.DARKSALMON);
                pattern.put(ThemeElement.SHOP_ITEM_FRAME_AVAILABLE, Color.ANTIQUEWHITE);
                pattern.put(ThemeElement.SHOP_SIGN_NO, Color.ANTIQUEWHITE);
                pattern.put(ThemeElement.CELL_BG_UP, Color.BURLYWOOD);
                pattern.put(ThemeElement.CELL_BG_DOWN, Color.SANDYBROWN);
                pattern.put(ThemeElement.CELL_LIGHT, Color.BLANCHEDALMOND);
                pattern.put(ThemeElement.CELL_SHADOW, Color.SADDLEBROWN);
                pattern.put(ThemeElement.CELL_SCANNED, Color.ORANGE);

                break;
            case 1:
                pattern.put(ThemeElement.MAIN_MENU_BG, Color.MEDIUMSEAGREEN);
                pattern.put(ThemeElement.MAIN_MENU_BORDER, Color.DARKGREEN);
                pattern.put(ThemeElement.MAIN_MENU_QUOTE_FRONT, Color.LIGHTGREEN);
                pattern.put(ThemeElement.MAIN_MENU_QUOTE_BACK, Color.FORESTGREEN);
                pattern.put(ThemeElement.MAIN_MENU_VERSION, Color.FORESTGREEN);
                pattern.put(ThemeElement.BUTTON_BACKGROUND, Color.DARKGREEN);
                pattern.put(ThemeElement.BUTTON_BORDER, Color.LIGHTGREEN);
                pattern.put(ThemeElement.LABEL, Color.YELLOW);
                pattern.put(ThemeElement.SHOP_TITLE, Color.SLATEGRAY);
                pattern.put(ThemeElement.SHOP_HEADER_FOOTER, Color.MEDIUMSEAGREEN);
                pattern.put(ThemeElement.SHOP_BG, Color.DARKSLATEGRAY);
                pattern.put(ThemeElement.SHOP_BORDER, Color.DARKGREEN);
                pattern.put(ThemeElement.SHOP_SCORE, Color.LIGHTGREEN);
                pattern.put(ThemeElement.SHOP_MOVES, Color.PALEGREEN);
                pattern.put(ThemeElement.SHOP_ITEM_BG, Color.SEAGREEN);
                pattern.put(ThemeElement.SHOP_ITEM_FRAME_AVAILABLE, Color.LIGHTGREEN);
                pattern.put(ThemeElement.SHOP_SIGN_NO, Color.PINK);
                pattern.put(ThemeElement.CELL_BG_UP, Color.LIGHTGREEN);
                pattern.put(ThemeElement.CELL_BG_DOWN, Color.DARKSEAGREEN);
                pattern.put(ThemeElement.CELL_LIGHT, Color.HONEYDEW);
                pattern.put(ThemeElement.CELL_SHADOW, Color.SEAGREEN);
                pattern.put(ThemeElement.CELL_SCANNED, Color.MEDIUMSPRINGGREEN);
                break;
            case 2:
                pattern.put(ThemeElement.MAIN_MENU_BG, Color.ROYALBLUE);
                pattern.put(ThemeElement.MAIN_MENU_BORDER, Color.STEELBLUE);
                pattern.put(ThemeElement.MAIN_MENU_QUOTE_FRONT, Color.LIGHTSKYBLUE);
                pattern.put(ThemeElement.MAIN_MENU_QUOTE_BACK, Color.DIMGRAY);
                pattern.put(ThemeElement.MAIN_MENU_VERSION, Color.CORNFLOWERBLUE);
                pattern.put(ThemeElement.BUTTON_BACKGROUND, Color.STEELBLUE);
                pattern.put(ThemeElement.BUTTON_BORDER, Color.LIGHTSKYBLUE);
                pattern.put(ThemeElement.LABEL, Color.YELLOW);
                pattern.put(ThemeElement.SHOP_TITLE, Color.LIGHTSKYBLUE);
                pattern.put(ThemeElement.SHOP_HEADER_FOOTER, Color.ROYALBLUE);
                pattern.put(ThemeElement.SHOP_BG, Color.CORNFLOWERBLUE);
                pattern.put(ThemeElement.SHOP_BORDER, Color.DODGERBLUE);
                pattern.put(ThemeElement.SHOP_SCORE, Color.LIGHTSKYBLUE);
                pattern.put(ThemeElement.SHOP_MOVES, Color.CORNFLOWERBLUE);
                pattern.put(ThemeElement.SHOP_ITEM_BG, Color.STEELBLUE);
                pattern.put(ThemeElement.SHOP_ITEM_FRAME_AVAILABLE, Color.LIGHTBLUE);
                pattern.put(ThemeElement.SHOP_SIGN_NO, Color.PINK);
                pattern.put(ThemeElement.CELL_BG_UP, Color.LIGHTSKYBLUE);
                pattern.put(ThemeElement.CELL_BG_DOWN, Color.CORNFLOWERBLUE);
                pattern.put(ThemeElement.CELL_LIGHT, Color.LIGHTBLUE);
                pattern.put(ThemeElement.CELL_SHADOW, Color.STEELBLUE);
                pattern.put(ThemeElement.CELL_SCANNED, Color.DEEPSKYBLUE);
                break;
            default:
                break;
        }
    }

    public static int getCurrentNumber() {
        return currentNumber;
    }

    public Color getColor(ThemeElement themeElement) {
        return pattern.get(themeElement);
    }
}
