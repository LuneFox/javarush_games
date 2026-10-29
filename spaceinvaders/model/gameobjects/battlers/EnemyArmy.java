package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.StageProgress;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.view.shapes.BossTankShape;
import com.javarush.games.spaceinvaders.view.shapes.TankShape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

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
    /** Скорость въезда танков на поле. */
    private static final double INTRO_SPEED = 2.0;
    /** Дополнительный отступ босса за верхней границей. */
    private static final int BOSS_INTRO_GAP = 20;
    /** Длительность заморозки танков звёздочкой. */
    private static final long STAR_FREEZE_MS = 1000L;

    /** Игра, в которой находится армия. */
    private final SpaceInvadersGame game;
    /** Живые и уничтожаемые танки. */
    private final List<EnemyTank> tanks = new ArrayList<>();
    /** Логика движения строя. */
    private final ArmyMarch march = new ArmyMarch();
    /** Босс армии. */
    private final BossTank boss;
    /** Целевые координаты танков на поле. */
    private final Map<EnemyTank, Double> introTargets = new IdentityHashMap<>();
    /** Ряды танков в порядке выезда: снизу вверх. */
    private final List<Double> introRows = new ArrayList<>();
    /** Индекс текущего выезжающего ряда. */
    private int introRowIndex;
    /** Признак вступительной сцены. */
    private boolean introActive;
    /** Признак выезда босса после обычных танков. */
    private boolean introBossPhase;
    /** Время, до которого танки заморожены звёздочкой. */
    private long frozenUntil;

    /** Создаёт армию из танков и босса. */
    public EnemyArmy(SpaceInvadersGame game) {
        this.game = game;
        spawnTanks();
        this.boss = createBossTank();
        tanks.add(boss);
        march.setDirection(mirroredLevel() ? Direction.LEFT : Direction.RIGHT);
        startIntro();
    }

    /** Проверяет, нужно ли зеркалить поле для текущей стадии. */
    private static boolean mirroredLevel() {
        return StageProgress.getNumber() % 2 == 1;
    }

    /** Прячет танки за верхней границей и запускает вступительную сцену. */
    private void startIntro() {
        introActive = true;
        introBossPhase = false;
        introRowIndex = 0;
        introRows.clear();

        for (EnemyTank tank : tanks) {
            double target = tank.getY();
            introTargets.put(tank, target);
            if (tank == boss) {
                tank.setPosition(tank.getX(), -tank.getHeight() - BOSS_INTRO_GAP);
            } else {
                if (!introRows.contains(target)) {
                    introRows.add(target);
                }
                tank.setPosition(tank.getX(), -tank.getHeight());
            }
        }
        Collections.sort(introRows, Collections.reverseOrder());
    }

    /** Расставляет обычные и красные танки. */
    private void spawnTanks() {
        boolean[] redSlots = chooseRedSlots(redTankCount());
        int index = 0;
        boolean mirrored = mirroredLevel();
        for (int column = 0; column < COLUMNS; column++) {
            for (int row = 0; row < ROWS; row++) {
                int x = column * TANK_STEP;
                if (mirrored) {
                    x = GameField.WIDTH - TankShape.TANK_1[0].length - x;
                }
                int y = row * TANK_STEP + FIRST_ROW_TOP_Y;
                tanks.add(redSlots[index++] ? new RedTank(x, y) : new EnemyTank(x, y));
            }
        }
    }

    /** Возвращает число красных танков для текущей стадии. */
    private int redTankCount() {
        return Math.min(Math.max(0, StageProgress.getNumber() - 1), ROWS * COLUMNS);
    }

    /** Случайно выбирает слоты для красных танков. */
    private boolean[] chooseRedSlots(int count) {
        boolean[] slots = new boolean[ROWS * COLUMNS];
        for (int i = 0; i < count; i++) {
            int slot;
            do {
                slot = game.getRandomNumber(slots.length);
            } while (slots[slot]);
            slots[slot] = true;
        }
        return slots;
    }

    /** Создаёт босса по центру текущего строя обычных танков. */
    private BossTank createBossTank() {
        double left = GameField.WIDTH;
        double right = 0;
        for (EnemyTank tank : tanks) {
            left = Math.min(left, tank.getX());
            right = Math.max(right, tank.getX() + tank.getWidth());
        }

        double center = (left + right) / 2.0;
        double x = center - BossTankShape.BOSS_TANK_1[0].length / 2.0;
        return new BossTank(x, BOSS_ROW_Y);
    }

    /** Рисует все танки. */
    @Override
    public void draw() {
        for (EnemyTank tank : tanks) {
            tank.draw();
        }
    }

    /** Двигает строй или проигрывает вступительную сцену. */
    @Override
    public void move() {
        if (game.isRoundOver()) return;
        if (isFrozen()) return;
        if (introActive) {
            updateIntro();
            return;
        }

        march.advance(tanks);
    }

    /** Двигает танки к позициям, затем выпускает босса. */
    private void updateIntro() {
        if (!introBossPhase) {
            if (introRowIndex >= introRows.size()) {
                introBossPhase = true;
                return;
            }

            double rowTarget = introRows.get(introRowIndex);
            boolean allArrived = true;
            for (EnemyTank tank : tanks) {
                if (tank == boss) continue;

                double target = introTargets.get(tank);
                if (target != rowTarget) continue;
                if (tank.getY() < target) {
                    tank.setPosition(tank.getX(), Math.min(target, tank.getY() + INTRO_SPEED));
                    allArrived = false;
                }
            }
            if (allArrived) {
                introRowIndex++;
            }
            return;
        }

        double target = introTargets.get(boss);
        if (boss.getY() < target) {
            boss.setPosition(boss.getX(), Math.min(target, boss.getY() + INTRO_SPEED));
        } else {
            introActive = false;
        }
    }

    /** Сообщает, идёт ли вступительная сцена. */
    public boolean isIntroActive() {
        return introActive;
    }

    /** Сообщает, выезжает ли сейчас босс. */
    public boolean isBossIntroActive() {
        return introActive && introBossPhase;
    }

    /** Отталкивает танки на шаг назад и замораживает их на секунду. */
    public void pushBackAndFreeze() {
        for (EnemyTank tank : tanks) {
            tank.stepBack(introTargets.get(tank));
        }
        frozenUntil = System.currentTimeMillis() + STAR_FREEZE_MS;
    }

    /** Сообщает, заморожены ли танки звёздочкой. */
    public boolean isFrozen() {
        return System.currentTimeMillis() < frozenUntil;
    }

    /** Заставляет случайный танк выстрелить. */
    public void fireRandomTank() {
        if (isIntroActive() || isFrozen() || game.isRoundOver() || tanks.isEmpty()) return;

        tanks.get(game.getRandomNumber(tanks.size())).shoot();
    }

    /** Сообщает, уничтожен ли босс. */
    public boolean isBossDefeated() {
        return !boss.isAlive();
    }

    /** Взрывает все оставшиеся танки. */
    public void explodeAllTanks() {
        for (EnemyTank tank : tanks) {
            tank.explode();
        }
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

    /** Сообщает, уничтожены ли все танки, даже если анимация ещё не закончилась. */
    public boolean isDefeated() {
        for (EnemyTank tank : tanks) {
            if (tank.isAlive()) {
                return false;
            }
        }
        return true;
    }

    /** Возвращает танки. */
    public List<EnemyTank> getTanks() {
        return tanks;
    }
}
