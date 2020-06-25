package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.Game;
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

    private void createShips() {
        ships = new ArrayList<>();
        for (int x = 0; x < COLUMNS_COUNT; x++) {
            for (int y = 0; y < ROWS_COUNT; y++) {
                ships.add(new EnemyShip(x * STEP, y * STEP + 12));
            }
        }
    }

    public void draw(Game game) {
        for (EnemyShip ship : ships) {
            ship.draw(game);
        }
    }

    private double getLeftBorder() {
        double leftBorder = SpaceInvadersGame.WIDTH;
        for (EnemyShip ship : ships) {
            if (ship.x < leftBorder) {
                leftBorder = ship.x;
            }
        }
        return leftBorder;
    }

    private double getRightBorder() {
        double rightBorder = 0;
        for (EnemyShip ship : ships) {
            if (ship.x + ship.width > rightBorder) {
                rightBorder = ship.x + ship.width;
            }
        }
        return rightBorder;
    }
}
