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
    static String forestSign;     // 4
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
                fieldSign = "w";     // 0
                woodSign = "木";        // 1
                waterSign = "川";       // 2
                fireSign = "炎";        // 3
                forestSign = "森";      // 4
                wormHoleSign = "下";    // 5
                mountainSign = "山";    // 6
                wallSign = "壁";        // 7
                sandSign = ". -";       // 8
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
                bodySign = " ";
                fieldSign = "w";
                woodSign = "\uD83C\uDF42";
                waterSign = "\uD83C\uDF0A";
                fireSign = "\uD83D\uDD25";
                forestSign = "\uD83C\uDF33";
                wormHoleSign = "\uD83D\uDD73️";
                mountainSign = " ";
                wallSign = "";
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
