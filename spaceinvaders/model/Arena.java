package com.javarush.games.spaceinvaders.model;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.EnemyArmy;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.CoinBullet;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.Brick;

import java.util.ArrayList;
import java.util.List;

/**
 * Everything that lives on the field during one stage: Mario, the enemy army, the projectiles and
 * the bricks. Knows how to update, draw and check the state of that world, but not how a game
 * session or a screen is managed.
 */
public class Arena {
    private static final int MAX_COINS_ON_FIELD = 2;

    private final SpaceInvadersGame game;
    private final Mario mario;
    private final EnemyArmy enemyArmy;
    private final List<Bullet> playerBullets = new ArrayList<>();
    private final List<Bullet> enemyBullets = new ArrayList<>();
    private final List<Brick> bricks;

    public Arena(SpaceInvadersGame game) {
        this.game = game;
        this.mario = new Mario();
        this.enemyArmy = new EnemyArmy(game);
        this.bricks = LevelLayout.createBricks();
    }

    /*
     * Per-turn updates
     */

    public void update() {
        mario.move();
        enemyArmy.move();
        enemyArmy.fireRandomTank();
        moveAll(playerBullets);
        moveAll(enemyBullets);
        moveAll(bricks);
    }

    private void moveAll(List<? extends Movable> objects) {
        for (Movable object : objects) {
            object.move();
        }
    }

    public void resolveCollisions() {
        mario.receiveBullets(enemyBullets);
        enemyArmy.receiveBullets(playerBullets);
        enemyArmy.receiveBullets(enemyBullets);
        for (Brick brick : bricks) {
            brick.bumpIfTouchedBy(mario);
        }
    }

    public void removeDestroyedObjects() {
        enemyArmy.removeDestroyedTanks();
        removeDestroyedBullets(playerBullets);
        removeDestroyedBullets(enemyBullets);
    }

    private void removeDestroyedBullets(List<Bullet> bullets) {
        bullets.removeIf(bullet -> bullet.hasLeftField() || !bullet.isAlive());
    }

    /*
     * Drawing
     */

    public void drawObjects() {
        enemyArmy.draw();
        for (Bullet bullet : playerBullets) {
            bullet.draw();
        }
        for (Brick brick : bricks) {
            brick.draw();
        }
        for (Bullet bullet : enemyBullets) {
            bullet.draw();
        }
        mario.draw();
    }

    /*
     * State of the battle
     */

    public void addPlayerBullet(Bullet bullet) {
        if (bullet instanceof CoinBullet && countCoinsOnField() >= MAX_COINS_ON_FIELD) return;

        playerBullets.add(bullet);
    }

    private long countCoinsOnField() {
        return playerBullets.stream()
                .filter(bullet -> bullet instanceof CoinBullet)
                .count();
    }

    public boolean isMarioDefeated() {
        return !mario.isAlive();
    }

    public boolean isArmyDestroyed() {
        return enemyArmy.isEmpty();
    }

    public boolean hasArmyReachedTheBrickLine() {
        return enemyArmy.isBottomLineReached(LevelLayout.BRICK_ROW_Y);
    }

    /*
     * Objects of the arena
     */

    public Mario getMario() {
        return mario;
    }

    public EnemyArmy getEnemyArmy() {
        return enemyArmy;
    }

    public List<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public List<Bullet> getPlayerBullets() {
        return playerBullets;
    }

    public List<Brick> getBricks() {
        return bricks;
    }
}
