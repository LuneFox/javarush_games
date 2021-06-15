package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;

import java.util.Date;

public class Timer {
    private MinesweeperGame game;
    public float time;
    private final float TIME_LIMIT = 500;
    public boolean isOn;
    public boolean optionIsOn;
    private Date lastTickTime;
    private final Color[] COLORS;

    public Timer(MinesweeperGame game) {
        this.COLORS = new Color[]{Color.RED, Color.DEEPPINK};
        this.time = 0;
        this.game = game;
        this.isOn = true;
        this.lastTickTime = new Date();
    }

    public void draw() {
        if (isOn) {
            for (int i = 0; i < ((time / TIME_LIMIT) * 100); i++) {
                game.display.setCellColor(i, 0, COLORS[0]);
            }
        }
    }

    public void countDown() {
        if (isOn && ((new Date().getTime() - lastTickTime.getTime()) >= 1000)) {
            time = (time > 0) ? time -= game.difficulty : 0;
            Color swap = COLORS[0];
            COLORS[0] = COLORS[1];
            COLORS[1] = swap;
            lastTickTime = new Date();
        }
    }

    public void restart() {
        if (isOn) {
            time = TIME_LIMIT;
        }
    }

    public boolean isZero() {
        return (isOn && time <= 0);
    }

    public int getScore() {
        return (isOn) ? ((int) (time / 100) * (game.difficulty / 5)) : 0;
    }
}
