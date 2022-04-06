package com.javarush.games.moonlander.model.painter;

import com.javarush.engine.cell.Color;
import com.javarush.games.moonlander.MoonLanderGame;

import java.util.Arrays;
import java.util.List;

public class ColorPalette extends DrawableObject {
    private final MoonLanderGame game = MoonLanderGame.getInstance();
    private static Color[] paletteColors = new Color[]{Color.BLACK, Color.DARKBLUE, Color.NAVY, Color.MIDNIGHTBLUE, Color.INDIGO, Color.MEDIUMBLUE, Color.DARKRED, Color.MAROON, Color.PURPLE, Color.BLUE, Color.DARKMAGENTA, Color.DARKSLATEBLUE, Color.DARKSLATEGREY, Color.DARKGREEN, Color.DARKVIOLET, Color.BROWN, Color.SADDLEBROWN, Color.FIREBRICK, Color.BLUEVIOLET, Color.DARKORCHID, Color.MEDIUMVIOLETRED, Color.CRIMSON, Color.SLATEBLUE, Color.SIENNA, Color.GREEN, Color.DARKOLIVEGREEN, Color.ROYALBLUE, Color.RED, Color.MEDIUMSLATEBLUE, Color.FORESTGREEN, Color.TEAL, Color.MEDIUMORCHID, Color.OLIVE, Color.DEEPPINK, Color.MAGENTA, Color.DIMGREY, Color.SEAGREEN, Color.ORANGERED, Color.INDIANRED, Color.MEDIUMPURPLE, Color.CHOCOLATE, Color.OLIVEDRAB, Color.DARKCYAN, Color.DODGERBLUE, Color.DARKGOLDENROD, Color.STEELBLUE, Color.TOMATO, Color.ORCHID, Color.PALEVIOLETRED, Color.DARKORANGE, Color.PERU, Color.CORNFLOWERBLUE, Color.SLATEGREY, Color.HOTPINK, Color.LIMEGREEN, Color.CORAL, Color.MEDIUMSEAGREEN, Color.VIOLET, Color.SALMON, Color.GOLDENROD, Color.LIME, Color.ORANGE, Color.GREY, Color.LIGHTCORAL, Color.LIGHTSLATEGREY, Color.LIGHTSEAGREEN, Color.LAWNGREEN, Color.CHARTREUSE, Color.CADETBLUE, Color.YELLOWGREEN, Color.ROSYBROWN, Color.GREENYELLOW, Color.SPRINGGREEN, Color.YELLOW, Color.GOLD, Color.DARKSALMON, Color.SANDYBROWN, Color.MEDIUMSPRINGGREEN, Color.DEEPSKYBLUE, Color.DARKTURQUOISE, Color.LIGHTSALMON, Color.MEDIUMAQUAMARINE, Color.PLUM, Color.MEDIUMTURQUOISE, Color.DARKSEAGREEN, Color.TURQUOISE, Color.DARKKHAKI, Color.LIGHTGREEN, Color.PALEGREEN, Color.CYAN, Color.AQUAMARINE, Color.LIGHTPINK, Color.BURLYWOOD, Color.KHAKI, Color.TAN, Color.LIGHTSKYBLUE, Color.PINK, Color.SKYBLUE, Color.DARKGREY, Color.PALEGOLDENROD, Color.NAVAJOWHITE, Color.THISTLE, Color.PALETURQUOISE, Color.LIGHTSTEELBLUE, Color.LIGHTBLUE, Color.MOCCASIN, Color.WHEAT, Color.PEACHPUFF, Color.POWDERBLUE, Color.LEMONCHIFFON, Color.BISQUE, Color.LIGHTGOLDENRODYELLOW, Color.SILVER, Color.BLANCHEDALMOND, Color.LIGHTYELLOW, Color.MISTYROSE, Color.PAPAYAWHIP, Color.CORNSILK, Color.LIGHTCYAN, Color.BEIGE, Color.LAVENDER, Color.HONEYDEW, Color.ANTIQUEWHITE, Color.LIGHTGREY, Color.LAVENDERBLUSH, Color.GAINSBORO, Color.OLDLACE, Color.LINEN, Color.IVORY, Color.AZURE, Color.FLORALWHITE, Color.MINTCREAM, Color.SEASHELL, Color.ALICEBLUE, Color.GHOSTWHITE, Color.WHITESMOKE, Color.SNOW, Color.WHITE};

    public ColorPalette(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw() {
        drawPalette();
        drawPickedColors();
    }

    public void pickPrimaryColor(int x, int y) {
        if (checkClickOutsidePalette(x, y)) return;
        game.painter.primaryColor = game.getCellColor(x, y).ordinal();
    }

    public void pickSecondaryColor(int x, int y) {
        if (checkClickOutsidePalette(x, y)) return;
        game.painter.secondaryColor = game.getCellColor(x, y).ordinal();
    }

    private void drawPalette() {
        int colorValue = 0;
        for (int y = posY; y < posY + height; y++) {
            for (int x = posX; x < posX + width; x++) {
                if (colorValue >= paletteColors.length) {
                    break;
                }
                game.setCellValueEx(x, y, paletteColors[colorValue], "");
                int realCellColor = game.getCellColor(x, y).ordinal();
                String shownNumber = game.painter.showNumbers ? String.valueOf(realCellColor) : "";
                game.setCellValueEx(x, y, paletteColors[colorValue], shownNumber, getOverlayNumberColor(realCellColor));
                colorValue++;
            }
        }
    }

    private void drawPickedColors() {
        String shownNumber;
        // Background
        for (int x = 2; x < 6; x++) {
            for (int y = 3; y < 7; y++) {
                game.setCellValueEx(x, y, game.painter.canvas.getBackgroundColor(), "");
            }
        }
        shownNumber = game.painter.showNumbers ? String.valueOf(game.painter.canvas.getBackgroundColor().ordinal()) : "";
        game.setCellValueEx(2, 3, Color.NONE, shownNumber, getOverlayNumberColor(game.painter.canvas.getBackgroundColor().ordinal()));

        // Secondary Color
        for (int x = 4; x < 6; x++) {
            for (int y = 5; y < 7; y++) {
                game.setCellValueEx(x, y, Color.values()[game.painter.secondaryColor], "");
            }
        }
        shownNumber = game.painter.showNumbers ? String.valueOf(game.painter.secondaryColor) : "";
        game.setCellValueEx(5, 6, Color.NONE, shownNumber, getOverlayNumberColor(game.painter.secondaryColor));

        // Primary color
        for (int x = 3; x < 5; x++) {
            for (int y = 4; y < 6; y++) {
                game.setCellValueEx(x, y, Color.values()[game.painter.primaryColor], "");
            }
        }
        shownNumber = game.painter.showNumbers ? String.valueOf(game.painter.primaryColor) : "";
        game.setCellValueEx(4, 5, Color.NONE, shownNumber, getOverlayNumberColor(game.painter.primaryColor));
    }

    private boolean checkClickOutsidePalette(int x, int y) {
        return x < posX || x >= posX + width || y < posY || y >= posY + height;
    }

    public static Color getOverlayNumberColor(int color) {
        List<Color> colors = Arrays.asList(paletteColors);
        int index = colors.indexOf(Color.values()[color]);
        if (index < paletteColors.length / 2 + 1) return Color.WHITE;
        else return Color.BLACK;
    }
}
