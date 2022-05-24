package com.javarush.games.racer.model.car;

import com.javarush.engine.cell.Key;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.controller.Control;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.gameobjects.HitBox;
import com.javarush.games.racer.model.gameobjects.Sprite;
import com.javarush.games.racer.model.road.RoadManager;
import com.javarush.games.racer.view.Shapes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeLorean extends GameObject {
    private static final double MAX_ENERGY = 1.21;
    private static final double MAX_GAS = 23.0;
    private static final double PADDING_LEFT = 1;
    private static final double MAX_STEER_SPEED = 2.0;
    private static final double ACCELERATION_GAS = 0.05;
    private static final double ACCELERATION_BRAKE = -0.2;
    private static final double ACCELERATION_RELEASE = -0.025;
    private static final double ENTER_PORTAL_THRESHOLD = 8.80;
    private static final double START_GLOWING_THRESHOLD = 6.0;

    private Direction verDirection;
    private Direction horDirection;
    private Animation animation;
    private double speed;
    private double energy;
    private double gas;
    private double distance;

    private final Portal portal;
    private final List<TireFlame> tireFlames;
    private EnergyPickupIcon energyPickupIcon;

    private enum Animation {
        NORMAL, GLOWING
    }

    public DeLorean() {
        super(PADDING_LEFT, (RacerGame.HEIGHT / 2.0) - (Shapes.DELOREAN_RUN_0.length / 2.0) + 8);
        this.hitBox = new HitBox(6, 9, 28, 20);
        this.verDirection = Direction.NONE;
        this.horDirection = Direction.NONE;
        this.energy = 0;
        this.speed = 0;
        this.gas = MAX_GAS;
        this.distance = 0;
        setNormalAnimation();

        portal = new Portal(this);
        tireFlames = new ArrayList<>(Arrays.asList(
                new TireFlame(TireFlame.Side.RIGHT),
                new TireFlame(TireFlame.Side.LEFT)
        ));
        energyPickupIcon = null;
    }

    public void move() {
        gas();
        steer();
        countDistance();
    }

    public void stop() {
        speed = 0;
    }

    private void gas() {
        if (!isAtLeftmostPosition()) return;

        changeSpeed();
        selectAnimationBasedOnSpeed();
    }

    private void depleteGas() {
        if (horDirection != Direction.RIGHT) return;
        gas = Math.max(gas - 0.02, 0);
    }

    private void changeSpeed() {
        if (horDirection == Direction.RIGHT) accelerate();
        else if (horDirection == Direction.LEFT) brake();
        else if (horDirection == Direction.NONE) release();
    }

    private void accelerate() {
        if (gas > 0) {
            depleteGas();
            speed += ACCELERATION_GAS * (1 - speed * 0.1);
            limitMaxSpeed();
        } else {
            release();
        }
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
            speed = Math.min(START_GLOWING_THRESHOLD - 0.01, speed);
        } else {
            speed = Math.min(ENTER_PORTAL_THRESHOLD + 0.01, speed);
        }
    }

    private void limitMinSpeed() {
        speed = Math.max(0, speed);
    }

    private void selectAnimationBasedOnSpeed() {
        if (speed <= 0) {
            setAnimationDelay(Integer.MAX_VALUE);
        } else if (speed > 0 && speed < START_GLOWING_THRESHOLD) {
            setNormalAnimation();
            setAnimationDelay((int) (10 / speed + 1));
        } else if (speed >= START_GLOWING_THRESHOLD) {
            setGlowingAnimation();
            setAnimationDelay((int) (10 / speed + 1));
        }
    }

    private void setNormalAnimation() {
        if (animation == Animation.NORMAL) return;

        setAnimatedView(Sprite.Loop.ENABLED, Integer.MAX_VALUE,
                Shapes.DELOREAN_RUN_0,
                Shapes.DELOREAN_RUN_1,
                Shapes.DELOREAN_RUN_2,
                Shapes.DELOREAN_RUN_3);
        animation = Animation.NORMAL;
    }

    private void setGlowingAnimation() {
        if (animation == Animation.GLOWING) return;

        setAnimatedView(Sprite.Loop.ENABLED, Integer.MAX_VALUE,
                Shapes.DELOREAN_GLOW_0,
                Shapes.DELOREAN_GLOW_1,
                Shapes.DELOREAN_GLOW_2,
                Shapes.DELOREAN_GLOW_3);
        animation = Animation.GLOWING;
    }

    private void steer() {
        if (!isAtLeftmostPosition()) return;

        checkOffRoad();
        changeVerticalPosition();
    }

    public boolean isAtLeftmostPosition() {
        return x == PADDING_LEFT;
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

        cutSpeedToPercentageOfCurrent(99.5);
    }

    public void cutSpeedToPercentageOfCurrent(double percentage) {
        speed = (speed / 100.0) * percentage;
    }

    private void changeVerticalPosition() {
        if (verDirection == Direction.UP) y -= Math.min(speed, MAX_STEER_SPEED);
        else if (verDirection == Direction.DOWN) y += Math.min(speed, MAX_STEER_SPEED);
    }

    private void countDistance() {
        this.distance += speed / 7.0;
    }

    @Override
    public void draw() {
        if (speed > ENTER_PORTAL_THRESHOLD) {
            moveIntoPortal();
        }

        super.draw();


        if (energyPickupIcon != null) {
            energyPickupIcon.draw();
        }

        tireFlames.forEach(GameObject::draw);
        portal.draw();
    }

    private void moveIntoPortal() {
        final int gapBetweenCarAndPortal = 3;
        final int shift = 3;

        if (x == PADDING_LEFT + gapBetweenCarAndPortal) {
            maskIn(shift);
        }

        x += shift;
    }

    public boolean passedPortal() {
        return x - portal.x > 5;
    }

    public void addEnergy() {
        energy += MAX_ENERGY / 10.0;
    }

    public boolean hasMaxEnergy() {
        return energy >= MAX_ENERGY;
    }

    public double getUsedGasInLitres() {
        return (MAX_GAS - gas) / 10.0;
    }


    /*
     * Plain getters, setters
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

    public double getEnergy() {
        return energy;
    }

    public double getGas() {
        return gas;
    }

    public double getDistance() {
        return distance;
    }

    public void setEnergyPickupIcon(EnergyPickupIcon energyPickupIcon) {
        this.energyPickupIcon = energyPickupIcon;
    }
}
