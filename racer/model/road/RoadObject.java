package com.javarush.games.racer.model.road;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.gameobjects.Mirror;
import com.javarush.games.racer.view.Shapes;

/**
 * Common base for everything lying on the road: obstacles (holes, puddles) and pickups (energy).
 * Carries its own horizontal {@code speed} in world pixels per frame, which the manager adds to the car
 * speed, and a per-type collision reaction in {@link #onContact(DeLorean)}.
 */
public abstract class RoadObject extends GameObject {
    protected double speed;

    /**
     * Creates the object and installs the static sprite matching the type.
     * The shape lookup's default branch treats {@code PUDDLE} as the fallback, so any new enum constant
     * would silently be drawn as a puddle.
     *
     * @param type kind of road object
     */
    public RoadObject(RoadObjectType type) {
        this.setStaticView(getShape(type));
    }

    /**
     * Factory that instantiates the concrete class for a type.
     * {@code ENERGY} and {@code HOLE} are handled explicitly; everything else, {@code PUDDLE} included,
     * falls into the default branch and yields a {@link Puddle}.
     *
     * @param type requested kind of road object
     * @return a new object of the matching class
     */
    public static RoadObject create(RoadObjectType type) {
        switch (type) {
            case ENERGY:
                return new Energy();
            case HOLE:
                return new Hole();
            default:
                return new Puddle();
        }
    }

    /**
     * Maps a type to its pixel-art shape. Like the constructor and the factory, anything that is not
     * {@code ENERGY} or {@code HOLE} is treated as a puddle.
     *
     * @param type requested kind of road object
     * @return the shape array used as the object's static view
     */
    private static int[][] getShape(RoadObjectType type) {
        switch (type) {
            case ENERGY:
                return Shapes.ENERGY;
            case HOLE:
                return Shapes.HOLE;
            default:
                return Shapes.PUDDLE;
        }
    }

    /**
     * Draws the object at its own position, without the edge handling of {@link #draw(Mirror)}.
     * Note that {@code super.draw(Mirror.NONE)} resolves to {@code GameObject}'s three-argument
     * draw, not to the override below, so this overload skips the clamping; all three subclasses
     * override it to route through {@link #draw(Mirror)} instead.
     */
    @Override
    public void draw() {
        super.draw(Mirror.NONE);
    }

    /**
     * Draws the object, clamping it while it enters from the right of the screen. If its centre has
     * passed the right edge, the sprite is drawn with its centre pinned to that edge (so half of it
     * stays off-screen) and it is made to flicker; once the game is stopped, such objects are
     * skipped entirely. Otherwise flicker is switched off and the object is drawn at its real
     * position.
     *
     * @param mirror horizontal mirroring to apply to the sprite
     */
    @Override
    public void draw(Mirror mirror) {
        if (this.x + (this.getWidth() / 2.0) > RacerGame.WIDTH) {
            if (game.isStopped()) return;
            startFlicker();
            draw(RacerGame.WIDTH - (this.getWidth() / 2.0), this.y, mirror);
        } else {
            stopFlicker();
            super.draw(mirror);
        }
    }

    /**
     * Scrolls the object left by the given amount, normally its own speed plus the car's speed.
     * Only x changes; the lane row (y) stays fixed.
     *
     * @param boost world pixels to subtract from x this frame
     */
    public void move(double boost) {
        this.x -= boost;
    }

    /**
     * Applies this object's effect to the car. Called once per frame while the car fully overlaps it.
     *
     * @param deLorean the player's car
     */
    public abstract void onContact(DeLorean deLorean);
}
