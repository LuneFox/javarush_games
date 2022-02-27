package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.view.graphics.Drawable;

import java.util.Date;

/**
 * Red timer stripe that appears at the top of the screen when you play in time attack mode.
 */

public class Timer implements Drawable {
    private final MinesweeperGame game = MinesweeperGame.getInstance();
    public float time;
    private final float TIME_LIMIT = 500;
    public boolean isEnabled;
    public boolean isEnabledSetting;
    private Date lastTickTime;
    private final Color[] COLORS;

    public Timer() {
        this.COLORS = new Color[]{Color.RED, Color.DEEPPINK};
        this.time = 0;
        this.isEnabled = false;
        this.isEnabledSetting = false;
        this.lastTickTime = new Date();
    }

    public void draw() {
        if (!isEnabled) return;
        if (game.isStopped) return;
        if (game.isFirstMove) return;
        for (int i = 0; i < ((time / TIME_LIMIT) * 100); i++) {
            game.display.setCellColor(i, 0, COLORS[0]);
        }
    }

    public void countDown() {
        if (!isEnabled) return;
        if (new Date().getTime() - lastTickTime.getTime() >= 1000) {
            time = (time > 0) ? time - game.difficulty : 0;
            swapColor();
            lastTickTime = new Date();
        }
    }

    public void swapColor() {
        Color swap = COLORS[0];
        COLORS[0] = COLORS[1];
        COLORS[1] = swap;
    }

    public void restart() {
        if (isEnabled)
            time = TIME_LIMIT;
    }

    public boolean isZero() {
        return (isEnabled && time <= 0);
    }

    public int getScore() {
        return (isEnabled) ? ((int) (time / 100) * (game.difficulty / 5)) : 0;
    }
}
