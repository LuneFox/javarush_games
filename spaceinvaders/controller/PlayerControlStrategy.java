package com.javarush.games.spaceinvaders.controller;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;

/**
 * Controls Mario during a battle. The game-level keys (pause and restart) are delegated to the game.
 */
public class PlayerControlStrategy implements ControlStrategy {
    private final SpaceInvadersGame game;

    public PlayerControlStrategy(SpaceInvadersGame game) {
        this.game = game;
    }

    @Override
    public void upPressed() {
        if (!canControlMario()) return;

        game.getMario().jump();
    }

    @Override
    public void rightPressed() {
        if (!canControlMario()) return;

        game.getMario().walkTowards(Direction.RIGHT);
    }

    @Override
    public void rightReleased() {
        if (game.isRoundOver()) return;
        if (game.getMario().getWalkingDirection() != Direction.RIGHT) return;

        game.getMario().walkTowards(Direction.NONE);
    }

    @Override
    public void leftPressed() {
        if (!canControlMario()) return;

        game.getMario().walkTowards(Direction.LEFT);
    }

    @Override
    public void leftReleased() {
        if (game.isRoundOver()) return;
        if (game.getMario().getWalkingDirection() != Direction.LEFT) return;

        game.getMario().walkTowards(Direction.NONE);
    }

    @Override
    public void spacePressed() {
        if (game.isRoundOver()) {
            // The timer is stopped only when the ending is shown, this ignores stray auto-repeats
            if (game.isEndingShown()) game.startNewGame();
            return;
        }
        if (game.isPaused()) return;

        game.getMario().useBonus();
    }

    @Override
    public void pausePressed() {
        game.togglePause();
    }

    private boolean canControlMario() {
        Mario mario = game.getMario();
        return !game.isPaused() && !game.isRoundOver() && mario.isAlive();
    }
}
