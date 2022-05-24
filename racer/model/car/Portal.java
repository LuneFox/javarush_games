package com.javarush.games.racer.model.car;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.gameobjects.Sprite;
import com.javarush.games.racer.view.Shapes;

public class Portal extends GameObject {
    private static final double GROW_THRESHOLD = 7.5;
    private static final double EXPAND_THRESHOLD = 8.0;
    private final DeLorean deLorean;
    private Animation animation;
    private int afterEffectTime = 20;

    private enum Animation {
        GROWING, EXPANDED
    }

    Portal(DeLorean deLorean) {
        setGrowingAnimation(2);
        this.deLorean = deLorean;
    }

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

    private void drawAfterEffect() {
        if (afterEffectTime > 0) {
            setGrowingAnimation(1);
            super.draw();
            afterEffectTime--;
        }
    }

    void alignToDeLorean() {
        if (deLorean.isAtLeftmostPosition()) {
            this.x = deLorean.x + deLorean.getWidth() - 2;
            this.y = deLorean.y - 2;
        }
    }

    private void selectAnimationBasedOnSpeed() {
        if (deLorean.getSpeed() < EXPAND_THRESHOLD) {
            setGrowingAnimation(2);
        } else {
            setExpandedAnimation();
        }
    }

    private void setExpandedAnimation() {
        if (animation == Animation.EXPANDED) return;
        setAnimatedView(Sprite.Loop.ENABLED, 1,
                Shapes.PORTAL_EXPANDED_0,
                Shapes.PORTAL_EXPANDED_1,
                Shapes.PORTAL_EXPANDED_2,
                Shapes.PORTAL_EXPANDED_3);
        animation = Animation.EXPANDED;
    }

    private void setGrowingAnimation(int delay) {
        if (animation == Animation.GROWING) return;
        setAnimatedView(Sprite.Loop.ENABLED, delay,
                Shapes.PORTAL_GROWING_0,
                Shapes.PORTAL_GROWING_1);
        animation = Animation.GROWING;
    }
}
