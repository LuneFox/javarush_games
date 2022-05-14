package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.engine.cell.Game;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.view.shapes.ObjectShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnemyArmy {
    private static final int ROWS = 3;
    private static final int COLUMNS = 10;
    private static final int STEP = ObjectShape.TANK_1.length + 1;
    private List<Tank> enemyTanks;
    private double speed;
    private Direction direction;

    public EnemyArmy() {
        createTanks();
        this.direction = Direction.RIGHT;
    }

    private void createTanks() {
        enemyTanks = new ArrayList<>();
        addTanks();
        addBossTank();
    }

    private void addTanks() {
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                addTank(x, y);
            }
        }
    }

    private void addTank(int x, int y) {
        int marginTop = 15;
        int tankX = x * STEP;
        int tankY = y * STEP + marginTop;
        SmallTank tank = new SmallTank(tankX, tankY);
        enemyTanks.add(tank);
    }

    private void addBossTank() {
        double bossTankX = (STEP * COLUMNS / 2.0) - (ObjectShape.BOSS_TANK_1.length / 2.0);
        double bossTankY = 1;
        BossTank bossTank = new BossTank(bossTankX, bossTankY);
        enemyTanks.add(bossTank);
    }

    public void draw() {
        enemyTanks.forEach(GameObject::draw);
    }

    public void move() {
        if (enemyTanks.isEmpty()) return;
        updateSpeed();
        moveAllTanks();
    }

    private void updateSpeed() {
        speed = Math.min(3.0, 5.0 / enemyTanks.size());
    }

    private void moveAllTanks() {
        boolean directionChanged = changeDirectionNearBorder();
        enemyTanks.forEach(tank -> tank.move(directionChanged ? Direction.DOWN : direction, speed));
    }

    private boolean changeDirectionNearBorder() {
        if (direction == Direction.LEFT && isNearLeftBorder()) {
            direction = Direction.RIGHT;
            return true;
        } else if (direction == Direction.RIGHT && isNearRightBorder()) {
            direction = Direction.LEFT;
            return true;
        }
        return false;
    }

    private boolean isNearLeftBorder() {
        double armyLeftBorder = SpaceInvadersGame.WIDTH;
        for (Tank tank : enemyTanks) {
            armyLeftBorder = Math.min(armyLeftBorder, tank.x);
        }
        return armyLeftBorder <= 0;
    }

    private boolean isNearRightBorder() {
        double armyRightBorder = 0;
        for (Tank tank : enemyTanks) {
            armyRightBorder = Math.max(armyRightBorder, tank.x + tank.getWidth());
        }
        return armyRightBorder >= SpaceInvadersGame.WIDTH;
    }

    public Optional<Bullet> fire(Game game) {
        Optional<Bullet> bulletOptional = Optional.empty();
        if (enemyTanks.isEmpty()) return bulletOptional;

        int chanceToFire = game.getRandomNumber(100 / SpaceInvadersGame.COMPLEXITY);
        if (chanceToFire != 0) return bulletOptional;

        int randomTank = game.getRandomNumber(enemyTanks.size());
        return enemyTanks.get(randomTank).fire();
    }

    public void verifyHit(List<Bullet> bullets) {
        if (bullets.isEmpty()) return;
        for (Tank tank : enemyTanks) {
            for (Bullet bullet : bullets) {
                destroyTankAndBulletOnCollision(tank, bullet);
            }
        }
    }

    private void destroyTankAndBulletOnCollision(Tank enemyTank, Bullet bullet) {
        if (!bullet.canKillEnemies) return;
        if (!bullet.isAlive) return;
        if (!enemyTank.isAlive) return;
        if (!enemyTank.collidesWith(bullet, Mirror.NONE)) return;

        enemyTank.kill();
        bullet.kill();
    }

    public boolean reachedLine(double lineHeight) {
        double armyBottomBorder = 0;
        for (Tank tank : enemyTanks) {
            armyBottomBorder = Math.max(armyBottomBorder, tank.y + tank.getHeight());
        }
        return armyBottomBorder > lineHeight;
    }

    public void removeDeadTanks() {
        enemyTanks.removeIf(GameObject::nothingHasRemained);
    }

    public int getTanksCount() {
        return enemyTanks.size();
    }
}
