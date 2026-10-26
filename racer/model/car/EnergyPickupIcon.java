package com.javarush.games.racer.model.car;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.Shapes;

/**
 * The small icon that rises above the car for a moment when an energy pickup is collected. It
 * animates only by being drawn: each draw paints it one pixel higher and counts up, and once it has
 * risen ten pixels it silently stops drawing itself.
 */
public class EnergyPickupIcon extends GameObject {
    /** How many frames this icon has already been drawn; drawing stops once it exceeds 10. */
    int time;

    /**
     * Creates the icon at the position it rises from and gives it its static frame. The given
     * coordinates are the baseline: the first draw happens exactly there, later draws go higher.
     *
     * @param x x coordinate the icon rises from
     * @param y y coordinate the icon rises from
     */
    public EnergyPickupIcon(double x, double y) {
        super(x, y);
        setStaticView(Shapes.ENERGY_ICON);
    }

    /**
     * Draws the icon at {@code y - time}, so it climbs one pixel per call, then increments its
     * counter. Once the counter exceeds 10 the method returns without drawing anything, which
     * quietly ends the effect; the owning car keeps the stale reference until the next pickup
     * replaces it.
     */
    @Override
    public void draw() {
        if (time > 10) return;

        super.draw(x, y - time);
        time++;
    }
}
