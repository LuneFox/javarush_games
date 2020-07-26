package com.javarush.games.minesweeper;

import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Image;

public class Dice {
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
                image = Menu.IMAGES.get(Bitmap.DICE_2);
                break;
            case 3:
                image = Menu.IMAGES.get(Bitmap.DICE_3);
                break;
            case 4:
                image = Menu.IMAGES.get(Bitmap.DICE_4);
                break;
            case 5:
                image = Menu.IMAGES.get(Bitmap.DICE_5);
                break;
            case 6:
                image = Menu.IMAGES.get(Bitmap.DICE_6);
                break;
            default:
                image = Menu.IMAGES.get(Bitmap.DICE_1);
                break;
        }
    }

    public void draw() {
        if (!isHidden) {
            if (showDelay < 20) {
                showDelay++;
                image.setPosition(this.x * 10 + 2, this.y * 10 + 2);
                image.draw();
            }
        }
    }
}