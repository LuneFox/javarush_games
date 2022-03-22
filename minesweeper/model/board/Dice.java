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
    private Cell appearCell;
    private Image image;
    private int rollResult;
    private int rollsSum = 0;
    private int rollsCount = 0;
    private int timeToLive;

    public void draw() {
        if (isLuckyDiceInactive()) return;
        if (timeToLive <= 0) return;
        image.draw(this.x * 10 + 2, this.y * 10 + 2);
        timeToLive--;
    }

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

    public void hide() {
        timeToLive = 0;
    }

    public int getRollsCount() {
        return rollsCount;
    }

    /**
     * @return Average luck from 1.0 (bad luck) to 6.0 (perfect luck)
     */
    public double getAverageLuck() {
        return (Util.round((double) rollsSum / rollsCount, 2));
    }

    private void setImage(int number, int x, int y) {
        setPosition(x, y);
        this.image = Image.cache.get(ImageType.valueOf("BOARD_DICE_" + number));
        this.timeToLive = 20;
    }

    private boolean isLuckyDiceInactive() {
        if (game.shop.luckyDice == null) return true;
        int diceRemainingTurns = game.shop.luckyDice.expireMove - game.player.getMoves();
        return !Util.inside(diceRemainingTurns, 0, 2) || game.player.getMoves() == 0;
    }
}
