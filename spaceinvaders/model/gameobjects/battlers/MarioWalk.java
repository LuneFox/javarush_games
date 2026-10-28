package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;

/**
 * Horizontal walking physics of Mario: inertia, acceleration and braking when the direction is
 * reversed.
 */
class MarioWalk {
    private static final double START_SPEED = 0.6;
    private static final double MAX_SPEED = 2.4;
    private static final double ACCELERATION = 0.2;
    private static final double STOP_SPEED_THRESHOLD = 0.5;

    private double speed;

    /**
     * Recalculates the speed for the requested direction and tells whether Mario is braking now.
     */
    boolean update(Direction direction) {
        accelerate(direction);
        return brakeIfReversing(direction);
    }

    private void accelerate(Direction direction) {
        if (direction == Direction.RIGHT) {
            if (speed == 0) speed = START_SPEED;
            if (speed < MAX_SPEED) speed += ACCELERATION;
        } else if (direction == Direction.LEFT) {
            if (speed == 0) speed = -START_SPEED;
            if (speed > -MAX_SPEED) speed -= ACCELERATION;
        } else {
            speed += (speed > 0) ? -ACCELERATION : ACCELERATION;
            if (Math.abs(speed) < STOP_SPEED_THRESHOLD) speed = 0;
        }
    }

    /**
     * Mario keeps sliding for a while in the old direction before he starts moving the other way.
     */
    private boolean brakeIfReversing(Direction direction) {
        if (direction == Direction.RIGHT && speed < ACCELERATION) {
            speed += ACCELERATION / 2;
            return true;
        }
        if (direction == Direction.LEFT && speed > -ACCELERATION) {
            speed -= ACCELERATION / 2;
            return true;
        }
        return false;
    }

    double getSpeed() {
        return speed;
    }

    boolean isMoving() {
        return Math.abs(speed) > ACCELERATION;
    }
}
