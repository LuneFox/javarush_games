package com.javarush.games.moonlander.model.painter;

import java.util.HashMap;
import java.util.Map;

public class Util {
    public static final Map<String, String> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put("BLACK", "#000000");
        COLOR_MAP.put("NAVY", "#000080");
        COLOR_MAP.put("DARKBLUE", "#00008B");
        COLOR_MAP.put("MEDIUMBLUE", "#0000CD");
        COLOR_MAP.put("BLUE", "#0000FF");
        COLOR_MAP.put("DARKGREEN", "#006400");
        COLOR_MAP.put("GREEN", "#008000");
        COLOR_MAP.put("TEAL", "#008080");
        COLOR_MAP.put("DARKCYAN", "#008B8B");
        COLOR_MAP.put("DEEPSKYBLUE", "#00BFFF");
        COLOR_MAP.put("DARKTURQUOISE", "#00CED1");
        COLOR_MAP.put("MEDIUMSPRINGGREEN", "#00FA9A");
        COLOR_MAP.put("LIME", "#00FF00");
        COLOR_MAP.put("SPRINGGREEN", "#00FF7F");
        COLOR_MAP.put("CYAN", "#00FFFF");
        COLOR_MAP.put("MIDNIGHTBLUE", "#191970");
        COLOR_MAP.put("DODGERBLUE", "#1E90FF");
        COLOR_MAP.put("LIGHTSEAGREEN", "#20B2AA");
        COLOR_MAP.put("FORESTGREEN", "#228B22");
        COLOR_MAP.put("SEAGREEN", "#2E8B57");
        COLOR_MAP.put("DARKSLATEGREY", "#2F4F4F");
        COLOR_MAP.put("LIMEGREEN", "#32CD32");
        COLOR_MAP.put("MEDIUMSEAGREEN", "#3CB371");
        COLOR_MAP.put("TURQUOISE", "#40E0D0");
        COLOR_MAP.put("ROYALBLUE", "#4169E1");
        COLOR_MAP.put("STEELBLUE", "#4682B4");
        COLOR_MAP.put("DARKSLATEBLUE", "#483D8B");
        COLOR_MAP.put("MEDIUMTURQUOISE", "#48D1CC");
        COLOR_MAP.put("INDIGO", "#4B0082");
        COLOR_MAP.put("DARKOLIVEGREEN", "#556B2F");
        COLOR_MAP.put("CADETBLUE", "#5F9EA0");
        COLOR_MAP.put("CORNFLOWERBLUE", "#6495ED");
        COLOR_MAP.put("MEDIUMAQUAMARINE", "#66CDAA");
        COLOR_MAP.put("DIMGREY", "#696969");
        COLOR_MAP.put("SLATEBLUE", "#6A5ACD");
        COLOR_MAP.put("OLIVEDRAB", "#6B8E23");
        COLOR_MAP.put("SLATEGREY", "#708090");
        COLOR_MAP.put("LIGHTSLATEGREY", "#778899");
        COLOR_MAP.put("MEDIUMSLATEBLUE", "#7B68EE");
        COLOR_MAP.put("LAWNGREEN", "#7CFC00");
        COLOR_MAP.put("CHARTREUSE", "#7FFF00");
        COLOR_MAP.put("AQUAMARINE", "#7FFFD4");
        COLOR_MAP.put("MAROON", "#800000");
        COLOR_MAP.put("PURPLE", "#800080");
        COLOR_MAP.put("OLIVE", "#808000");
        COLOR_MAP.put("GREY", "#808080");
        COLOR_MAP.put("SKYBLUE", "#87CEEB");
        COLOR_MAP.put("LIGHTSKYBLUE", "#87CEFA");
        COLOR_MAP.put("BLUEVIOLET", "#8A2BE2");
        COLOR_MAP.put("DARKRED", "#8B0000");
        COLOR_MAP.put("DARKMAGENTA", "#8B008B");
        COLOR_MAP.put("SADDLEBROWN", "#8B4513");
        COLOR_MAP.put("DARKSEAGREEN", "#8FBC8F");
        COLOR_MAP.put("LIGHTGREEN", "#90EE90");
        COLOR_MAP.put("MEDIUMPURPLE", "#9370DB");
        COLOR_MAP.put("DARKVIOLET", "#9400D3");
        COLOR_MAP.put("PALEGREEN", "#98FB98");
        COLOR_MAP.put("DARKORCHID", "#9932CC");
        COLOR_MAP.put("YELLOWGREEN", "#9ACD32");
        COLOR_MAP.put("SIENNA", "#A0522D");
        COLOR_MAP.put("BROWN", "#A52A2A");
        COLOR_MAP.put("DARKGREY", "#A9A9A9");
        COLOR_MAP.put("LIGHTBLUE", "#ADD8E6");
        COLOR_MAP.put("GREENYELLOW", "#ADFF2F");
        COLOR_MAP.put("PALETURQUOISE", "#AFEEEE");
        COLOR_MAP.put("LIGHTSTEELBLUE", "#B0C4DE");
        COLOR_MAP.put("POWDERBLUE", "#B0E0E6");
        COLOR_MAP.put("FIREBRICK", "#B22222");
        COLOR_MAP.put("DARKGOLDENROD", "#B8860B");
        COLOR_MAP.put("MEDIUMORCHID", "#BA55D3");
        COLOR_MAP.put("ROSYBROWN", "#BC8F8F");
        COLOR_MAP.put("DARKKHAKI", "#BDB76B");
        COLOR_MAP.put("SILVER", "#C0C0C0");
        COLOR_MAP.put("MEDIUMVIOLETRED", "#C71585");
        COLOR_MAP.put("INDIANRED", "#CD5C5C");
        COLOR_MAP.put("PERU", "#CD853F");
        COLOR_MAP.put("CHOCOLATE", "#D2691E");
        COLOR_MAP.put("TAN", "#D2B48C");
        COLOR_MAP.put("LIGHTGREY", "#D3D3D3");
        COLOR_MAP.put("THISTLE", "#D8BFD8");
        COLOR_MAP.put("ORCHID", "#DA70D6");
        COLOR_MAP.put("GOLDENROD", "#DAA520");
        COLOR_MAP.put("PALEVIOLETRED", "#DB7093");
        COLOR_MAP.put("CRIMSON", "#DC143C");
        COLOR_MAP.put("GAINSBORO", "#DCDCDC");
        COLOR_MAP.put("PLUM", "#DDA0DD");
        COLOR_MAP.put("BURLYWOOD", "#DEB887");
        COLOR_MAP.put("LIGHTCYAN", "#E0FFFF");
        COLOR_MAP.put("LAVENDER", "#E6E6FA");
        COLOR_MAP.put("DARKSALMON", "#E9967A");
        COLOR_MAP.put("VIOLET", "#EE82EE");
        COLOR_MAP.put("PALEGOLDENROD", "#EEE8AA");
        COLOR_MAP.put("LIGHTCORAL", "#F08080");
        COLOR_MAP.put("KHAKI", "#F0E68C");
        COLOR_MAP.put("ALICEBLUE", "#F0F8FF");
        COLOR_MAP.put("HONEYDEW", "#F0FFF0");
        COLOR_MAP.put("AZURE", "#F0FFFF");
        COLOR_MAP.put("SANDYBROWN", "#F4A460");
        COLOR_MAP.put("WHEAT", "#F5DEB3");
        COLOR_MAP.put("BEIGE", "#F5F5DC");
        COLOR_MAP.put("WHITESMOKE", "#F5F5F5");
        COLOR_MAP.put("MINTCREAM", "#F5FFFA");
        COLOR_MAP.put("GHOSTWHITE", "#F8F8FF");
        COLOR_MAP.put("SALMON", "#FA8072");
        COLOR_MAP.put("ANTIQUEWHITE", "#FAEBD7");
        COLOR_MAP.put("LINEN", "#FAF0E6");
        COLOR_MAP.put("LIGHTGOLDENRODYELLOW", "#FAFAD2");
        COLOR_MAP.put("OLDLACE", "#FDF5E6");
        COLOR_MAP.put("RED", "#FF0000");
        COLOR_MAP.put("MAGENTA", "#FF00FF");
        COLOR_MAP.put("DEEPPINK", "#FF1493");
        COLOR_MAP.put("ORANGERED", "#FF4500");
        COLOR_MAP.put("TOMATO", "#FF6347");
        COLOR_MAP.put("HOTPINK", "#FF69B4");
        COLOR_MAP.put("CORAL", "#FF7F50");
        COLOR_MAP.put("DARKORANGE", "#FF8C00");
        COLOR_MAP.put("LIGHTSALMON", "#FFA07A");
        COLOR_MAP.put("ORANGE", "#FFA500");
        COLOR_MAP.put("LIGHTPINK", "#FFB6C1");
        COLOR_MAP.put("PINK", "#FFC0CB");
        COLOR_MAP.put("GOLD", "#FFD700");
        COLOR_MAP.put("PEACHPUFF", "#FFDAB9");
        COLOR_MAP.put("NAVAJOWHITE", "#FFDEAD");
        COLOR_MAP.put("MOCCASIN", "#FFE4B5");
        COLOR_MAP.put("BISQUE", "#FFE4C4");
        COLOR_MAP.put("MISTYROSE", "#FFE4E1");
        COLOR_MAP.put("BLANCHEDALMOND", "#FFEBCD");
        COLOR_MAP.put("PAPAYAWHIP", "#FFEFD5");
        COLOR_MAP.put("LAVENDERBLUSH", "#FFF0F5");
        COLOR_MAP.put("SEASHELL", "#FFF5EE");
        COLOR_MAP.put("CORNSILK", "#FFF8DC");
        COLOR_MAP.put("LEMONCHIFFON", "#FFFACD");
        COLOR_MAP.put("FLORALWHITE", "#FFFAF0");
        COLOR_MAP.put("SNOW", "#FFFAFA");
        COLOR_MAP.put("YELLOW", "#FFFF00");
        COLOR_MAP.put("LIGHTYELLOW", "#FFFFE0");
        COLOR_MAP.put("IVORY", "#FFFFF0");
        COLOR_MAP.put("WHITE", "#FFFFFF");
    }

    private Util() {

    }

    public static String exportArrayToCode(int[][] array, int rightBound, int bottomBound, boolean includeLineBreaks) {
        StringBuilder result = new StringBuilder();
        result.append("new int[][]{");
        for (int y = 0; y < bottomBound; y++) {
            for (int x = 0; x < rightBound; x++) {
                if (x == 0) {
                    if (includeLineBreaks) result.append("\n");
                    result.append("{").append(array[y][x]).append(",");
                } else if (x < rightBound - 1) {
                    result.append(array[y][x]).append(",");
                } else if (x == rightBound - 1) {
                    result.append(array[y][x]).append("}");
                }
                if (x == rightBound - 1 && y != bottomBound - 1) {
                    result.append(",");
                }
            }
        }
        if (includeLineBreaks) result.append("\n");
        result.append("}");
        return result.toString();
    }
}
