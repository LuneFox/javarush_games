package com.javarush.games.racer.model;

import com.javarush.games.racer.view.Shapes;

public class Portal extends GameObject {

    private static final double DISPLAY_POINT = 7.5;
    private static final double EXPAND_POINT = 8.0;
    private Animation animation;
    private int finishTimeOut = 20;

    public Portal() {
        super(0, 0);
        setStaticView(Shapes.PORTAL_GROW_0);
        animation = Animation.NONE;
    }

    public void align(DeLorean deLorean) {
        if (deLorean.x == 3) {
            this.x = deLorean.x + deLorean.getWidth() - 2;
            this.y = deLorean.y - 2;
        }
    }


    public void draw() {
        DeLorean deLorean = game.delorean;
        if (deLorean.getSpeed() < EXPAND_POINT && animation != Animation.GROWING) {
            animateGrowing();
        } else if (deLorean.getSpeed() >= EXPAND_POINT && animation != Animation.ACTIVE) {
            animateActive();
        }
        if (deLorean.getSpeed() >= DISPLAY_POINT || (game.isStopped && finishTimeOut > 0)) {
            if (deLorean.getSpeed() < DeLorean.MAX_SPEED - 0.09) {
                align(deLorean);
            }
            if (game.isStopped) {
                finishTimeOut--;
            }
            super.draw();
        }
    }

    private void animateActive() {
        setAnimatedView(Sprite.Loop.ENABLED, 1,
                Shapes.PORTAL_0,
                Shapes.PORTAL_1,
                Shapes.PORTAL_2,
                Shapes.PORTAL_3
        );
        animation = Animation.ACTIVE;
    }

    private void animateGrowing() {
        setAnimatedView(Sprite.Loop.ENABLED, 2,
                Shapes.PORTAL_GROW_0,
                Shapes.PORTAL_GROW_1
        );
        animation = Animation.GROWING;
    }

    private enum Animation {
        ACTIVE, GROWING, NONE
    }
}
