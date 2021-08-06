package com.javarush.games.minesweeper;

import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Image;
import com.javarush.games.minesweeper.view.View;

/**
 * Graphical dice that appears on screen when you use Lucky Dice
 */

public class Dice {
    public Cell appearCell;
    public boolean isHidden;
    private int x;
    private int y;
    private int onScreenTime;
    private Image image;
    private final Image[] images = {
            View.IMAGES.get(Bitmap.DICE_1), View.IMAGES.get(Bitmap.DICE_2), View.IMAGES.get(Bitmap.DICE_3),
            View.IMAGES.get(Bitmap.DICE_4), View.IMAGES.get(Bitmap.DICE_5), View.IMAGES.get(Bitmap.DICE_6)};

    public Dice(int number) {
        setImage(number, 0, 0);
        isHidden = false;
    }

    public void setImage(int number, int x, int y) {
        this.x = x;
        this.y = y;
        this.onScreenTime = 0;
        image = images[number - 1];
    }

    public void draw() {
        if (!isHidden) {
            if (onScreenTime++ < 20) {
                image.drawAt(this.x * 10 + 2, this.y * 10 + 2);
            }
        }
    }
}
