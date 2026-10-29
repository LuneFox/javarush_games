package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.StageProgress;
import com.javarush.games.spaceinvaders.view.printer.Printer;
import com.javarush.games.spaceinvaders.view.printer.TextAlign;

/** Текст игры: счёт, рекорд, стадия и мигающая пауза. */
public class ScoreBoard {
    /** Y строки счёта. */
    private static final int SCORE_Y = 50;
    /** Y строки рекорда. */
    private static final int TOP_SCORE_Y = 1;
    /** Y строки стадии. */
    private static final int STAGE_Y = 1;
    /** Y надписи паузы. */
    private static final int PAUSE_Y = 50;
    /** Y центральной надписи с номером стадии во время заставки. */
    private static final int INTRO_STAGE_Y = 47;
    /** Период мигания паузы. */
    private static final int BLINK_PERIOD = 10;

    /** Счётчик кадров мигания паузы. */
    private int pauseTicks;

    /** Рисует счёт, рекорд и стадию. */
    public void draw() {
        draw(true);
    }

    /** Рисует табло; showScore управляет центральной строкой счёта. */
    public void draw(boolean showScore) {
        if (showScore) {
            Printer.print(String.valueOf(Score.getScore()), Color.DARKBLUE, GameField.WIDTH, SCORE_Y, TextAlign.CENTER);
        }
        Printer.print("TOP: " + Score.getTopScore(), Color.DARKBLUE, GameField.WIDTH, TOP_SCORE_Y, TextAlign.RIGHT);
        Printer.print("Stage " + StageProgress.getNumber(), Color.DARKBLUE, 1, STAGE_Y);
    }

    /** Рисует номер стадии по центру во время вступительной сцены. */
    public void drawIntroStageIfActive(boolean introActive) {
        drawIntroStageIfActive(introActive, false);
    }

    /** Рисует STAGE N, а во время выезда босса — GO!. */
    public void drawIntroStageIfActive(boolean introActive, boolean bossIntroActive) {
        if (!introActive) return;

        String text = bossIntroActive ? "GO!" : "STAGE " + StageProgress.getNumber();
        Printer.print("<" + text + ">", Color.WHITE,
                GameField.WIDTH, INTRO_STAGE_Y, TextAlign.CENTER);
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
