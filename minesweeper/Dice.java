package com.javarush.games.minesweeper;

import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Drawable;
import com.javarush.games.minesweeper.graphics.Image;
import com.javarush.games.minesweeper.view.View;

/**
 * Graphical dice that appears on screen when you use Lucky Dice
 */

public class Dice implements Drawable {
    public Cell appearCell;
    public boolean isHidden;
    public int totalBonus;
    public int totalCells;
    private int x;
    private int y;
    private int onScreenTime;
    private Image image;
    private final Image[] images = {
            View.IMAGES.get(Bitmap.SHOP_DICE_1), View.IMAGES.get(Bitmap.SHOP_DICE_2), View.IMAGES.get(Bitmap.SHOP_DICE_3),
            View.IMAGES.get(Bitmap.SHOP_DICE_4), View.IMAGES.get(Bitmap.SHOP_DICE_5), View.IMAGES.get(Bitmap.SHOP_DICE_6)};

    public Dice(int number) {
        setImage(number, 0, 0);
        reset();
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

    public void reset() {
        totalBonus = 0;
        totalCells = 0;
        isHidden = false;
    }

    public double getAverageLuck() {
        return (Util.round((double) totalBonus / totalCells, 2));
    }
}
