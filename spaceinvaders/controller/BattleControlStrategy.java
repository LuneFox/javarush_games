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
        if (game.isGameStopped) return;
        game.mario.jump();
    }

    @Override
    public void pressRight() {
        if (game.isGameStopped) return;
        game.mario.setMoveDirection(Direction.RIGHT);
    }

    @Override
    public void releaseRight() {
        if (game.mario.getMoveDirection() == Direction.RIGHT) {
            game.mario.setMoveDirection(Direction.NONE);
        }
    }

    @Override
    public void pressLeft() {
        if (game.isGameStopped) return;
        game.mario.setMoveDirection(Direction.LEFT);
    }

    @Override
    public void releaseLeft() {
        if (game.mario.getMoveDirection() == Direction.LEFT) {
            game.mario.setMoveDirection(Direction.NONE);
        }
    }

    @Override
    public void pressSpace() {
        if (game.isGameStopped && game.displayedEnding) {
            game.createGame();
        } else {
            game.marioFire();
        }
    }
}
