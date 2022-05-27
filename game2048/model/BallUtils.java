package com.javarush.games.game2048.model;

import com.javarush.engine.cell.Color;

public class BallUtils {
    public static final char[] BALL_SYMBOLS = " ❶❷❸❹❺❻❼❽➈➉⑪⑫⑬⑭⑮⬤".toCharArray();

    public static Color getColorForBallNumber(int number) {
        if (number == 1 || number == 9) {
            return Color.YELLOW;
        } else if (number == 2 || number == 10) {
            return Color.MEDIUMBLUE;
        } else if (number == 3 || number == 11) {
            return Color.RED;
        } else if (number == 4 || number == 12) {
            return Color.PURPLE;
        } else if (number == 5 || number == 13) {
            return Color.ORANGE;
        } else if (number == 6 || number == 14) {
            return Color.LAWNGREEN;
        } else if (number == 7 || number == 15) {
            return Color.SADDLEBROWN;
        } else if (number == 8) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }
}
