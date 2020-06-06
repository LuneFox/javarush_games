package com.javarush.games.racer;

public class Portal extends GameObject {

    private static final double DISPLAY_POINT = 7.5;
    private static final double EXPAND_POINT = 8.0;
    private Animation animation;
    private int finishTimeOut = 20;

    public Portal() {
        super(0, 0, ShapeMatrix.PORTAL_GROW_0);
        animation = Animation.NONE;
    }

    public void align(DeLorean deLorean) {
        if (deLorean.x == 3) {
            this.x = deLorean.x + deLorean.width - 2;
            this.y = deLorean.y - 2;
        }
    }


    public void animate(RacerGame game, DeLorean delorean) {
        if (delorean.getSpeed() < EXPAND_POINT && animation != Animation.GROWING) {
            animateGrowing();
        } else if (delorean.getSpeed() >= EXPAND_POINT && animation != Animation.ACTIVE) {
            animateActive();
        }
        if (delorean.getSpeed() >= DISPLAY_POINT || (game.isStopped && finishTimeOut > 0 )) {
            if (delorean.getSpeed() < DeLorean.MAX_SPEED - 0.09) {
                align(delorean);
            }
            if (game.isStopped){
                finishTimeOut--;
            }
            super.animate(game, 1);
        }
    }

    private void animateActive() {
        setAnimation(
                ShapeMatrix.PORTAL_0,
                ShapeMatrix.PORTAL_1,
                ShapeMatrix.PORTAL_2,
                ShapeMatrix.PORTAL_3
        );
        animation = Animation.ACTIVE;
    }

    private void animateGrowing() {
        setAnimation(
                ShapeMatrix.PORTAL_GROW_0,
                ShapeMatrix.PORTAL_GROW_1
        );
        animation = Animation.GROWING;
    }

    private enum Animation {
        ACTIVE, GROWING, NONE
    }
}
