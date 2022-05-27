package com.javarush.games.snake.view;

import com.javarush.games.snake.model.enums.Graphics;

public class Signs {
    public static String headSign;
    public static String deadSign;
    public static String bodySign;
    public static String fieldSign;      // 0
    public static String woodSign;       // 1
    public static String waterSign;      // 2
    public static String fireSign;       // 3
    public static String forestSign1;    // 4
    public static String forestSign2;    // 4
    public static String wormHoleSign;   // 5
    public static String mountainSign;   // 6
    public static String wallSign;       // 7
    public static String sandSign;       // 8
    public static String voidSign;       // 9
    public static String neutralOrb;
    public static String waterOrb;
    public static String fireOrb;
    public static String earthOrb;
    public static String airOrb;
    public static String almightyOrb;
    public static Graphics currentSetting;

    public static void set(Graphics graphics) {
        switch (graphics) {
            case KANJI:
                headSign = "頭";
                deadSign = "死";
                bodySign = "体";
                fieldSign = "w";
                woodSign = "木";
                waterSign = "川";
                fireSign = "炎";
                forestSign1 = "森";
                forestSign2 = "林";
                wormHoleSign = "下";
                mountainSign = "山";
                wallSign = "壁";
                sandSign = ". -";
                voidSign = "";
                neutralOrb = "玉";
                waterOrb = "水";
                fireOrb = "火";
                earthOrb = "土";
                airOrb = "風";
                almightyOrb = "力";
                currentSetting = Graphics.KANJI;
                break;
            case EMOJI:
                headSign = "\uD83D\uDC32";
                deadSign = "\uD83D\uDC80";
                bodySign = "S";
                fieldSign = "w";
                woodSign = "\uD83C\uDF42";
                waterSign = "~";
                fireSign = "\uD83D\uDD25";
                forestSign1 = "\uD83C\uDF33";
                forestSign2 = "\uD83C\uDF32";
                wormHoleSign = "\uD83D\uDD73️";
                mountainSign = "\uD83C\uDFD4";
                wallSign = "⬜";
                sandSign = ". -";
                voidSign = "";
                neutralOrb = "\uD83D\uDD2E";
                waterOrb = "\uD83D\uDCA7";
                fireOrb = "\uD83C\uDF0B";
                earthOrb = "\uD83C\uDF31";
                airOrb = "\uD83C\uDF2A️";
                almightyOrb = "⚡";
                currentSetting = Graphics.EMOJI;
                break;
            default:
                break;
        }
    }
}
