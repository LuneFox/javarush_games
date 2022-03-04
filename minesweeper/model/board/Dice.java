package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.view.graphics.Cache;
import com.javarush.games.minesweeper.view.graphics.VisualElement;
import com.javarush.games.minesweeper.view.graphics.Drawable;
import com.javarush.games.minesweeper.view.graphics.Image;

/**
 * Graphical dice that appears on screen when you use Lucky Dice
 */

public class Dice implements Drawable {
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
        if (number == 1) this.image = Cache.get(VisualElement.SHOP_DICE_1);
        else if (number == 2) this.image = Cache.get(VisualElement.SHOP_DICE_2);
        else if (number == 3) this.image = Cache.get(VisualElement.SHOP_DICE_3);
        else if (number == 4) this.image = Cache.get(VisualElement.SHOP_DICE_4);
        else if (number == 5) this.image = Cache.get(VisualElement.SHOP_DICE_5);
        else if (number == 6) this.image = Cache.get(VisualElement.SHOP_DICE_6);
        this.onScreenTime = 0;
    }

    // Draw dice over the cell until is exceeds display duration, onScreenTime counts with every game step (frame)
    public void draw() {
        if (onScreenTime < DISPLAY_DURATION) {
            image.draw(this.x * 10 + 2, this.y * 10 + 2);
            onScreenTime++;
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
