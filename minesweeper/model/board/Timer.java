package com.javarush.games.minesweeper.model.board;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;

import java.util.Date;

/**
 * Red timer stripe that appears at the top of the screen when you play in time attack mode.
 */

public class Timer extends InteractiveObject {
    public float time;
    private final float TIME_LIMIT = 500;
    private Date lastTickTime;
    private final Color[] COLORS;

    public Timer() {
        super();
        x = 0;
        y = 0;
        height = 1;
        width = 100;
        this.COLORS = new Color[]{Color.RED, Color.DEEPPINK};
        this.time = 0;
        this.lastTickTime = new Date();
    }

    public void draw() {
        if (!Options.timerEnabled) return;
        if (game.isStopped) return;
        if (game.isFirstMove) return;
        for (int i = x; i < ((time / TIME_LIMIT) * width); i++) {
            game.display.setCellColor(i, 0, COLORS[0]);
        }
        tick();
    }

    public void tick() {
        if (!game.isStopped && timeIsUp()) {
            loseByTimeOut();
        } else {
            countDown();
        }
    }

    private void loseByTimeOut() {
        Phase.setActive(Phase.BOARD);
        PopUpMessage.show("Время вышло!");
        game.lose();
    }

    private void countDown() {
        if (!Options.timerEnabled) return;
        if (new Date().getTime() - lastTickTime.getTime() >= 1000) {
            time = (time > 0) ? time - Options.difficulty : 0;
            swapColor();
            lastTickTime = new Date();
        }
    }

    public void swapColor() {
        Color swap = COLORS[0];
        COLORS[0] = COLORS[1];
        COLORS[1] = swap;
    }

    public void reset() {
        if (Options.timerEnabled)
            time = TIME_LIMIT;
    }

    public boolean timeIsUp() {
        return (Options.timerEnabled && time <= 0);
    }

    public int getScore() {
        return (Options.timerEnabled) ? ((int) (time / 100) * (Options.difficulty / 5)) : 0;
    }
}
