package com.javarush.games.minesweeper.graphics;

/**
 * Small sized images (10x10 px). Mainly used to be drawn over tiles.
 */

public class Sprite extends Image {
    public Sprite(Bitmap bitmap, int x, int y) { // creates a sprite in a logical position
        super(bitmap, x, y);
    }
}
