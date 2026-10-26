package com.javarush.games.racer.model.car;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.Shapes;

/**
 * The static brake-light sprite shown at the car's bottom-left while the player brakes. Purely
 * visual: it is positioned by the car and does not affect the simulation.
 */
public class StopSignal extends GameObject {
    private final DeLorean deLorean;

    /**
     * Creates the brake light with its single static frame and remembers the car it belongs to.
     *
     * @param deLorean the car the signal is attached to
     */
    public StopSignal(DeLorean deLorean) {
        this.deLorean = deLorean;
        setStaticView(Shapes.STOP_SIGNAL);
    }

    /**
     * Positions the signal at (car.x + 3, car.y + car height - signal height - 5), i.e. just inside
     * the car's bottom-left corner. Called on every draw.
     */
    void alignToDelorean() {
        setPosition(deLorean.x + 3, deLorean.y + deLorean.getHeight() - this.getHeight() - 5);
    }

    /**
     * Aligns the signal to the car and draws it; the car decides when that happens.
     */
    @Override
    public void draw() {
        alignToDelorean();
        super.draw();
    }
}
