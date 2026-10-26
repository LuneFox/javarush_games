package com.javarush.games.racer.model.car;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.gameobjects.Sprite;
import com.javarush.games.racer.view.Shapes;

/**
 * The exhaust flame drawn at the DeLorean's rear while it accelerates. It is a four-frame looping
 * animation that re-aligns itself to the car on every draw; the car decides when it is visible.
 */
public class ThrustFire extends GameObject {
    private final DeLorean deLorean;

    /**
     * Creates the flame with its looping four-frame animation and remembers the car it is attached
     * to.
     *
     * @param deLorean the car whose bottom edge the flame is aligned to
     */
    ThrustFire(DeLorean deLorean) {
        this.deLorean = deLorean;
        setAnimatedView(Sprite.Loop.ENABLED, 1,
                Shapes.THRUST_FIRE_1,
                Shapes.THRUST_FIRE_2,
                Shapes.THRUST_FIRE_3,
                Shapes.THRUST_FIRE_4
        );
    }

    /**
     * Moves the flame to x = car.x and y = car bottom - flame height - 4, so it overlaps the car's
     * lower rear. It is drawn after the car, so the flame is painted on top of that part of the
     * sprite rather than hidden behind it.
     */
    void alignToDelorean() {
        setPosition(deLorean.x, deLorean.y + deLorean.getHeight() - this.getHeight() - 4);
    }

    /**
     * Re-aligns the flame to the car and then draws it, so the caller never has to position it
     * manually.
     */
    @Override
    public void draw() {
        alignToDelorean();
        super.draw();
    }
}
