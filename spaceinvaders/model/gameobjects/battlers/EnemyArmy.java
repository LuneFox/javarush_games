package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.view.shapes.BossTankShape;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

import java.util.ArrayList;
import java.util.List;

public class EnemyArmy implements Movable, Drawable {
    private static final int ROWS = 3;
    private static final int COLUMNS = 10;
    private static final int STEP = TankShape.TANK_1.length + 1;

    private final SpaceInvadersGame game;
    private List<EnemyTank> enemyTanks;
    private Direction direction;
    private double speed;

    public EnemyArmy(SpaceInvadersGame game) {
        createTanks();
        this.direction = Direction.RIGHT;
        this.game = game;
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
        EnemyTank tank = new EnemyTank(tankX, tankY);
        enemyTanks.add(tank);
    }

    private void addBossTank() {
        double bossTankX = (STEP * COLUMNS / 2.0) - (BossTankShape.BOSS_TANK_1.length / 2.0);
        double bossTankY = 1;
        BossTank bossTank = new BossTank(bossTankX, bossTankY);
        enemyTanks.add(bossTank);
    }

    public void draw() {
        enemyTanks.forEach(GameObject::draw);
    }

    public void move() {
        if (game.isStopped()) return;
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
        if (direction == Direction.LEFT && reachedLeftBorder()) {
            direction = Direction.RIGHT;
            return true;
        } else if (direction == Direction.RIGHT && reachedRightBorder()) {
            direction = Direction.LEFT;
            return true;
        }
        return false;
    }

    private boolean reachedLeftBorder() {
        return enemyTanks.stream()
                .mapToDouble(tank -> tank.x)
                .min()
                .orElse(SpaceInvadersGame.WIDTH) <= 0;
    }

    private boolean reachedRightBorder() {
        return enemyTanks.stream()
                .mapToDouble(tank -> tank.x + tank.getWidth())
                .max()
                .orElse(0) >= SpaceInvadersGame.WIDTH;
    }

    public void attack() {
        if (game.isStopped()) return;
        if (enemyTanks.isEmpty()) return;

        int randomTankNumber = (int) (Math.random() * enemyTanks.size());
        enemyTanks.get(randomTankNumber).shoot();
    }

    public void verifyHit(List<Bullet> bullets) {
        if (bullets.isEmpty()) return;
        for (EnemyTank tank : enemyTanks) {
            for (Bullet bullet : bullets) {
                destroyTankAndBulletOnCollision(tank, bullet);
            }
        }
    }

    private void destroyTankAndBulletOnCollision(EnemyTank enemyTank, Bullet bullet) {
        if (!bullet.canKillEnemies) return;
        if (!bullet.isAlive) return;
        if (!enemyTank.isAlive) return;
        if (!bullet.collidesWith(enemyTank, Mirror.NONE)) return;

        enemyTank.kill();
        bullet.kill();
    }

    public boolean reachedBottomLine(double lineHeight) {
        return enemyTanks.stream()
                .mapToDouble(tank -> tank.y + tank.getHeight())
                .max()
                .orElse(0) >= lineHeight - 1;
    }

    public void removeDeadTanks() {
        enemyTanks.removeIf(GameObject::nothingHasRemained);
    }

    public int getTanksCount() {
        return enemyTanks.size();
    }

    public List<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }
}
