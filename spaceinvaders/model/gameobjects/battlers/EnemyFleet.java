package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.engine.cell.Game;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Bullet;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.ArrayList;
import java.util.List;

public class EnemyFleet {

    public EnemyFleet() {
        createShips();
    }

    private static final int ROWS_COUNT = 3;
    private static final int COLUMNS_COUNT = 10;
    private static final int STEP = ObjectShape.TANK_1.length + 1;

    private List<EnemyShip> ships;
    private Direction direction = Direction.RIGHT;


    // -------- BASIC ACTIONS

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


    // -------- GRAPHICS

    public void draw(SpaceInvadersGame game, boolean reversed) {
        ships.forEach(GameObject::draw);
    }


    // -------- UTILITIES

    private void createShips() {
        ships = new ArrayList<>();
        for (int x = 0; x < COLUMNS_COUNT; x++) {
            for (int y = 0; y < ROWS_COUNT; y++) {
                ships.add(new EnemyShip(x * STEP, y * STEP + 15));
            }
        }
        ships.add(new Boss((STEP * COLUMNS_COUNT / 2.0) - (ObjectShape.BOSS_TANK_1.length / 2.0) - 1, 1));
    }

    public int verifyHit(List<Bullet> bullets) {
        if (bullets.isEmpty()) {
            return 0;
        }

        int[] sum = new int[]{0};
        ships.forEach(enemyShip -> {
            bullets.forEach(bullet -> {
                if (enemyShip.collidesWithAnotherObject(bullet, Mirror.NONE) && enemyShip.isAlive && bullet.isAlive) {
                    if (bullet.deadlyForEnemies) {
                        enemyShip.kill();
                        bullet.kill();
                        sum[0] += enemyShip.score;
                    }
                }
            });
        });
        return sum[0];
    }

    public void deleteHiddenShips() {
        ArrayList<EnemyShip> shipsClone = new ArrayList<>(ships);
        shipsClone.forEach(enemyShip -> {
            if (!enemyShip.isVisible()) {
                ships.remove(enemyShip);
            }
        });
    }


    // -------- STATUS GETTERS

    private double getSpeed() {
        return Math.min(3.0, 5.0 / ships.size());
    }

    public int getShipsCount() {
        return ships.size();
    }

    public double getBottomBorder() {
        final double[] bottomBorder = {0};
        ships.forEach(ship -> {
            bottomBorder[0] = Math.max(bottomBorder[0], ship.y + ship.height);
        });
        return bottomBorder[0];
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
