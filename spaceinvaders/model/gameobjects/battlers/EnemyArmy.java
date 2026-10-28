package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.view.shapes.BossTankShape;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

import java.util.ArrayList;
import java.util.List;

/** Армия врагов: создание танков и команды строю. */
public class EnemyArmy implements Movable, Drawable {
    /** Количество рядов обычных танков. */
    private static final int ROWS = 3;
    /** Количество колонок обычных танков. */
    private static final int COLUMNS = 10;
    /** Шаг между танками по X и Y. */
    private static final int TANK_STEP = TankShape.TANK_1.length + 1;
    /** Верхняя координата первого ряда. */
    private static final int FIRST_ROW_TOP_Y = 15;
    /** Верхняя координата босса. */
    private static final int BOSS_ROW_Y = 1;

    /** Игра, в которой находится армия. */
    private final SpaceInvadersGame game;
    /** Живые и уничтожаемые танки. */
    private final List<EnemyTank> tanks = new ArrayList<>();
    /** Логика движения строя. */
    private final ArmyMarch march = new ArmyMarch();

    /** Создаёт армию из танков и босса. */
    public EnemyArmy(SpaceInvadersGame game) {
        this.game = game;
        spawnTanks();
        tanks.add(createBossTank());
    }

    /** Расставляет обычные танки. */
    private void spawnTanks() {
        for (int column = 0; column < COLUMNS; column++) {
            for (int row = 0; row < ROWS; row++) {
                int x = column * TANK_STEP;
                int y = row * TANK_STEP + FIRST_ROW_TOP_Y;
                tanks.add(new EnemyTank(x, y));
            }
        }
    }

    /** Создаёт босса по центру. */
    private BossTank createBossTank() {
        double x = (TANK_STEP * COLUMNS / 2.0) - (BossTankShape.BOSS_TANK_1[0].length / 2.0);
        return new BossTank(x, BOSS_ROW_Y);
    }

    /** Рисует все танки. */
    @Override
    public void draw() {
        for (EnemyTank tank : tanks) {
            tank.draw();
        }
    }

    /** Двигает строй. */
    @Override
    public void move() {
        if (game.isRoundOver()) return;

        march.advance(tanks);
    }

    /** Заставляет случайный танк выстрелить. */
    public void fireRandomTank() {
        if (game.isRoundOver() || tanks.isEmpty()) return;

        tanks.get(game.getRandomNumber(tanks.size())).shoot();
    }

    /** Обрабатывает попадания пуль по танкам. */
    public void receiveBullets(List<Bullet> bullets) {
        for (EnemyTank tank : tanks) {
            for (Bullet bullet : bullets) {
                destroyTankIfHit(tank, bullet);
            }
        }
    }

    /** Уничтожает танк и пулю при столкновении. */
    private void destroyTankIfHit(EnemyTank tank, Bullet bullet) {
        if (!bullet.isLethalToEnemies()) return;
        if (!bullet.isAlive() || !tank.isAlive()) return;
        if (!bullet.collidesWith(tank, Mirror.NONE)) return;

        tank.takeHit();
        bullet.destroy();
    }

    /** Проверяет, дошёл ли строй до линии. */
    public boolean isBottomLineReached(double lineY) {
        for (EnemyTank tank : tanks) {
            if (tank.getY() + tank.getHeight() >= lineY - 1) {
                return true;
            }
        }
        return false;
    }

    /** Убирает уничтоженные танки. */
    public void removeDestroyedTanks() {
        tanks.removeIf(GameObject::canBeRemoved);
    }

    /** Сообщает, пуста ли армия. */
    public boolean isEmpty() {
        return tanks.isEmpty();
    }

    /** Возвращает танки. */
    public List<EnemyTank> getTanks() {
        return tanks;
    }
}
