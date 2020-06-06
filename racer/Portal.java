package com.javarush.games.racer;

public class Portal extends GameObject {

    private Animation animation;

    public Portal() {
        super(0, 0, ShapeMatrix.PORTAL_0);

    }

    public void align(DeLorean deLorean) {
        this.x = deLorean.x + 35;
        this.y = deLorean.y - 2;
    }


    public void animate(RacerGame game, DeLorean delorean) {
        if (delorean.getSpeed() >= 7.5 && delorean.getSpeed() < 8 && animation != Animation.GROWING) {
            animateGrowing();
        } else if (delorean.getSpeed() >= 8 && animation != Animation.ACTIVE) {
            animateActive();
        }
        if (delorean.getSpeed() >= 7.5) {
            align(delorean);
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
        ACTIVE, GROWING
    }
}
