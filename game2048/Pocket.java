package com.javarush.games.game2048;

class Pocket {
    final static String POCKET_ICON = "⬤";
    int x;
    int y;
    int score;
    boolean open;

    Pocket(int x, int y) {
        this.x = x;
        this.y = y;
        open = true;
    }
}
