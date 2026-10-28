package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Score;

/**
 * The dialog shown when the stage is over.
 */
public class EndingScreen {
    private static final String VICTORY_PREFIX = "SCORE: ";
    private static final String VICTORY_SUFFIX = " ~ THANK YOU, MARIO!\nBUT OUR PRINCESS IS IN ANOTHER GAME!";
    private static final String DEFEAT_MESSAGE = "MAMMA MIA!";
    private static final int VICTORY_TEXT_SIZE = 20;
    private static final int DEFEAT_TEXT_SIZE = 75;

    private final SpaceInvadersGame game;

    private boolean shown;

    public EndingScreen(SpaceInvadersGame game) {
        this.game = game;
    }

    public void showVictory() {
        String message = VICTORY_PREFIX + Score.getScore() + VICTORY_SUFFIX;
        show(message, Color.WHITE, VICTORY_TEXT_SIZE);
    }

    public void showDefeat() {
        show(DEFEAT_MESSAGE, Color.RED, DEFEAT_TEXT_SIZE);
    }

    private void show(String message, Color textColor, int textSize) {
        game.stopTurnTimer();
        game.showMessageDialog(Color.NONE, message, textColor, textSize);
        shown = true;
    }

    public boolean isShown() {
        return shown;
    }

    public void reset() {
        shown = false;
    }
}
