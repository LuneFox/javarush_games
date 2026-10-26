package com.javarush.games.racer.model.car;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.gameobjects.Sprite;
import com.javarush.games.racer.view.Shapes;

/**
 * The time-travel portal that ends the race. It is created by the car, follows the car's right edge
 * while the car is at its leftmost x, appears at speed 7.5 and expands at 8.0. When the game is
 * stopped it plays a short 20-frame after-effect and then stops drawing.
 */
public class Portal extends GameObject {
    private static final double GROW_THRESHOLD = 7.5;
    private static final double EXPAND_THRESHOLD = 8.0;
    private final DeLorean deLorean;
    private Animation animation;
    private int afterEffectTime = 20;

    /** Which of the two portal looks is currently installed: the small growing one or the expanded one. */
    private enum Animation {
        GROWING, EXPANDED
    }

    /**
     * Creates the portal with the growing animation and remembers the car it belongs to. Its
     * position is set later, on the first draw that aligns it to the car.
     *
     * @param deLorean the car whose speed and position drive the portal
     */
    Portal(DeLorean deLorean) {
        setGrowingAnimation(2);
        this.deLorean = deLorean;
    }

    /**
     * Draws the portal if it is due: below speed 7.5 it is invisible, between 7.5 and 8.0 it uses the
     * growing animation and from 8.0 the expanded one. While the game is stopped it delegates to the
     * after-effect instead.
     */
    public void draw() {
        if (game.isStopped()) {
            drawAfterEffect();
            return;
        }

        if (deLorean.getSpeed() < GROW_THRESHOLD) {
            return;
        }

        alignToDeLorean();
        selectAnimationBasedOnSpeed();
        super.draw();
    }

    /**
     * Plays the closing effect: redraws the portal with the growing animation and decrements
     * {@code afterEffectTime}, after which the portal is not drawn at all any more.
     */
    private void drawAfterEffect() {
        if (afterEffectTime > 0) {
            setGrowingAnimation(1);
            super.draw();
            afterEffectTime--;
        }
    }

    /**
     * Places the portal at the car's nose: x = car.x + car width - 2 (so its left edge overlaps the
     * car's last two columns) and y two pixels above the car's top. It only does so while the car is
     * at its leftmost x; after that the portal stays where it last was.
     */
    void alignToDeLorean() {
        if (deLorean.isAtLeftmostPosition()) {
            this.x = deLorean.x + deLorean.getWidth() - 2;
            this.y = deLorean.y - 2;
        }
    }

    /**
     * Picks the growing animation below speed 8.0 and the expanded one from 8.0 upwards.
     */
    private void selectAnimationBasedOnSpeed() {
        if (deLorean.getSpeed() < EXPAND_THRESHOLD) {
            setGrowingAnimation(2);
        } else {
            setExpandedAnimation();
        }
    }

    /**
     * Installs the four-frame expanded portal animation (frame delay 1) unless it is already active,
     * so the sprite is not reset on every frame.
     */
    private void setExpandedAnimation() {
        if (animation == Animation.EXPANDED) return;
        setAnimatedView(Sprite.Loop.ENABLED, 1,
                Shapes.PORTAL_EXPANDED_0,
                Shapes.PORTAL_EXPANDED_1,
                Shapes.PORTAL_EXPANDED_2,
                Shapes.PORTAL_EXPANDED_3);
        animation = Animation.EXPANDED;
    }

    /**
     * Installs the two-frame growing animation with the given frame delay, unless the growing
     * animation is already active. The delay therefore only takes effect when the animation is
     * actually (re)installed: the after-effect's request for a delay of 1 is silently ignored if the
     * portal was still in its growing state.
     *
     * @param delay number of frames to show each sprite frame
     */
    private void setGrowingAnimation(int delay) {
        if (animation == Animation.GROWING) return;
        setAnimatedView(Sprite.Loop.ENABLED, delay,
                Shapes.PORTAL_GROWING_0,
                Shapes.PORTAL_GROWING_1);
        animation = Animation.GROWING;
    }
}
