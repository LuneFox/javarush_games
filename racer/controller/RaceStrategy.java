package com.javarush.games.racer.controller;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.Direction;

public class RaceStrategy implements ControlStrategy {
    private final RacerGame game;

    public RaceStrategy(RacerGame game) {
        this.game = game;
    }

    @Override
    public void pressUp() {
        game.delorean.setVerticalDirection(Direction.UP);
    }

    @Override
    public void releaseUp() {
        if (game.delorean.getVerticalDirection() == Direction.UP) {
            game.delorean.setVerticalDirection(Direction.NONE);
        }
    }

    @Override
    public void pressDown() {
        game.delorean.setVerticalDirection(Direction.DOWN);
    }

    @Override
    public void releaseDown() {
        if (game.delorean.getVerticalDirection() == Direction.DOWN) {
            game.delorean.setVerticalDirection(Direction.NONE);
        }
    }

    @Override
    public void pressRight() {
        game.delorean.setHorizontalDirection(Direction.RIGHT);
    }

    @Override
    public void releaseRight() {
        if (game.delorean.getHorizontalDirection() == Direction.RIGHT) {
            game.delorean.setHorizontalDirection(Direction.NONE);
        }
    }

    @Override
    public void pressLeft() {
        game.delorean.setHorizontalDirection(Direction.LEFT);
    }

    @Override
    public void releaseLeft() {
        if (game.delorean.getHorizontalDirection() == Direction.LEFT) {
            game.delorean.setHorizontalDirection(Direction.NONE);
        }
    }

    @Override
    public void pressSpace() {
        if (game.isStopped && game.finishTimeOut <= 0) {
            game.createGame();
        }
    }
}
