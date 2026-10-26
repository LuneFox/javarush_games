package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.HitBox;
import com.javarush.games.racer.model.gameobjects.Mirror;

/**
 * A hole in the asphalt. Hitting it cuts the car's speed to 80% of its current value and, if the car is
 * still moving, may randomly jerk it one cell up or down.
 */
public class Hole extends RoadObject {

    /**
     * Creates the hole with no own speed and a 15x8 hit box covering its shape.
     */
    Hole() {
        super(RoadObjectType.HOLE);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 15, 8);
    }

    /**
     * Slows the car to 80% of its current speed; if it is still moving afterwards, randomly nudges it up
     * or down. Since overlap is checked every frame, a car lingering on the hole can be slowed repeatedly.
     *
     * @param deLorean the player's car
     */
    @Override
    public void onContact(DeLorean deLorean) {
        deLorean.cutSpeedToPercentageOfCurrent(80.0);

        if (deLorean.getSpeed() > 0) {
            deLorean.steerRandomly();
        }
    }

    /**
     * Draws the hole in its normal orientation.
     */
    @Override
    public void draw() {
        super.draw(Mirror.NONE);
    }
}