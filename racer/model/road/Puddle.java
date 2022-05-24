package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.HitBox;
import com.javarush.games.racer.model.gameobjects.Mirror;
import com.javarush.games.racer.model.gameobjects.Sprite;
import com.javarush.games.racer.view.Shapes;

public class Puddle extends RoadObject {
    private final Mirror mirror;
    private boolean isSplashing;
    private DeLorean deLorean;

    Puddle() {
        super(RoadObjectType.PUDDLE);
        this.speed = 0;
        this.hitBox = new HitBox(3, 3, 18, 11);
        this.mirror = (Math.random() > 0.5) ? Mirror.HORIZONTAL : Mirror.NONE;
    }

    @Override
    public void onContact(DeLorean deLorean) {
        this.deLorean = deLorean;
        deLorean.cutSpeedToPercentageOfCurrent(95.0);
    }

    @Override
    public void draw() {
        selectAnimation();
        super.draw(mirror);
    }

    private void selectAnimation() {
        if (deLorean == null) return;

        if (deLorean.getSpeed() > 0 && HitBox.checkFullOverlap(this, deLorean)) {
            setSplashingAnimation();
        } else {
            setNormalAnimation();
        }
    }

    private void setSplashingAnimation() {
        if (isSplashing) return;
        setAnimatedView(Sprite.Loop.ENABLED, 2,
                Shapes.PUDDLE_SPLASH_1,
                Shapes.PUDDLE_SPLASH_2);
        isSplashing = true;
    }

    private void setNormalAnimation() {
        if (!isSplashing) return;
        setStaticView(Shapes.PUDDLE);
        isSplashing = false;
    }

}
