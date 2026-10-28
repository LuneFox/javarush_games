package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Score;

/** Диалог окончания раунда. */
public class EndingScreen {
    private static final String VICTORY_PREFIX = "SCORE: ";
    private static final String VICTORY_SUFFIX = " ~ THANK YOU, MARIO!\nBUT OUR PRINCESS IS IN ANOTHER GAME!";
    private static final String DEFEAT_MESSAGE = "MAMMA MIA!";
    private static final int VICTORY_TEXT_SIZE = 20;
    private static final int DEFEAT_TEXT_SIZE = 75;

    private final SpaceInvadersGame game;

    private boolean shown;

    /** Создаёт экран финала для игры. */
    public EndingScreen(SpaceInvadersGame game) {
        this.game = game;
    }

    /** Показывает победу со счётом. */
    public void showVictory() {
        String message = VICTORY_PREFIX + Score.getScore() + VICTORY_SUFFIX;
        show(message, Color.WHITE, VICTORY_TEXT_SIZE);
    }

    /** Показывает поражение. */
    public void showDefeat() {
        show(DEFEAT_MESSAGE, Color.RED, DEFEAT_TEXT_SIZE);
    }

    /** Останавливает таймер и показывает диалог. */
    private void show(String message, Color textColor, int textSize) {
        game.stopTurnTimer();
        game.showMessageDialog(Color.NONE, message, textColor, textSize);
        shown = true;
    }

    /** Сообщает, показан ли финал. */
    public boolean isShown() {
        return shown;
    }

    /** Сбрасывает показ финала. */
    public void reset() {
        shown = false;
    }
}
