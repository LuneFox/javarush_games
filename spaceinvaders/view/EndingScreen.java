package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.view.printer.Printer;
import com.javarush.games.spaceinvaders.view.printer.TextAlign;

/** Победный и проигрышный текст на пиксельном экране. */
public class EndingScreen {
    /** Сообщение о поражении. */
    private static final String DEFEAT_MESSAGE = "MAMMA MIA!";
    /** Y первой строки победного текста. */
    private static final int VICTORY_TEXT_Y = 25;
    /** Высота строки победного текста. */
    private static final int VICTORY_LINE_HEIGHT = 9;
    /** Y текста поражения. */
    private static final int DEFEAT_TEXT_Y = 37;
    /** Сдвиг строки счёта под текстом поражения. */
    private static final int DEFEAT_SCORE_LINE_OFFSET = 9;
    /** Задержка перед показом счёта после поражения. */
    private static final long DEFEAT_SCORE_DELAY_MS = 2000L;

    /** Игра, для которой показывается финал. */
    private final SpaceInvadersGame game;

    /** Признак показанного финала. */
    private boolean shown;
    /** Признак видимого победного текста. */
    private boolean victoryTextVisible;
    /** Признак видимого текста поражения. */
    private boolean defeatTextVisible;
    /** Время показа текста поражения. */
    private long defeatShownAt;
    /** Признак видимой строки счёта после поражения. */
    private boolean defeatScoreVisible;

    /** Создаёт экран финала для игры. */
    public EndingScreen(SpaceInvadersGame game) {
        this.game = game;
    }

    /** Показывает победный текст. */
    public void showVictory() {
        game.stopTurnTimer();
        victoryTextVisible = true;
        defeatTextVisible = false;
        defeatScoreVisible = false;
        shown = true;
    }

    /** Показывает текст поражения. */
    public void showDefeat() {
        defeatTextVisible = true;
        defeatShownAt = System.currentTimeMillis();
        defeatScoreVisible = false;
        shown = true;
    }

    /** Рисует победный или проигрышный текст пиксельным шрифтом. */
    public void draw() {
        if (victoryTextVisible) {
            String[] lines = {
                    "SCORE: " + Score.getScore(),
                    "THANK YOU, MARIO!",
                    "BUT OUR PRINCESS IS",
                    "IN ANOTHER GAME!"
            };
            for (int i = 0; i < lines.length; i++) {
                Printer.print("<" + lines[i] + ">", Color.WHITE, GameField.WIDTH,
                        VICTORY_TEXT_Y + i * VICTORY_LINE_HEIGHT, TextAlign.CENTER);
            }
        } else if (defeatTextVisible) {
            Printer.print("<" + DEFEAT_MESSAGE + ">", Color.RED,
                    GameField.WIDTH, DEFEAT_TEXT_Y, TextAlign.CENTER);

            if (!defeatScoreVisible
                    && System.currentTimeMillis() - defeatShownAt >= DEFEAT_SCORE_DELAY_MS) {
                defeatScoreVisible = true;
            }
            if (defeatScoreVisible) {
                Printer.print("<SCORE: " + Score.getScore() + ">", Color.WHITE,
                        GameField.WIDTH, DEFEAT_TEXT_Y + DEFEAT_SCORE_LINE_OFFSET,
                        TextAlign.CENTER);
                game.stopTurnTimer();
            }
        }
    }

    /** Сообщает, показан ли финал. */
    public boolean isShown() {
        return shown;
    }

    /** Сбрасывает показ финала. */
    public void reset() {
        shown = false;
        victoryTextVisible = false;
        defeatTextVisible = false;
        defeatScoreVisible = false;
    }
}
