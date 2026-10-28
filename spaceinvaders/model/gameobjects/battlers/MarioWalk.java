package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;

/** Физика ходьбы Марио: инерция, ускорение и торможение. */
class MarioWalk {
    /** Начальная скорость ходьбы. */
    private static final double START_SPEED = 0.6;
    /** Максимальная скорость ходьбы. */
    private static final double MAX_SPEED = 2.4;
    /** Ускорение при ходьбе. */
    private static final double ACCELERATION = 0.2;
    /** Порог остановки. */
    private static final double STOP_SPEED_THRESHOLD = 0.5;

    /** Текущая скорость. */
    private double speed;

    /** Пересчитывает скорость и сообщает о торможении. */
    boolean update(Direction direction) {
        accelerate(direction);
        return brakeIfReversing(direction);
    }

    /** Ускоряет или замедляет Марио. */
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

    /** Тормозит при смене направления. */
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

    /** Возвращает текущую скорость. */
    double getSpeed() {
        return speed;
    }

    /** Сообщает, идёт ли Марио. */
    boolean isMoving() {
        return Math.abs(speed) > ACCELERATION;
    }
}
