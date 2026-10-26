package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.car.EnergyPickupIcon;
import com.javarush.games.racer.model.gameobjects.HitBox;
import com.javarush.games.racer.model.gameobjects.Mirror;

/**
 * An energy pickup. It has zero own speed, so it drifts left only with the scrolling world, and it is
 * collected when the car's hit box overlaps it, adding a tenth of the energy bar.
 */
public class Energy extends RoadObject {

    private boolean isCollected;

    /**
     * Creates the pickup with no own speed, an 18x9 hit box anchored at its top-left corner and an
     * uncollected state. The hit box is wider than the 8-pixel sprite, so contact can register slightly
     * before the sprites visually touch.
     */
    Energy() {
        super(RoadObjectType.ENERGY);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 18, 9);
        isCollected = false;
    }

    /**
     * Draws the pickup unless it has already been collected; a collected pickup disappears immediately
     * but stays in the manager's list until it scrolls off-screen.
     */
    @Override
    public void draw() {
        if (isCollected) return;
        super.draw(Mirror.NONE);
    }

    /**
     * Collects the pickup on contact: adds one tenth of a full energy bar, hides the sprite and spawns a
     * pickup icon at the car's centre that floats one pixel up per frame. Does nothing when the pickup
     * was already collected or when the car's energy bar is full.
     *
     * @param deLorean the player's car
     */
    @Override
    public void onContact(DeLorean deLorean) {
        if (isCollected) return;
        if (deLorean.hasMaxEnergy()) return;

        isCollected = true;
        deLorean.addEnergy();
        deLorean.setEnergyPickUpIcon(new EnergyPickupIcon(deLorean.x + deLorean.getWidth() / 2.0, deLorean.y));
    }
}
