package com.javarush.games.racer.controller;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.car.Direction;

public class RaceStrategy implements ControlStrategy {
    private final RacerGame game;
    private final DeLorean delorean;

    public RaceStrategy(RacerGame game) {
        this.game = game;
        this.delorean = game.getDelorean();
    }

    @Override
    public void pressUp() {
        delorean.setVerDirection(Direction.UP);
    }

    @Override
    public void releaseUp() {
        if (delorean.getVerDirection() == Direction.UP) {
            delorean.setVerDirection(Direction.NONE);
        }
    }

    @Override
    public void pressDown() {
        delorean.setVerDirection(Direction.DOWN);
    }

    @Override
    public void releaseDown() {
        if (delorean.getVerDirection() == Direction.DOWN) {
            delorean.setVerDirection(Direction.NONE);
        }
    }

    @Override
    public void pressRight() {
        delorean.setHorDirection(Direction.RIGHT);
    }

    @Override
    public void releaseRight() {
        if (delorean.getHorDirection() == Direction.RIGHT) {
            delorean.setHorDirection(Direction.NONE);
        }
    }

    @Override
    public void pressLeft() {
        delorean.setHorDirection(Direction.LEFT);
    }

    @Override
    public void releaseLeft() {
        if (delorean.getHorDirection() == Direction.LEFT) {
            delorean.setHorDirection(Direction.NONE);
        }
    }

    @Override
    public void pressSpace() {
        if (game.isStopped && game.framesAfterStop > RacerGame.ENDING_ANIMATION_LENGTH
                || delorean.getGas() <= 0) {
            game.createGame();
        }
    }
}
