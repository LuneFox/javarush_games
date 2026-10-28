package com.javarush.games.spaceinvaders.model.gameobjects.battlers;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.Mirror;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.gameobjects.Movable;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletFactory;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.BulletType;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bonuses.Bonus;
import com.javarush.games.spaceinvaders.model.gameobjects.physics.JumpPhysics;
import com.javarush.games.spaceinvaders.model.gameobjects.physics.JumpPhysicsBuilder;
import com.javarush.games.spaceinvaders.view.shapes.MarioShape;

import java.util.List;

/** Марио: ходьба с инерцией, прыжки, бонус и стрельба. */
public class Mario extends Battler implements Movable {
    /** Максимальный запас здоровья Марио. */
    private static final int HIT_POINTS = 5;
    /** Допустимый выход за края поля при ходьбе. */
    private static final int WALKING_MARGIN = 4;
    /** Максимальная сила прыжка. */
    private static final int MAX_JUMP_ENERGY = 5;
    /** Верхняя граница прыжка. */
    private static final int JUMP_CEILING_Y = 66;
    /** Скорость подъёма в прыжке. */
    private static final int JUMP_RAISE_SPEED = 4;
    /** Скорость падения. */
    private static final int JUMP_DESCEND_SPEED = 2;
    /** Длительность анимации смерти. */
    private static final int DEATH_ANIMATION_FRAMES = 30;
    /** Кадр начала подъёма при смерти. */
    private static final int DEATH_RISE_FROM_FRAME = 10;
    /** Кадр окончания подъёма при смерти. */
    private static final int DEATH_RISE_UNTIL_FRAME = 15;
    /** Шаг подъёма при смерти. */
    private static final int DEATH_RISE_STEP = -2;
    /** Шаг падения при смерти. */
    private static final int DEATH_FALL_STEP = 3;
    /** Зазор от головы Марио до снаряда. */
    private static final int BULLET_TOP_GAP = 4;
    /** Сколько миллисекунд показывать полоску здоровья после попадания. */
    private static final long HEALTH_BAR_DURATION_MS = 1000;
    /** Высота полоски здоровья. */
    private static final int HEALTH_BAR_HEIGHT = 2;
    /** Зазор между ногами Марио и полоской здоровья. */
    private static final int HEALTH_BAR_GAP = 1;

    /** Физика ходьбы. */
    private final MarioWalk walk = new MarioWalk();
    /** Аниматор поз. */
    private final MarioAnimator animator = new MarioAnimator(this);
    /** Физика прыжка. */
    private final JumpPhysics jumpPhysics;
    /** Левая граница ходьбы. */
    private final int leftWalkingBound;
    /** Правая граница ходьбы. */
    private final int rightWalkingBound;

    /** Текущее направление ходьбы. */
    private Direction walkingDirection = Direction.NONE;
    /** Направление взгляда. */
    private Direction facing = Direction.LEFT;
    /** Подобранный бонус или null. */
    private Bonus bonus;
    /** Счётчик кадров анимации смерти и победы. */
    private int animationFrame;
    /** Время, до которого видна полоска здоровья. */
    private long healthBarHideTime;

    /** Создаёт Марио в точке появления. */
    public Mario() {
        super(spawnX(), spawnY());
        this.hitPoints = HIT_POINTS;
        animator.stand();
        this.leftWalkingBound = -WALKING_MARGIN;
        this.rightWalkingBound = GameField.WIDTH - getWidth() + WALKING_MARGIN;
        this.jumpPhysics = new JumpPhysicsBuilder(this)
                .setMaxJumpEnergy(MAX_JUMP_ENERGY)
                .setFloorLevel(GameField.groundLevelFor(getHeight()))
                .setCeilingLevel(JUMP_CEILING_Y)
                .setRaiseSpeed(JUMP_RAISE_SPEED)
                .setDescendSpeed(JUMP_DESCEND_SPEED)
                .build();
    }

    /** Возвращает X точки появления. */
    private static double spawnX() {
        return GameField.WIDTH - MarioShape.STAND[0].length - 3;
    }

    /** Возвращает Y точки появления. */
    private static int spawnY() {
        return GameField.groundLevelFor(MarioShape.STAND.length);
    }

    /*
     * Движение
     */

    /** Делает шаг: ходьба, границы и прыжок. */
    @Override
    public void move() {
        if (!isAlive()) return;

        updateFacing();
        boolean braking = walk.update(walkingDirection);
        showWalkPose(braking);
        setPosition(getX() + walk.getSpeed(), getY());
        keepInBounds();

        jumpPhysics.update();
        if (jumpPhysics.isInAir()) {
            animator.jump();
        }
    }

    /** Выбирает позу по скорости и торможению. */
    private void showWalkPose(boolean braking) {
        if (braking) {
            animator.brake();
        } else if (walk.isMoving()) {
            animator.walk();
        } else {
            animator.stand();
        }
    }

    /** Не даёт Марио выйти за границы поля. */
    private void keepInBounds() {
        if (getX() < leftWalkingBound) {
            setPosition(leftWalkingBound, getY());
        } else if (getX() > rightWalkingBound) {
            setPosition(rightWalkingBound, getY());
        }
    }

    /** Начинает прыжок. */
    public void jump() {
        jumpPhysics.startJump();
    }

    /** Задаёт направление ходьбы. */
    public void walkTowards(Direction direction) {
        this.walkingDirection = direction;
    }

    /** Возвращает направление ходьбы. */
    public Direction getWalkingDirection() {
        return walkingDirection;
    }

    /** Возвращает направление взгляда. */
    public Direction getFacing() {
        return facing;
    }

    /*
     * Бонус
     */

    /** Подбирает бонус. */
    public void pickUp(Bonus bonus) {
        this.bonus = bonus;
    }

    /** Сообщает, есть ли у Марио бонус. */
    public boolean hasBonus() {
        return bonus != null;
    }

    /** Использует бонус по нажатию пробела. */
    public void useBonus() {
        if (!isAlive() || bonus == null) return;

        Bonus usedBonus = bonus;
        bonus = null;
        usedBonus.activate();
    }

    /** Запускает снаряд над головой Марио. */
    public void launchProjectile(BulletType type, int[][] projectileImage) {
        if (!isAlive()) return;

        double x = getX() + (getWidth() - projectileImage[0].length) / 2.0;
        double y = getY() - projectileImage.length + BULLET_TOP_GAP;
        game.addPlayerBullet(BulletFactory.create(type, x, y));
    }

    /*
     * Урон и смерть
     */

    /** Обрабатывает попадания вражеских пуль. */
    public void receiveBullets(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            if (isHitBy(bullet)) {
                takeHit();
                bullet.destroy();
            }
        }
    }

    /** Проверяет попадание пули в Марио. */
    private boolean isHitBy(Bullet bullet) {
        if (!isAlive() || !bullet.isAlive()) return false;

        // Отражать нужно Марио: пуля рисуется без отражения
        return bullet.collidesWith(this, Mirror.of(facing));
    }

    /** Применяет попадание: теряет бонус или здоровье и показывает HP. */
    @Override
    public void takeHit() {
        if (!isAlive()) return;

        // Бонус принимает удар на себя и забирает свои очки
        if (hasBonus()) {
            bonus = null;
            Score.removeBonusPickup();
            showHealthBar();
            return;
        }

        super.takeHit();
        showHealthBar();
    }

    /** Показывает полоску здоровья на секунду. */
    private void showHealthBar() {
        healthBarHideTime = System.currentTimeMillis() + HEALTH_BAR_DURATION_MS;
    }

    /** Проигрывает анимацию смерти. */
    public void playDeathAnimation() {
        if (animationFrame > DEATH_ANIMATION_FRAMES) return;

        animationFrame++;
        animator.die();
        bonus = null;

        if (animationFrame >= DEATH_RISE_FROM_FRAME) {
            double step = (animationFrame < DEATH_RISE_UNTIL_FRAME) ? DEATH_RISE_STEP : DEATH_FALL_STEP;
            setPosition(getX(), getY() + step);
        }
    }

    /** Проигрывает анимацию победы. */
    public void playVictoryAnimation() {
        walkTowards(Direction.NONE);
        animator.victoryFrame(animationFrame);
        animationFrame++;
    }

    /*
     * Отрисовка
     */

    /** Рисует Марио, полоску здоровья и иконку бонуса. */
    @Override
    public void draw() {
        updateFacing();
        super.draw(Mirror.of(facing));
        drawHealthBar();
        drawBonusIcon();
    }

    /** Рисует полоску здоровья под ногами, пока она видна. */
    private void drawHealthBar() {
        if (!isHealthBarVisible()) return;

        int barX = (int) getX();
        int barY = (int) getY() + getHeight() + HEALTH_BAR_GAP;
        int barWidth = getWidth();

        for (int x = 0; x < barWidth; x++) {
            for (int y = 0; y < HEALTH_BAR_HEIGHT; y++) {
                game.getScreen().setPixel(barX + x, barY + y, Color.DARKGRAY);
            }
        }

        int filledWidth = barWidth * hitPoints / HIT_POINTS;
        Color barColor = healthColor();
        for (int x = 0; x < filledWidth; x++) {
            for (int y = 0; y < HEALTH_BAR_HEIGHT; y++) {
                game.getScreen().setPixel(barX + x, barY + y, barColor);
            }
        }
    }

    /** Сообщает, видна ли полоска здоровья. */
    private boolean isHealthBarVisible() {
        return isAlive() && System.currentTimeMillis() < healthBarHideTime;
    }

    /** Возвращает цвет полоски по текущему здоровью. */
    private Color healthColor() {
        if (hitPoints >= 4) return Color.GREEN;
        if (hitPoints >= 2) return Color.YELLOW;
        return Color.RED;
    }

    /** Обновляет направление взгляда. */
    private void updateFacing() {
        if (walkingDirection == Direction.RIGHT) {
            facing = Direction.RIGHT;
        } else if (walkingDirection == Direction.LEFT) {
            facing = Direction.LEFT;
        }
    }

    /** Рисует иконку бонуса над головой. */
    private void drawBonusIcon() {
        if (bonus == null) return;

        double iconX = getX() + (getWidth() - bonus.getIconWidth()) / 2.0;
        bonus.drawIconAt(iconX, getY() - 3);
    }
}
