package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.GameField;

import java.util.List;

/**
 * Movement of the enemy formation: keeps the march direction, speeds the army up as tanks die and
 * makes it step down when the border of the field is reached.
 */
class ArmyMarch {
    private static final double MAX_SPEED = 3.0;
    private static final double SPEED_DIVIDEND = 5.0;

    private Direction direction = Direction.RIGHT;

    void advance(List<EnemyTank> tanks) {
        if (tanks.isEmpty()) return;

        Direction step = shouldStepDown(tanks) ? Direction.DOWN : direction;
        double speed = speedFor(tanks.size());
        for (EnemyTank tank : tanks) {
            tank.advance(step, speed);
        }
    }

    private double speedFor(int tankCount) {
        return Math.min(MAX_SPEED, SPEED_DIVIDEND / tankCount);
    }

    private boolean shouldStepDown(List<EnemyTank> tanks) {
        if (direction == Direction.LEFT && leftMostX(tanks) <= 0) {
            direction = Direction.RIGHT;
            return true;
        }
        if (direction == Direction.RIGHT && rightMostEdge(tanks) >= GameField.WIDTH) {
            direction = Direction.LEFT;
            return true;
        }
        return false;
    }

    private double leftMostX(List<EnemyTank> tanks) {
        double leftMost = GameField.WIDTH;
        for (EnemyTank tank : tanks) {
            leftMost = Math.min(leftMost, tank.getX());
        }
        return leftMost;
    }

    private double rightMostEdge(List<EnemyTank> tanks) {
        double rightMost = 0;
        for (EnemyTank tank : tanks) {
            rightMost = Math.max(rightMost, tank.getX() + tank.getWidth());
        }
        return rightMost;
    }
}
