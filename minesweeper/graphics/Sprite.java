package com.javarush.games.minesweeper.graphics;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Small sized images (10x10 px). Mainly used to be drawn over tiles.
 */

public class Sprite extends Image {
    public Sprite(Bitmap bitmap, int x, int y) { // creates a sprite in a logical position
        super(bitmap, x, y);
    }

    protected int[][] assignBitmap(Bitmap bitmap) {
        ImageDataStorage imageData = new ImageDataStorage(bitmap);
        colors = imageData.getColors();
        return imageData.getData();
    }
}
