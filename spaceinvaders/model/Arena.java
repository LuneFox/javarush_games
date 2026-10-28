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

/** Всё, что живёт на поле один раунд: Марио, армия, пули и кирпичи. */
public class Arena {
    private static final int MAX_COINS_ON_FIELD = 2;

    private final SpaceInvadersGame game;
    private final Mario mario;
    private final EnemyArmy enemyArmy;
    private final List<Bullet> playerBullets = new ArrayList<>();
    private final List<Bullet> enemyBullets = new ArrayList<>();
    private final List<Brick> bricks;

    /** Создаёт поле с Марио, армией и кирпичами. */
    public Arena(SpaceInvadersGame game) {
        this.game = game;
        this.mario = new Mario();
        this.enemyArmy = new EnemyArmy(game);
        this.bricks = LevelLayout.createBricks();
    }

    /*
     * Обновление за ход
     */

    /** Двигает все объекты поля на один шаг. */
    public void update() {
        mario.move();
        enemyArmy.move();
        enemyArmy.fireRandomTank();
        moveAll(playerBullets);
        moveAll(enemyBullets);
        moveAll(bricks);
    }

    /** Двигает список объектов. */
    private void moveAll(List<? extends Movable> objects) {
        for (Movable object : objects) {
            object.move();
        }
    }

    /** Обрабатывает столкновения объектов поля. */
    public void resolveCollisions() {
        mario.receiveBullets(enemyBullets);
        enemyArmy.receiveBullets(playerBullets);
        enemyArmy.receiveBullets(enemyBullets);
        for (Brick brick : bricks) {
            brick.bumpIfTouchedBy(mario);
        }
    }

    /** Убирает уничтоженные танки и пули. */
    public void removeDestroyedObjects() {
        enemyArmy.removeDestroyedTanks();
        removeDestroyedBullets(playerBullets);
        removeDestroyedBullets(enemyBullets);
    }

    /** Убирает улетевшие и уничтоженные пули. */
    private void removeDestroyedBullets(List<Bullet> bullets) {
        bullets.removeIf(bullet -> bullet.hasLeftField() || !bullet.isAlive());
    }

    /*
     * Отрисовка
     */

    /** Рисует объекты поля в порядке слоёв. */
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
     * Состояние боя
     */

    /** Добавляет пулю игрока с учётом лимита монет. */
    public void addPlayerBullet(Bullet bullet) {
        if (bullet instanceof CoinBullet && countCoinsOnField() >= MAX_COINS_ON_FIELD) return;

        playerBullets.add(bullet);
    }

    /** Считает монеты на поле. */
    private long countCoinsOnField() {
        return playerBullets.stream()
                .filter(bullet -> bullet instanceof CoinBullet)
                .count();
    }

    /** Сообщает, побеждён ли Марио. */
    public boolean isMarioDefeated() {
        return !mario.isAlive();
    }

    /** Сообщает, уничтожена ли армия. */
    public boolean isArmyDestroyed() {
        return enemyArmy.isEmpty();
    }

    /** Сообщает, дошла ли армия до линии кирпичей. */
    public boolean hasArmyReachedTheBrickLine() {
        return enemyArmy.isBottomLineReached(LevelLayout.BRICK_ROW_Y);
    }

    /*
     * Объекты поля
     */

    /** Возвращает Марио. */
    public Mario getMario() {
        return mario;
    }

    /** Возвращает армию врагов. */
    public EnemyArmy getEnemyArmy() {
        return enemyArmy;
    }

    /** Возвращает вражеские пули. */
    public List<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    /** Возвращает пули игрока. */
    public List<Bullet> getPlayerBullets() {
        return playerBullets;
    }

    /** Возвращает кирпичи уровня. */
    public List<Brick> getBricks() {
        return bricks;
    }
}
