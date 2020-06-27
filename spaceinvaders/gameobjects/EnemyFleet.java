package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.Game;
import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.ShapeMatrix;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

import java.util.ArrayList;
import java.util.List;

public class EnemyFleet {

    public EnemyFleet() {
        createShips();
    }

    private static final int ROWS_COUNT = 3;
    private static final int COLUMNS_COUNT = 10;
    private static final int STEP = ShapeMatrix.ENEMY.length + 1;

    private List<EnemyShip> ships;
    private Direction direction = Direction.RIGHT;

    private void createShips() {
        ships = new ArrayList<>();
        for (int x = 0; x < COLUMNS_COUNT; x++) {
            for (int y = 0; y < ROWS_COUNT; y++) {
                ships.add(new EnemyShip(x * STEP, y * STEP + 12));
            }
        }
    }

    public void draw(Game game) {
        ships.forEach(ship -> ship.draw(game));
    }

    public void move() {
        if (ships.isEmpty()) {
            return;
        }

        boolean changedDirection = false;

        if (direction == Direction.LEFT && getLeftBorder() < 0) {
            direction = Direction.RIGHT;
            changedDirection = true;
        } else if (direction == Direction.RIGHT && getRightBorder() > SpaceInvadersGame.WIDTH) {
            direction = Direction.LEFT;
            changedDirection = true;
        }

        double speed = getSpeed();

        for (EnemyShip ship : ships) {
            if (changedDirection) {
                ship.move(Direction.DOWN, speed);
            } else {
                ship.move(direction, speed);
            }
        }
    }

    public Bullet fire(Game game) {
        if (ships.isEmpty()) {
            return null;
        }

        int noFireChance = game.getRandomNumber(100 / SpaceInvadersGame.COMPLEXITY);
        if (noFireChance > 0) {
            return null;
        }

        int randomShip = game.getRandomNumber(ships.size());
        return (ships.get(randomShip).fire());
    }

    private double getSpeed() {
        return Math.min(2.0, 3.0 / ships.size());
    }

    private double getLeftBorder() {
        final double[] leftBorder = {SpaceInvadersGame.WIDTH};
        ships.forEach(ship -> {
            leftBorder[0] = Math.min(leftBorder[0], ship.x);
        });
        return leftBorder[0];
    }

    private double getRightBorder() {
        final double[] rightBorder = {0};
        ships.forEach(ship -> {
            rightBorder[0] = Math.max(rightBorder[0], ship.x + ship.width);
        });
        return rightBorder[0];
    }

}
