package com.javarush.games.racer.model;

import com.javarush.engine.cell.Key;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.controller.Control;
import com.javarush.games.racer.model.road.RoadManager;
import com.javarush.games.racer.view.Shapes;

public class DeLorean extends GameObject {
    public static final double MAX_ENERGY = 1.21;
    private static final double PADDING_LEFT = 1;
    private static final double MAX_STEER_SPEED = 2.0;
    private static final double ACCELERATION_GAS = 0.05;
    private static final double ACCELERATION_BRAKE = -0.2;
    private static final double ACCELERATION_RELEASE = -0.025;
    private static final double ENTER_PORTAL_POINT = 8.80;
    private static final double START_GLOWING_POINT = 6.0;

    private Direction verDirection;
    private Direction horDirection;
    private Animation animation;
    private double speed;
    private double energy;

    private enum Animation {
        NORMAL, GLOWING
    }

    public DeLorean() {
        super(PADDING_LEFT, (RacerGame.HEIGHT / 2.0) - (Shapes.DELOREAN_RUN_0.length / 2.0) + 8);
        this.hitBox = new HitBox(9, 6, 20, 28);
        this.verDirection = Direction.NONE;
        this.horDirection = Direction.NONE;
        this.energy = 0;
        this.speed = 0;
        setNormalAnimation();
    }

    public void steer() {
        if (!isAtLeftmostPosition()) return;

        checkOffRoad();
        changeVerticalPosition();
    }

    public boolean isAtLeftmostPosition() {
        return this.x == PADDING_LEFT;
    }

    private void checkOffRoad() {
        final int upperLimit = RoadManager.UPPER_BORDER - 8;
        final int lowerLimit = RoadManager.LOWER_BORDER - getHeight();

        if (y >= upperLimit && y <= lowerLimit) return;

        if (y < upperLimit) {
            y = upperLimit;
        } else if (y > lowerLimit) {
            y = lowerLimit;
        }

        slowDownFromDirt();
    }

    private void slowDownFromDirt() {
        speed = (speed / 100) * 99.5;
    }

    private void changeVerticalPosition() {
        if (verDirection == Direction.UP)
            y -= Math.min(speed, MAX_STEER_SPEED);
        else if (verDirection == Direction.DOWN)
            y += Math.min(speed, MAX_STEER_SPEED);
    }

    public void gas() {
        if (!isAtLeftmostPosition()) return;

        changeSpeed();
        selectAnimationBasedOnSpeed();
    }

    private void changeSpeed() {
        if (horDirection == Direction.RIGHT)
            accelerate();
        else if (horDirection == Direction.LEFT)
            brake();
        else if (horDirection == Direction.NONE)
            release();
    }

    private void accelerate() {
        RacerGame.allowCountTime(); // TODO: Перенести активацию на пробег?
        speed += ACCELERATION_GAS * (1 - speed * 0.1);
        limitMaxSpeed();
    }

    private void brake() {
        speed += ACCELERATION_BRAKE;
        limitMinSpeed();
    }

    private void release() {
        speed += ACCELERATION_RELEASE;
        limitMinSpeed();
    }

    private void limitMaxSpeed() {
        if (energy < MAX_ENERGY) {
            speed = Math.min(START_GLOWING_POINT - 0.1, speed);
        } else {
            speed = Math.min(ENTER_PORTAL_POINT + 0.1, speed);
        }
    }

    private void limitMinSpeed() {
        speed = Math.max(0, speed);
    }

    private void selectAnimationBasedOnSpeed() {
        if (speed <= 0) {
            setAnimationDelay(Integer.MAX_VALUE);
        } else if (speed > 0 && speed < START_GLOWING_POINT) {
            setNormalAnimation();
            setAnimationDelay((int) (10 / speed + 1));
        } else if (speed >= START_GLOWING_POINT) {
            setGlowingAnimation();
            setAnimationDelay((int) (10 / speed + 1));
        }
    }

    public void setNormalAnimation() {
        if (animation == Animation.NORMAL) return;
        setAnimatedView(Sprite.Loop.ENABLED, Integer.MAX_VALUE,
                Shapes.DELOREAN_RUN_0,
                Shapes.DELOREAN_RUN_1,
                Shapes.DELOREAN_RUN_2,
                Shapes.DELOREAN_RUN_3);
        this.animation = Animation.NORMAL;
    }

    public void setGlowingAnimation() {
        if (animation == Animation.GLOWING) return;
        setAnimatedView(Sprite.Loop.ENABLED, Integer.MAX_VALUE,
                Shapes.DELOREAN_GLOW_0,
                Shapes.DELOREAN_GLOW_1,
                Shapes.DELOREAN_GLOW_2,
                Shapes.DELOREAN_GLOW_3);
        this.animation = Animation.GLOWING;
    }

    @Override
    public void draw() {
        if (speed > ENTER_PORTAL_POINT) {
            moveTowardsPortal();
        }

        super.draw();
    }

    private void moveTowardsPortal() {
        int shift = 3;

        if (!isAtLeftmostPosition()) {
            maskIn(shift);
        }

        this.x += shift;
    }


    /**
     * Getters, setters
     */

    public Direction getHorDirection() {
        return horDirection;
    }

    @Control({Key.LEFT, Key.RIGHT})
    public void setHorDirection(Direction horDirection) {
        this.horDirection = horDirection;
    }

    public Direction getVerDirection() {
        return verDirection;
    }

    @Control({Key.UP, Key.DOWN})
    public void setVerDirection(Direction verDirection) {
        this.verDirection = verDirection;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }
}
