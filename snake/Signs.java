package com.javarush.games.snake;

import com.javarush.games.snake.enums.Graphics;

class Signs {
    static String headSign;
    static String deadSign;
    static String bodySign;
    static String fieldSign;      // 0
    static String woodSign;       // 1
    static String waterSign;      // 2
    static String fireSign;       // 3
    static String forestSign1;    // 4
    static String forestSign2;    // 4
    static String wormHoleSign;   // 5
    static String mountainSign;   // 6
    static String wallSign;       // 7
    static String sandSign;       // 8
    static String voidSign;       // 9
    static String neutralOrb;
    static String waterOrb;
    static String fireOrb;
    static String earthOrb;
    static String airOrb;
    static String almightyOrb;
    static Graphics currentSetting;

    static void set(Graphics graphics) {
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
