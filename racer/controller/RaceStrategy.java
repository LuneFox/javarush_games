package com.javarush.games.racer.controller;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.car.Direction;

/**
 * Race control strategy: the arrow keys steer the DeLorean and SPACE restarts a finished or fuel-less run.
 *
 * <p>The game and its car are cached in the constructor. They are recreated together on every restart, and a
 * new controller (with a new strategy) is created at the same time, so the cached references stay valid.</p>
 */
public class RaceStrategy implements ControlStrategy {
    private final RacerGame game;
    private final DeLorean delorean;

    /**
     * Creates the strategy and caches the game and its current DeLorean.
     *
     * @param game the running game; its car is fetched once here
     */
    public RaceStrategy(RacerGame game) {
        this.game = game;
        this.delorean = game.getDelorean();
    }

    /**
     * Sets the vertical direction to UP. The car only uses it while standing at its leftmost position, and a
     * press overrides a previous DOWN direction.
     */
    @Override
    public void pressUp() {
        delorean.setVerDirection(Direction.UP);
    }

    /**
     * Clears the vertical direction only if it is still UP, so releasing this key cannot cancel a direction set
     * by another key.
     */
    @Override
    public void releaseUp() {
        if (delorean.getVerDirection() == Direction.UP) {
            delorean.setVerDirection(Direction.NONE);
        }
    }

    /**
     * Sets the vertical direction to DOWN. The car only uses it while standing at its leftmost position, and a
     * press overrides a previous UP direction.
     */
    @Override
    public void pressDown() {
        delorean.setVerDirection(Direction.DOWN);
    }

    /**
     * Clears the vertical direction only if it is still DOWN, so releasing this key cannot cancel a direction
     * set by another key.
     */
    @Override
    public void releaseDown() {
        if (delorean.getVerDirection() == Direction.DOWN) {
            delorean.setVerDirection(Direction.NONE);
        }
    }

    /**
     * Sets the horizontal direction to RIGHT, which the car treats as acceleration and which burns fuel while
     * the tank is not empty.
     */
    @Override
    public void pressRight() {
        delorean.setHorDirection(Direction.RIGHT);
    }

    /**
     * Clears the horizontal direction only if it is still RIGHT, so releasing this key cannot cancel a LEFT
     * press.
     */
    @Override
    public void releaseRight() {
        if (delorean.getHorDirection() == Direction.RIGHT) {
            delorean.setHorDirection(Direction.NONE);
        }
    }

    /**
     * Sets the horizontal direction to LEFT, which the car treats as braking. With no horizontal direction the
     * car coasts instead of accelerating.
     */
    @Override
    public void pressLeft() {
        delorean.setHorDirection(Direction.LEFT);
    }

    /**
     * Clears the horizontal direction only if it is still LEFT, so releasing this key cannot cancel a RIGHT
     * press.
     */
    @Override
    public void releaseLeft() {
        if (delorean.getHorDirection() == Direction.LEFT) {
            delorean.setHorDirection(Direction.NONE);
        }
    }

    /**
     * Restarts the game when the race is over (car stopped and ending animation finished) or when the tank is
     * empty. The condition is {@code (stopped && endingFinished) || gas <= 0}, so an empty tank also allows a
     * restart while the ending animation is still playing.
     */
    @Override
    public void pressSpace() {
        if (game.isStopped() && game.isEndingAnimationFinished() || delorean.getGas() <= 0) {
            game.createGame();
        }
    }
}
