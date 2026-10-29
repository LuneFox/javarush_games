package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.StageProgress;
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
    /** Победные шутки; выбор зависит от номера стадии. */
    private static final String[][] VICTORY_JOKES = {
            {
                    "THANK YOU, MARIO!",
                    "BUT OUR PRINCESS IS",
                    "IN ANOTHER GAME!"
            },
            {
                    "THE PRINCESS IS",
                    "SAFE. SHE WENT",
                    "FOR A 1-UP."
            },
            {
                    "BOWSER CALLED.",
                    "HE WANTS HIS",
                    "CASTLE BACK."
            },
            {
                    "YOU GOT A STAR!",
                    "NOW PLEASE STOP",
                    "EATING MY COINS."
            },
            {
                    "LUIGI WAS HERE.",
                    "HE TOOK THE",
                    "LAST MUSHROOM."
            },
            {
                    "THE PIPE LEADS TO",
                    "ANOTHER CASTLE.",
                    "CLASSIC."
            },
            {
                    "CONGRATULATIONS!",
                    "YOUR PRINCESS IS",
                    "IN THE DLC."
            },
            {
                    "YOU SAVED THE DAY!",
                    "NOW PLEASE SAVE",
                    "YOUR PROGRESS."
            },
            {
                    "MARIO, YOU ARE",
                    "THE BEST PLUMBER",
                    "IN THIS GALAXY."
            },
            {
                    "THE TANKS HAVE",
                    "RAGE QUIT.",
                    "YOU WIN!"
            },
            {
                    "IT'S-A ME, MARIO!",
                    "AND I'M HERE TO",
                    "FIX YOUR PIPES."
            },
            {
                    "BLUE SHELL INCOMING!",
                    "...WRONG GAME.",
                    "STILL COUNTS."
            }
    };

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
            Printer.print("<SCORE: " + Score.getScore() + ">", Color.WHITE,
                    GameField.WIDTH, VICTORY_TEXT_Y, TextAlign.CENTER);

            String[] joke = VICTORY_JOKES[(StageProgress.getNumber() - 1) % VICTORY_JOKES.length];
            for (int i = 0; i < joke.length; i++) {
                Printer.print("<" + joke[i] + ">", Color.WHITE, GameField.WIDTH,
                        VICTORY_TEXT_Y + (i + 1) * VICTORY_LINE_HEIGHT, TextAlign.CENTER);
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
