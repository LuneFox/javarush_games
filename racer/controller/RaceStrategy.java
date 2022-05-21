package com.javarush.games.racer.controller;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.car.Direction;

public class RaceStrategy implements ControlStrategy {
    private final RacerGame game;

    public RaceStrategy(RacerGame game) {
        this.game = game;
    }

    @Override
    public void pressUp() {
        game.delorean.setVerDirection(Direction.UP);
    }

    @Override
    public void releaseUp() {
        if (game.delorean.getVerDirection() == Direction.UP) {
            game.delorean.setVerDirection(Direction.NONE);
        }
    }

    @Override
    public void pressDown() {
        game.delorean.setVerDirection(Direction.DOWN);
    }

    @Override
    public void releaseDown() {
        if (game.delorean.getVerDirection() == Direction.DOWN) {
            game.delorean.setVerDirection(Direction.NONE);
        }
    }

    @Override
    public void pressRight() {
        game.delorean.setHorDirection(Direction.RIGHT);
    }

    @Override
    public void releaseRight() {
        if (game.delorean.getHorDirection() == Direction.RIGHT) {
            game.delorean.setHorDirection(Direction.NONE);
        }
    }

    @Override
    public void pressLeft() {
        game.delorean.setHorDirection(Direction.LEFT);
    }

    @Override
    public void releaseLeft() {
        if (game.delorean.getHorDirection() == Direction.LEFT) {
            game.delorean.setHorDirection(Direction.NONE);
        }
    }

    @Override
    public void pressSpace() {
        if (game.isStopped && game.finishTimeOut <= 0) {
            game.createGame();
        }
    }
}
