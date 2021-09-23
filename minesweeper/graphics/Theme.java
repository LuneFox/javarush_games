package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;

public enum Theme {
    MAIN_MENU_BG(new Color[]{
            Color.BROWN,
            Color.MEDIUMSEAGREEN,
            Color.ROYALBLUE
    }),
    MAIN_MENU_BORDER(new Color[]{
            Color.DARKRED,
            Color.DARKGREEN,
            Color.STEELBLUE
    }),
    MAIN_MENU_QUOTE_FRONT(new Color[]{
            Color.SALMON,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE
    }),
    MAIN_MENU_QUOTE_BACK(new Color[]{
            Color.DARKRED,
            Color.FORESTGREEN,
            Color.DIMGRAY
    }),
    MAIN_MENU_VERSION(new Color[]{
            Color.DARKRED,
            Color.FORESTGREEN,
            Color.CORNFLOWERBLUE
    }),
    BUTTON_BG(new Color[]{
            Color.DARKRED,
            Color.DARKGREEN,
            Color.STEELBLUE
    }),
    BUTTON_BORDER(new Color[]{
            Color.SALMON,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE
    }),
    LABEL(new Color[]{
            Color.YELLOW,
            Color.YELLOW,
            Color.YELLOW,
    }),
    SHOP_HEADER_FOOTER(new Color[]{
            Color.MAROON,
            Color.MEDIUMSEAGREEN,
            Color.ROYALBLUE
    }),
    SHOP_BORDER(new Color[]{
            Color.SALMON,
            Color.DARKGREEN,
            Color.DODGERBLUE
    }),
    SHOP_BG(new Color[]{
            Color.BROWN,
            Color.DARKSLATEGRAY,
            Color.CORNFLOWERBLUE
    }),
    SHOP_TITLE(new Color[]{
            Color.SALMON,
            Color.SLATEGRAY,
            Color.LIGHTSKYBLUE
    }),
    SHOP_SCORE(new Color[]{
            Color.SALMON,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE
    }),
    SHOP_MOVES(new Color[]{
            Color.INDIANRED,
            Color.PALEGREEN,
            Color.CORNFLOWERBLUE
    }),
    SHOP_ITEM_BG(new Color[]{
            Color.DARKSALMON,
            Color.SEAGREEN,
            Color.STEELBLUE
    }),
    SHOP_ITEM_FRAME_AVAILABLE(new Color[]{
            Color.ANTIQUEWHITE,
            Color.LIGHTGREEN,
            Color.LIGHTBLUE
    }),
    SHOP_SIGN_NO(new Color[]{
            Color.ANTIQUEWHITE,
            Color.PINK,
            Color.PINK
    }),
    CELL_LIGHT(new Color[]{
            Color.BLANCHEDALMOND,
            Color.HONEYDEW,
            Color.LIGHTBLUE
    }),
    CELL_BG_DOWN(new Color[]{
            Color.SANDYBROWN,
            Color.DARKSEAGREEN,
            Color.CORNFLOWERBLUE
    }),
    CELL_BG_UP(new Color[]{
            Color.BURLYWOOD,
            Color.LIGHTGREEN,
            Color.LIGHTSKYBLUE
    }),
    CELL_SHADOW(new Color[]{
            Color.SADDLEBROWN,
            Color.SEAGREEN,
            Color.STEELBLUE
    }),
    CELL_SCANNED(new Color[]{
            Color.ORANGE,
            Color.MEDIUMSPRINGGREEN,
            Color.DEEPSKYBLUE
    });

    public static final int USSR = 0;
    public static final int MINT = 1;
    public static final int SKY = 2;
    private static int currentNumber = USSR;
    Color[] colors;

    Theme(Color[] colors) {
        this.colors = colors;
    }

    public Color getColor(){
        return this.colors[currentNumber];
    }

    public static void set(int themeNumber) {
        Theme.currentNumber = themeNumber;
    }

    public static int get() {
        return currentNumber;
    }
}
