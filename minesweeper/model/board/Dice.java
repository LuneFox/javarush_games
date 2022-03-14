package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.image.Image;

/**
 * Graphical dice that appears on screen when you use Lucky Dice
 */

public class Dice extends InteractiveObject {
    private final static int DISPLAY_DURATION = 20;

    public Cell appearCell;     // cell at which the dice appears after click
    private Image image;
    public int totalBonus;      // sum of all sides on every affected cell (memory field)
    public int totalCells;      // total number of cells affected by the dice (memory field)
    private int onScreenTime;   // number of frames that have passed since it was displayed

    public Dice(int number) {
        setImage(number, 0, 0);
        totalBonus = 0;
        totalCells = 0;
    }

    public void setImage(int number, int x, int y) {
        setPosition(x, y);
        this.image = Image.cache.get(ImageType.valueOf("BOARD_DICE_" + number));
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

    public void displayIfActive() {
        if (game.shop.luckyDice == null) {
            return;
        }

        int diceRemainingTurns = game.shop.luckyDice.expireMove - game.player.getMoves();

        if (Util.inside(diceRemainingTurns, 0, 2) && game.player.getMoves() != 0) {
            draw();
        }
    }
}
