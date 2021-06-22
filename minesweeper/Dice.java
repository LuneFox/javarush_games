package com.javarush.games.minesweeper;

import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Image;
import com.javarush.games.minesweeper.view.View;

/**
 * Graphical dice that appears on screen when you use Lucky Dice
 */

public class Dice {
    public Cell cell; // appears at this cell
    private int x;
    private int y;
    private int showDelay;
    private Image image;
    boolean isHidden;

    public Dice(int number) {
        setImage(number, 0, 0);
        isHidden = false;
    }

    public void setImage(int number, int x, int y) {
        this.x = x;
        this.y = y;
        this.showDelay = 0;
        switch (number) {
            case 2:
                image = View.IMAGES.get(Bitmap.DICE_2);
                break;
            case 3:
                image = View.IMAGES.get(Bitmap.DICE_3);
                break;
            case 4:
                image = View.IMAGES.get(Bitmap.DICE_4);
                break;
            case 5:
                image = View.IMAGES.get(Bitmap.DICE_5);
                break;
            case 6:
                image = View.IMAGES.get(Bitmap.DICE_6);
                break;
            default:
                image = View.IMAGES.get(Bitmap.DICE_1);
                break;
        }
    }

    public void draw() {
        if (!isHidden) {
            if (showDelay < 20) {
                showDelay++;
                image.drawAt(this.x * 10 + 2, this.y * 10 + 2);
            }
        }
    }
}
