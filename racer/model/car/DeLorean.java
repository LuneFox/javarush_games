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

/**
 * The player's DeLorean: the car the player steers down a road that scrolls to the left.
 *
 * <p>The car normally stays at {@code x == 1} while the world moves, so {@link #getSpeed()} is
 * "world pixels per frame", not a real velocity; the HUD shows {@code speed * 10} as mph. Fuel
 * ({@code gas}) only gates acceleration, while energy (10 pickups) raises the speed cap and
 * unlocks the portal. The car also owns and draws the objects attached to it: its portal, thrust
 * fire, stop signal and tyre flames.
 */
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
    private final ThrustFire thrustFire;
    private final StopSignal stopSignal;
    private EnergyPickupIcon energyPickUpIcon;

    /** Which sprite set the car currently shows: the regular run or the glowing (88 mph) variant. */
    private enum Animation {
        NORMAL, GLOWING
    }

    /**
     * Creates the car at the left edge of the field, vertically centred and then shifted down by 8
     * px, with a hit box spanning (6, 9) to (28, 20) relative to its origin. Starts with a full
     * tank, zero energy, zero speed and builds its companions: the portal, thrust fire, stop signal
     * and both tyre flames.
     */
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
        thrustFire = new ThrustFire(this);
        stopSignal = new StopSignal(this);
        tireFlames = new ArrayList<>(Arrays.asList(
                new TireFlame(this, TireFlame.Side.RIGHT),
                new TireFlame(this, TireFlame.Side.LEFT)
        ));
        energyPickUpIcon = null;
    }

    /**
     * Advances the car by one frame: applies the horizontal input, steers, then adds this frame's
     * travel to the odometer. Called once per game turn, before the road is scrolled.
     */
    public void move() {
        gas();
        steer();
        countDistance();
    }

    /**
     * Drops the speed to zero immediately, leaving fuel, energy and directions untouched. Used when
     * the car has passed the portal, after which the game enters its stopped ending state.
     */
    public void stop() {
        speed = 0;
    }

    /**
     * Applies the current horizontal input and picks the matching animation. Despite the name it
     * handles accelerating, braking and coasting; it also returns early once the car has left its
     * leftmost x, so the speed is frozen while the car drives into the portal.
     */
    private void gas() {
        if (!isAtLeftmostPosition()) return;

        changeSpeed();
        selectAnimationBasedOnSpeed();
    }

    /**
     * Burns 0.02 fuel units for the current frame, but only while the RIGHT key is held, i.e. while
     * the car is really accelerating. The tank never goes below zero.
     */
    private void depleteGas() {
        if (horDirection != Direction.RIGHT) return;
        gas = Math.max(gas - 0.02, 0);
    }

    /**
     * Translates the horizontal direction into one of three speed changes: accelerate on RIGHT,
     * brake on LEFT, coast on NONE.
     */
    private void changeSpeed() {
        if (horDirection == Direction.RIGHT) accelerate();
        else if (horDirection == Direction.LEFT) brake();
        else if (horDirection == Direction.NONE) release();
    }

    /**
     * Speeds the car up with an eased step, {@code speed += 0.05 * (1 - 0.1 * speed)}, so each frame
     * adds less than the previous one and the car approaches a soft cap rather than accelerating
     * linearly. Fuel is consumed first; an empty tank falls back to coasting.
     */
    private void accelerate() {
        if (gas > 0) {
            depleteGas();
            speed += ACCELERATION_GAS * (1 - speed * 0.1);
            limitMaxSpeed();
        } else {
            release();
        }
    }

    /**
     * Slows the car by a constant 0.2 px/frame, clamped at zero so the car never reverses.
     */
    private void brake() {
        speed += ACCELERATION_BRAKE;
        limitMinSpeed();
    }

    /**
     * Coasting: bleeds off 0.025 px/frame of speed while no horizontal key is held, floored at zero.
     */
    private void release() {
        speed += ACCELERATION_RELEASE;
        limitMinSpeed();
    }

    /**
     * Clamps the speed to the band currently allowed: just under 6.0 (5.99) without full energy,
     * just over 8.8 (8.81) with all 1.21 GW collected. This is the "88 mph" gate that unlocks the
     * portal, and it is only applied by acceleration.
     */
    private void limitMaxSpeed() {
        if (energy < MAX_ENERGY) {
            speed = Math.min(START_GLOWING_THRESHOLD - 0.01, speed);
        } else {
            speed = Math.min(ENTER_PORTAL_THRESHOLD + 0.01, speed);
        }
    }

    /**
     * Floors the speed at zero; the car never moves backwards.
     */
    private void limitMinSpeed() {
        speed = Math.max(0, speed);
    }

    /**
     * Chooses the sprite set and the frame delay from the current speed. The delay is
     * {@code (int) (10 / speed + 1)}, so faster driving spins the wheels faster; a speed of zero or
     * less installs {@code Integer.MAX_VALUE} to freeze the animation, and 6.0 or more switches to
     * the glowing animation.
     */
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

    /**
     * Installs the regular running animation. Returns early if that animation is already active, so
     * calling it repeatedly does not reset the sprite to its first frame or stutter at the speed
     * threshold.
     */
    private void setNormalAnimation() {
        if (animation == Animation.NORMAL) return;

        setAnimatedView(Sprite.Loop.ENABLED, Integer.MAX_VALUE,
                Shapes.DELOREAN_RUN_0,
                Shapes.DELOREAN_RUN_1,
                Shapes.DELOREAN_RUN_2,
                Shapes.DELOREAN_RUN_3);
        animation = Animation.NORMAL;
    }

    /**
     * Installs the glowing (time-travel) animation. Returns early if it is already active, so
     * switching back and forth across the 6.0 threshold does not reset the sprite.
     */
    private void setGlowingAnimation() {
        if (animation == Animation.GLOWING) return;

        setAnimatedView(Sprite.Loop.ENABLED, Integer.MAX_VALUE,
                Shapes.DELOREAN_GLOW_0,
                Shapes.DELOREAN_GLOW_1,
                Shapes.DELOREAN_GLOW_2,
                Shapes.DELOREAN_GLOW_3);
        animation = Animation.GLOWING;
    }

    /**
     * Steers the car: first pushes it back onto the drivable strip if it left it, then moves it up
     * or down. Does nothing once the car is no longer at its leftmost x, so it cannot swerve while
     * driving into the portal.
     */
    private void steer() {
        if (!isAtLeftmostPosition()) return;

        checkOffRoad();
        changeVerticalPosition();
    }

    /**
     * Tells whether the car is at its usual driving x (1). Uses exact double equality, which is
     * safe here because x only ever changes by whole pixels; the check doubles as the "normal
     * driving mode" flag used by gas, steer and the portal.
     *
     * @return true while the car drives normally, false once it is sliding into the portal
     */
    public boolean isAtLeftmostPosition() {
        return x == PADDING_LEFT;
    }

    /**
     * Keeps the car on the drivable strip: clamps y into
     * {@code [UPPER_BORDER - 8, LOWER_BORDER - height]} and, whenever a clamp was needed, cuts the
     * speed to 99.5% as a grass/verge penalty. It runs before the vertical move, so the penalty
     * lands on the frame after the car leaves the strip.
     */
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

    /**
     * Scales the current speed by the given percentage; 80.0 keeps 80% of it, so the argument is a
     * percentage and not a multiplier. Holes and puddles use it as a one-off penalty.
     *
     * @param percentage percentage of the current speed to keep, e.g. 99.5 keeps almost all of it
     */
    public void cutSpeedToPercentageOfCurrent(double percentage) {
        speed = (speed / 100.0) * percentage;
    }

    /**
     * Moves y up or down by the current speed, but never by more than MAX_STEER_SPEED (2 px/frame),
     * so steering is equally responsive at any speed. Direction NONE leaves y unchanged.
     */
    private void changeVerticalPosition() {
        if (verDirection == Direction.UP) y -= Math.min(speed, MAX_STEER_SPEED);
        else if (verDirection == Direction.DOWN) y += Math.min(speed, MAX_STEER_SPEED);
    }

    /**
     * Adds this frame's travel to the odometer as {@code speed / 7.0}, the unit the game calls
     * "metres" and uses to decide when to spawn new road objects.
     */
    private void countDistance() {
        this.distance += speed / 7.0;
    }

    /**
     * Draws one frame of the car and everything attached to it. Above 8.8 px/frame it first slides
     * the car into the portal; then it draws the car itself, the thrust fire, the stop signal, the
     * pickup icon, both tyre flames and finally the portal on top of the car's nose.
     */
    @Override
    public void draw() {
        if (speed > ENTER_PORTAL_THRESHOLD) {
            moveIntoPortal();
        }

        super.draw();
        drawThrustFire();
        drawStopSignal();
        drawEnergyPickUpIcon();
        tireFlames.forEach(GameObject::draw);
        portal.draw();
    }

    /**
     * Slides the car 3 px to the right per frame towards the portal. The first time x reaches 4 the
     * sprite starts being masked in from the right by 3 px per frame, so the car appears to be
     * erased into the portal; the shifted x then switches off gas and steering, so the car keeps
     * sliding on its own.
     */
    private void moveIntoPortal() {
        final int gapBetweenCarAndPortal = 3;
        final int shift = 3;

        if (x == PADDING_LEFT + gapBetweenCarAndPortal) {
            maskIn(shift);
        }

        x += shift;
    }

    /**
     * Draws the exhaust flame behind the car, but only while the RIGHT key is held, there is fuel
     * left and the portal has not been passed yet.
     */
    private void drawThrustFire() {
        if (horDirection != Direction.RIGHT) return;
        if (gas <= 0) return;
        if (passedPortal()) return;

        thrustFire.draw();
    }

    /**
     * Draws the brake light, but only while the LEFT key is held and the portal has not been passed
     * yet.
     */
    private void drawStopSignal() {
        if (horDirection != Direction.LEFT) return;
        if (passedPortal()) return;

        stopSignal.draw();
    }

    /**
     * Draws the floating energy icon if one is currently attached, and does nothing otherwise. The
     * icon is never detached: it simply stops drawing itself once its rise is over.
     */
    private void drawEnergyPickUpIcon() {
        if (energyPickUpIcon == null) return;

        energyPickUpIcon.draw();
    }

    /**
     * Applies the hole's effect: with 10% probability nudges the car one pixel up, and with another
     * 10% one pixel down. The road clamp fixes any overshoot on the next frame.
     */
    public void steerRandomly() {
        double random = Math.random();
        if (random < 0.1) y--;
        else if (random < 0.2) y++;
    }

    /**
     * Tells whether the car has gone far enough into the portal to finish the race.
     *
     * @return true once x is more than 5 px past portal.x, which stops the game
     */
    public boolean passedPortal() {
        return x - portal.x > 5;
    }

    /**
     * Adds one tenth of the energy bar (0.121 GW); ten pickups fill it to 1.21 GW. Callers are
     * expected to check {@link #hasMaxEnergy()} first, as the pickup itself does.
     */
    public void addEnergy() {
        energy += MAX_ENERGY / 10.0;
    }

    /**
     * Tells whether the energy bar is full, which blocks further pickups and unlocks the higher
     * speed band.
     *
     * @return true when at least 1.21 GW has been collected
     */
    public boolean hasMaxEnergy() {
        return energy >= MAX_ENERGY;
    }

    /**
     * Converts the tank state into litres burned, treating 1 litre as 10 fuel units; a full
     * 23.0-unit tank therefore reports zero.
     *
     * @return fuel used so far, in litres
     */
    public double getUsedGasInLitres() {
        return (MAX_GAS - gas) / 10.0;
    }


    /*
     * Plain getters, setters
     */

    /**
     * @return the horizontal direction requested by the last LEFT/RIGHT key event, or NONE
     */
    public Direction getHorDirection() {
        return horDirection;
    }

    /**
     * Stores the horizontal input. The {@code @Control} annotation binds this setter to the LEFT
     * and RIGHT keys, which the controller calls on key down and release.
     *
     * @param horDirection direction to hold: RIGHT accelerates, LEFT brakes, NONE coasts
     */
    @Control({Key.LEFT, Key.RIGHT})
    public void setHorDirection(Direction horDirection) {
        this.horDirection = horDirection;
    }

    /**
     * @return the vertical direction requested by the last UP/DOWN key event, or NONE
     */
    public Direction getVerDirection() {
        return verDirection;
    }

    /**
     * Stores the vertical input. The {@code @Control} annotation binds this setter to the UP and
     * DOWN keys; the value is applied on the next {@link #steer()}.
     *
     * @param verDirection direction to hold: UP steers up, DOWN steers down, NONE keeps y
     */
    @Control({Key.UP, Key.DOWN})
    public void setVerDirection(Direction verDirection) {
        this.verDirection = verDirection;
    }

    /**
     * @return current speed in world pixels per frame, not in mph or km/h
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @return collected energy in GW, from 0 to 1.21
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * @return remaining fuel in fuel units, from 0 to 23.0
     */
    public double getGas() {
        return gas;
    }

    /**
     * @return distance driven so far, in the game's abstract "metres" (speed / 7.0 per frame)
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Attaches the icon shown while a pickup is being collected; a null value simply hides it. The
     * previous icon, if any, is replaced.
     *
     * @param energyPickUpIcon icon to draw at the car, or null to clear it
     */
    public void setEnergyPickUpIcon(EnergyPickupIcon energyPickUpIcon) {
        this.energyPickUpIcon = energyPickUpIcon;
    }
}
