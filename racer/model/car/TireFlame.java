package com.javarush.games.racer.model.car;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.gameobjects.Sprite;
import com.javarush.games.racer.view.Shapes;

/**
 * One of the two tire flames that make up the ending burnout effect. Both are owned by the car and
 * drawn only once the game is stopped. Each flame masks itself out once, and because the masked
 * width shrinks instead of growing, the effect reveals the flame from its left edge rather than
 * burning it away.
 */
public class TireFlame extends GameObject {
    private final Side side;
    private final DeLorean deLorean;

    /** Which tire of the car this flame belongs to. */
    enum Side {
        LEFT, RIGHT
    }

    /**
     * Creates the flame for one side of the car with a looping two-frame animation shown at a frame
     * delay of 3.
     *
     * @param deLorean the car the flame is attached to
     * @param side the tyre, left or right, this flame is drawn at
     */
    TireFlame(DeLorean deLorean, Side side) {
        this.deLorean = deLorean;
        this.side = side;
        setAnimatedView(Sprite.Loop.ENABLED, 3,
                Shapes.TIRE_FLAME_0,
                Shapes.TIRE_FLAME_1);
    }

    /**
     * Draws the flame only after the game has stopped. It aligns the flame to the car and then calls
     * {@code maskOut(3)}: the mask starts by hiding the whole sprite and loses 3 px of hidden width
     * per draw, so the flame fades into view from the left instead of being erased. Masking is
     * one-shot, so this reveal happens only once.
     */
    @Override
    public void draw() {
        if (!game.isStopped()) return;

        alignToDeLorean();
        maskOut(3);
        super.draw();
    }

    /**
     * Positions the flame at the car's left edge. The right tire sits at the car's bottom edge; the
     * left tire is drawn 10 px higher.
     */
    private void alignToDeLorean() {
        int shift = (side == Side.RIGHT) ? 0 : 10;
        x = deLorean.x;
        y = deLorean.y + deLorean.getHeight() - this.getHeight() - shift;
    }
}
