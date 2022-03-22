package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.model.Options;

/**
 * Lucky Dice representation with score memory
 */

public class Dice extends InteractiveObject {
    public Cell appearCell;
    private Image image;
    private int rollResult;
    public int rollsSum = 0;
    public int rollsCount = 0;
    private int timeToLive;

    public void roll(Cell cell) {
        if (cell.isMined()) return;
        if (!game.shop.luckyDice.isActivated()) return;
        if (!game.boardManager.isRecursiveMove()) {
            rollResult = game.getRandomNumber(6) + 1;  // one roll per move
            appearCell = cell;
        }
        setImage(rollResult, appearCell.x, appearCell.y);
        game.player.score.addDiceScore(Options.difficulty * rollResult);
        rollsCount++;
        rollsSum += rollResult;
    }

    public void setImage(int number, int x, int y) {
        setPosition(x, y);
        this.image = Image.cache.get(ImageType.valueOf("BOARD_DICE_" + number));
        this.timeToLive = 20;
    }

    public void draw() {
        if (timeToLive <= 0) return;
        image.draw(this.x * 10 + 2, this.y * 10 + 2);
        timeToLive--;
    }

    public void drawIfActive() {
        if (game.shop.luckyDice == null) return;
        int diceRemainingTurns = game.shop.luckyDice.expireMove - game.player.getMoves();
        if (Util.inside(diceRemainingTurns, 0, 2) && game.player.getMoves() != 0) {
            draw();
        }
    }

    public void hide() {
        timeToLive = 0;
    }

    public double getAverageLuck() {
        // Average luck from 1.0 (bad luck) to 6.0 (perfect luck)
        return (Util.round((double) rollsSum / rollsCount, 2));
    }
}
