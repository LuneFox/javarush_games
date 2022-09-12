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
    private final float TIME_LIMIT = 500F;
    private final Color[] COLORS = new Color[]{Color.RED, Color.DEEPPINK};
    private float time;
    private long lastTickTime;

    public Timer() {
        super();
        x = 0;
        y = 0;
        height = 1;
        width = 100;
        time = 0;
        lastTickTime = getCurrentTime();
    }

    public void draw() {
        if (!Options.isTimerEnabled()) return;
        if (game.isStopped()) return;
        if (game.isFirstMove()) return;

        for (int i = x; i < ((time / TIME_LIMIT) * width); i++) {
            game.drawPixel(i, 0, COLORS[0]);
        }

        tick();
    }

    public void tick() {
        if (!game.isStopped() && timeIsUp()) {
            initiateLoseByTimeout();
        } else {
            countDown();
        }
    }

    private void initiateLoseByTimeout() {
        Phase.setActive(Phase.BOARD);
        PopUpMessage.show("Время вышло!");
        game.lose();
    }

    private void countDown() {
        if (!Options.isTimerEnabled()) return;
        if (getCurrentTime() - lastTickTime < 1000) return;
        time = (time > 0) ? time - Options.getDifficulty() : 0;
        swapColor();
        lastTickTime = getCurrentTime();
    }

    public void swapColor() {
        Color swap = COLORS[0];
        COLORS[0] = COLORS[1];
        COLORS[1] = swap;
    }

    public void reset() {
        if (Options.isTimerEnabled()) {
            time = TIME_LIMIT;
            lastTickTime = getCurrentTime();
        }
    }

    public boolean timeIsUp() {
        return (Options.isTimerEnabled() && time <= 0);
    }

    public int getScoreBonus() {
        return (Options.isTimerEnabled()) ? ((int) (time / 50) * (Options.getDifficulty() / 5)) : 0;
    }

    private long getCurrentTime() {
        return new Date().getTime();
    }
}
