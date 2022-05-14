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
        if (game.mario.isJumping) return;
        game.mario.jump();
    }

    @Override
    public void releaseUp() {
        ControlStrategy.super.releaseUp();
    }

    @Override
    public void pressDown() {
        ControlStrategy.super.pressDown();
    }

    @Override
    public void releaseDown() {
        ControlStrategy.super.releaseDown();
    }

    @Override
    public void pressRight() {
        if (game.isGameStopped) return;
        game.mario.setDirection(Direction.RIGHT);
    }

    @Override
    public void releaseRight() {
        if (game.mario.getDirection() == Direction.RIGHT) {
            game.mario.setDirection(Direction.UP);
        }
    }

    @Override
    public void pressLeft() {
        if (game.isGameStopped) return;
        game.mario.setDirection(Direction.LEFT);
    }

    @Override
    public void releaseLeft() {
        if (game.mario.getDirection() == Direction.LEFT) {
            game.mario.setDirection(Direction.UP);
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

    @Override
    public void releaseSpace() {
        ControlStrategy.super.releaseSpace();
    }
}
