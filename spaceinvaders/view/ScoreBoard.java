package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.StageProgress;
import com.javarush.games.spaceinvaders.view.printer.Printer;
import com.javarush.games.spaceinvaders.view.printer.TextAlign;

/** Текст игры: счёт, рекорд, стадия и мигающая пауза. */
public class ScoreBoard {
    private static final int SCORE_Y = 50;
    private static final int TOP_SCORE_Y = 1;
    private static final int STAGE_Y = 1;
    private static final int PAUSE_Y = 50;
    private static final int BLINK_PERIOD = 10;

    private int pauseTicks;

    /** Рисует счёт, рекорд и стадию. */
    public void draw() {
        Printer.print(String.valueOf(Score.getScore()), Color.DARKBLUE, GameField.WIDTH, SCORE_Y, TextAlign.CENTER);
        Printer.print("TOP: " + Score.getTopScore(), Color.DARKBLUE, GameField.WIDTH, TOP_SCORE_Y, TextAlign.RIGHT);
        Printer.print("Stage " + StageProgress.getNumber(), Color.DARKBLUE, 1, STAGE_Y);
    }

    /** Мигает надписью паузы, если игра стоит. */
    public void drawPauseLabelIfPaused(boolean paused) {
        if (!paused) return;

        boolean labelVisible = pauseTicks++ % BLINK_PERIOD < BLINK_PERIOD / 2;
        if (labelVisible) {
            Printer.print("PAUSE", Color.WHITE, GameField.WIDTH, PAUSE_Y, TextAlign.CENTER);
        }
    }
}
