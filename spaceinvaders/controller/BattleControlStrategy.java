package com.javarush.games.spaceinvaders.controller;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;

public class BattleControlStrategy implements ControlStrategy {
    private final SpaceInvadersGame game;

    public BattleControlStrategy(SpaceInvadersGame game) {
        this.game = game;
    }

    @Override
    public void pressUp() {
        if (game.isStopped()) return;
        game.getMario().jump();
    }

    @Override
    public void pressRight() {
        if (game.isStopped()) return;
        game.getMario().setMoveDirection(Direction.RIGHT);
    }

    @Override
    public void releaseRight() {
        if (game.isStopped()) return;
        if (game.getMario().getMoveDirection() == Direction.RIGHT) {
            game.getMario().setMoveDirection(Direction.NONE);
        }
    }

    @Override
    public void pressLeft() {
        if (game.isStopped()) return;
        game.getMario().setMoveDirection(Direction.LEFT);
    }

    @Override
    public void releaseLeft() {
        if (game.isStopped()) return;
        if (game.getMario().getMoveDirection() == Direction.LEFT) {
            game.getMario().setMoveDirection(Direction.NONE);
        }
    }

    @Override
    public void pressSpace() {
        if (game.isStopped() && game.isEndingDisplayed()) {
            game.startNewGame();
        } else {
            game.getMario().shoot();
        }
    }
}
