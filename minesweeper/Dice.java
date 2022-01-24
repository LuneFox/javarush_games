package com.javarush.games.minesweeper;

import com.javarush.games.minesweeper.graphics.VisualElement;
import com.javarush.games.minesweeper.graphics.Drawable;
import com.javarush.games.minesweeper.graphics.Image;
import com.javarush.games.minesweeper.view.View;

/**
 * Graphical dice that appears on screen when you use Lucky Dice
 */

public class Dice implements Drawable {

    // Images to draw over cells, one for each side of the dice, null at 0 align numbers
    private final static Image[] SIDES_CACHE = {null,
            View.IMAGES_CACHE.get(VisualElement.SHOP_DICE_1),
            View.IMAGES_CACHE.get(VisualElement.SHOP_DICE_2),
            View.IMAGES_CACHE.get(VisualElement.SHOP_DICE_3),
            View.IMAGES_CACHE.get(VisualElement.SHOP_DICE_4),
            View.IMAGES_CACHE.get(VisualElement.SHOP_DICE_5),
            View.IMAGES_CACHE.get(VisualElement.SHOP_DICE_6)};
    private final static int DISPLAY_DURATION = 20;

    public Cell appearCell;     // cell at which the dice appears after click
    private Image image;
    private int x;
    private int y;
    public int totalBonus;      // sum of all sides on every affected cell (memory field)
    public int totalCells;      // total number of cells affected by the dice (memory field)
    private int onScreenTime;   // number of frames that have passed since it was displayed

    public Dice(int number) {
        setImage(number, 0, 0);
        totalBonus = 0;
        totalCells = 0;
    }

    public void setImage(int number, int x, int y) {
        this.x = x;
        this.y = y;
        this.image = SIDES_CACHE[number];
        this.onScreenTime = 0;
    }

    // Draw dice over the cell until is exceeds display duration, onScreenTime counts with every game step (frame)
    public void draw() {
        if (Screen.is(Screen.BOARD) || Screen.is(Screen.GAME_OVER)) {
            if (onScreenTime < DISPLAY_DURATION) {
                image.drawAt(this.x * 10 + 2, this.y * 10 + 2);
                onScreenTime++;
            }
        }
    }

    // Average luck from 1.0 (bad luck) to 6.0 (perfect luck)
    public double getAverageLuck() {
        return (Util.round((double) totalBonus / totalCells, 2));
    }

    // Instantly skip display time
    public void hide() {
        onScreenTime = DISPLAY_DURATION;
    }
}
