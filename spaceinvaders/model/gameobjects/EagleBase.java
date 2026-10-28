package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;

import java.util.ArrayList;
import java.util.List;

/** Маленькая база с орлом, опускающаяся после смерти босса. */
public class EagleBase implements Movable, Drawable {
    /** Ширина базы. */
    private static final int BASE_WIDTH = 18;
    /** Высота базы. */
    private static final int BASE_HEIGHT = 15;
    /** Размер одной клетки кирпича. */
    private static final int CELL_SIZE = 3;
    /** Смещение орла по X внутри базы. */
    private static final int EAGLE_OFFSET_X = 3;
    /** Смещение орла по Y внутри базы. */
    private static final int EAGLE_OFFSET_Y = 0;
    /** Координата X базы. */
    private static final int BASE_X = (GameField.WIDTH - BASE_WIDTH) / 2;
    /** Стартовая координата Y базы за верхней границей. */
    private static final int START_Y = -BASE_HEIGHT;
    /** Координата Y, на которой база останавливается. */
    private static final int FINAL_Y = 0;
    /** Шаг опускания базы за ход. */
    private static final int DESCENT_STEP = 1;

    /** Рыжие кирпичи базы. */
    private final List<BaseBrick> bricks = new ArrayList<>();
    /** Орёл внутри базы. */
    private final Eagle eagle = new Eagle();

    /** Текущая координата Y базы. */
    private double y = START_Y;
    /** Признак того, что база опустилась. */
    private boolean landed;
    /** Признак того, что орёл уничтожен. */
    private boolean defeated;

    /** Создаёт базу с кирпичами вокруг орла. */
    public EagleBase() {
        createBricks();
        updatePositions();
    }

    /** Расставляет кирпичи снизу и по бокам орла, оставляя верх открытым. */
    private void createBricks() {
        for (int column = 0; column < 6; column++) {
            addBrick(column, 4);
        }
        for (int row = 0; row < 4; row++) {
            addBrick(0, row);
            addBrick(5, row);
        }
    }

    /** Добавляет кирпич в заданную клетку базы. */
    private void addBrick(int column, int row) {
        bricks.add(new BaseBrick(column * CELL_SIZE, row * CELL_SIZE));
    }

    /** Обновляет положение всех частей базы. */
    private void updatePositions() {
        for (BaseBrick brick : bricks) {
            brick.updatePosition(BASE_X, y);
        }
        eagle.setPosition(BASE_X + EAGLE_OFFSET_X, y + EAGLE_OFFSET_Y);
    }

    /** Опускает базу к верхней границе экрана. */
    @Override
    public void move() {
        if (defeated || y >= FINAL_Y) return;

        y = Math.min(FINAL_Y, y + DESCENT_STEP);
        updatePositions();
        landed = y >= FINAL_Y;
    }

    /** Рисует кирпичи и орла. */
    @Override
    public void draw() {
        if (y + BASE_HEIGHT < 0) return;

        for (BaseBrick brick : bricks) {
            brick.draw();
        }
        eagle.draw();
    }

    /** Обрабатывает попадания пуль по кирпичам и орлу. */
    public void receiveBullets(List<Bullet> bullets) {
        if (!landed || defeated) return;

        for (BaseBrick brick : bricks) {
            for (Bullet bullet : bullets) {
                destroyBrickIfHit(brick, bullet);
            }
        }

        if (eagle.isAlive()) {
            for (Bullet bullet : bullets) {
                destroyEagleIfHit(bullet);
            }
        }

        bricks.removeIf(brick -> !brick.isAlive());
        if (!eagle.isAlive()) {
            defeated = true;
        }
    }

    /** Уничтожает кирпич и снаряд при столкновении. */
    private void destroyBrickIfHit(BaseBrick brick, Bullet bullet) {
        if (!bullet.isLethalToEnemies()) return;
        if (!bullet.isAlive() || !brick.isAlive()) return;
        if (!bullet.collidesWith(brick, Mirror.NONE)) return;

        brick.destroy();
        bullet.destroyOnBaseHit();
    }

    /** Уничтожает орла и снаряд при столкновении. */
    private void destroyEagleIfHit(Bullet bullet) {
        if (!bullet.isLethalToEnemies()) return;
        if (!bullet.isAlive() || !eagle.isAlive()) return;
        if (!bullet.collidesWith(eagle, Mirror.NONE)) return;

        eagle.takeHit();
        bullet.destroyOnBaseHit();
    }

    /** Взрывает орла, когда уничтожен последний танк. */
    public void destroyEagle() {
        if (defeated || !eagle.isAlive()) return;

        if (!landed) {
            y = FINAL_Y;
            updatePositions();
            landed = true;
        }
        eagle.takeHit();
        defeated = true;
    }

    /** Сообщает, опустилась ли база. */
    public boolean isLanded() {
        return landed;
    }

    /** Сообщает, уничтожен ли орёл. */
    public boolean isDefeated() {
        return defeated;
    }
}
