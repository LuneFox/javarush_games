package com.javarush.games.racer.model;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Key;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.controller.Control;
import com.javarush.games.racer.model.road.RoadManager;
import com.javarush.games.racer.view.Shapes;

public class DeLorean extends GameObject {
    public static final double MAX_SPEED = 8.89;
    public static final double MAX_ENERGY = 1.21;
    private static final double MAX_STEER = 2.0;
    private static final double GLOW_POINT = 6.0;
    private static final double BOOST = 0.05;
    private static final double BRAKE = 0.2;
    private static final double SLOWDOWN = 0.025;
    private static final double LEFT_PADDING = 3;

    private Direction verticalDirection;
    private Direction horizontalDirection;
    private Animation animation;
    private double speed;
    private double acceleration;
    private double energy;

    private enum Animation {
        NORMAL, GLOWING
    }

    public DeLorean() {
        super(3, (RacerGame.HEIGHT / 2.0) - (Shapes.DELOREAN_RUN_0.length / 2.0) + 8);
        this.hitBox = new HitBox(9, 6, 20, 28);
        this.verticalDirection = Direction.NONE;
        this.horizontalDirection = Direction.NONE;

        this.energy = 1.21;
        this.speed = 8.5;

        setNormalAnimation();
    }

    public void steer() {
        if (!isInDefaultPlace()) return;

        checkOffRoad();
        changeVerticalPosition();
    }

    private boolean isInDefaultPlace() {
        return this.x == LEFT_PADDING;
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
        if (verticalDirection == Direction.UP)
            y -= Math.min(speed, MAX_STEER);
        else if (verticalDirection == Direction.DOWN)
            y += Math.min(speed, MAX_STEER);
    }

    public void gas() {
        if (!isInDefaultPlace()) return;

        changeSpeed();
        changeAnimationBasedOnSpeed();
    }

    private void changeSpeed() {
        if (horizontalDirection == Direction.RIGHT)
            accelerate();
        else if (horizontalDirection == Direction.LEFT)
            brake();
        else if (horizontalDirection == Direction.NONE)
            slowDown();
    }

    private void accelerate() {
        RacerGame.allowCountTime = true;
        acceleration = BOOST * (1 - speed * 0.1);
        speed += acceleration;
        limitSpeedBeforeMaxEnergy();
    }

    private void limitSpeedBeforeMaxEnergy() {
        if (speed >= GLOW_POINT - 0.1 && energy < MAX_ENERGY) {
            speed = GLOW_POINT - 0.1;
        }
    }

    private void brake() {
        acceleration = BRAKE;
        speed -= acceleration;
        limitSpeedBelowZero();
    }

    private void slowDown() {
        acceleration = SLOWDOWN;
        speed -= acceleration;
        limitSpeedBelowZero();
    }

    private void limitSpeedBelowZero() {
        if (speed < 0) {
            speed = 0;
        }
    }

    private void changeAnimationBasedOnSpeed() {
        if (speed <= 0) {
            setAnimationDelay(Integer.MAX_VALUE);
        } else if (speed > 0 && speed < GLOW_POINT) {
            setNormalAnimation();
            setAnimationDelay((int) (10 / speed + 1));
        } else if (speed >= GLOW_POINT) {
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
        thrustForwardAtMaxSpeed();
        if (isInDefaultPlace()) {
            super.draw();
        } else {
            drawDisappearingInPortal();
        }
        stopGameAfterPassingPortal();
    }

    private void thrustForwardAtMaxSpeed() {
        if (speed > MAX_SPEED - 0.09 && !game.isStopped) {
            this.x += 3;
        }
    }

    private void drawDisappearingInPortal() {
        // TODO: старая логика исчезания в портале, нужно придумать альтернативу
        int shift = 6;
        int[][] matrix = getMatrix();

        for (int i = 0; i < getWidth() - (this.x - shift); i++) {
            for (int j = 0; j < getHeight(); j++) {
                int colorIndex = matrix[j][i];
                game.display.drawPixel((int) x + i, (int) y + j, Color.values()[colorIndex]);
            }
        }
    }

    private void stopGameAfterPassingPortal() {
        Portal portal = game.getPortal();

        if (this.x > portal.x + 5) {
            speed = 0;
            game.isStopped = true;
        }
    }

    /**
     * Getters, setters, etc.
     */

    public Direction getVerticalDirection() {
        return verticalDirection;
    }

    public Direction getHorizontalDirection() {
        return horizontalDirection;
    }

    public double getSpeed() {
        return speed;
    }

    @Control({Key.UP, Key.DOWN})
    public void setVerticalDirection(Direction verticalDirection) {
        this.verticalDirection = verticalDirection;
    }

    @Control({Key.LEFT, Key.RIGHT})
    public void setHorizontalDirection(Direction horizontalDirection) {
        this.horizontalDirection = horizontalDirection;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getEnergy() {
        return energy;
    }
}
