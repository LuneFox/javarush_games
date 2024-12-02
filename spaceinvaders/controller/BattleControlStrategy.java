package com.javarush.games.spaceinvaders.controller;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;

public class BattleControlStrategy implements ControlStrategy {
    private final SpaceInvadersGame game;

    public BattleControlStrategy(SpaceInvadersGame game) {
        this.game = game;
    }

    @Override
    public void pressUp() {
        if (game.isStopped() || game.isPaused()) return;

        Mario mario = game.getMario();
        mario.jump();
    }

    @Override
    public void pressRight() {
        if (game.isStopped() || game.isPaused()) return;

        Mario mario = game.getMario();
        mario.setMoveDirection(Direction.RIGHT);
    }

    @Override
    public void releaseRight() {
        if (game.isStopped()) return;

        Mario mario = game.getMario();
        if (mario.getMoveDirection() == Direction.RIGHT) {
            mario.setMoveDirection(Direction.NONE);
        }
    }

    @Override
    public void pressLeft() {
        if (game.isStopped() || game.isPaused()) return;

        Mario mario = game.getMario();
        mario.setMoveDirection(Direction.LEFT);
    }

    @Override
    public void releaseLeft() {
        if (game.isStopped()) return;

        Mario mario = game.getMario();
        if (mario.getMoveDirection() == Direction.LEFT) {
            mario.setMoveDirection(Direction.NONE);
        }
    }

    @Override
    public void pressSpace() {
        if (game.isStopped() && game.isEndingDisplayed()) {
            game.startNewGame();
        } else {
            Mario mario = game.getMario();
            mario.shoot();
        }
    }

    @Override
    public void pressPause() {
        game.pause();
    }
}
