package com.javarush.games.minesweeper.graphics;

import com.javarush.games.minesweeper.MinesweeperGame;

/**
 * Big sized images.
 */

public class Picture extends Image {

    public Picture(Bitmap bitmap, MinesweeperGame game, int drawX, int drawY) {
        super(bitmap, drawX, drawY);
    }

    public int[][] assignBitmap(Bitmap bitmap) {
        ImageData imageData = new ImageData(bitmap);
        colors = imageData.getColors();
        return imageData.getData();
    }
}
