package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.HitBox;
import com.javarush.games.racer.model.gameobjects.Mirror;
import com.javarush.games.racer.model.gameobjects.Sprite;
import com.javarush.games.racer.view.Shapes;

/**
 * A puddle that slows the car to 95% of its current speed and shows a looping two-frame splash while the
 * car is still overlapping it and moving. Each puddle randomly decides at creation whether to be
 * mirrored horizontally, so they do not all look identical.
 */
public class Puddle extends RoadObject {
    private final Mirror mirror;
    private boolean isSplashing;
    private DeLorean deLorean;

    /**
     * Creates the puddle with no own speed, a 15x8 hit box starting at (3,3) relative to its position,
     * and a random horizontal mirror.
     */
    Puddle() {
        super(RoadObjectType.PUDDLE);
        this.speed = 0;
        this.hitBox = new HitBox(3, 3, 18, 11);
        this.mirror = (Math.random() > 0.5) ? Mirror.HORIZONTAL : Mirror.NONE;
    }

    /**
     * Cuts the car's speed to 95% of its current value and remembers the car so the splash animation can
     * follow it. Called every frame while the car overlaps, so the speed can be reduced more than once.
     *
     * @param deLorean the player's car
     */
    @Override
    public void onContact(DeLorean deLorean) {
        this.deLorean = deLorean;
        deLorean.cutSpeedToPercentageOfCurrent(95.0);
    }

    /**
     * Chooses the splash or static frame for this frame, then draws the puddle with its stored mirroring.
     */
    @Override
    public void draw() {
        selectAnimation();
        super.draw(mirror);
    }

    /**
     * Switches to the looping splash while the remembered car still overlaps the puddle and is moving,
     * and back to the static puddle otherwise. Before the first contact no car is known, so the static
     * shape stays.
     */
    private void selectAnimation() {
        if (deLorean == null) return;

        if (deLorean.getSpeed() > 0 && HitBox.checkFullOverlap(this, deLorean)) {
            setSplashingAnimation();
        } else {
            setNormalAnimation();
        }
    }

    /**
     * Switches the sprite to the two-frame looping splash with a 2-frame delay; does nothing when the
     * splash is already playing, so the animation is not restarted every frame.
     */
    private void setSplashingAnimation() {
        if (isSplashing) return;
        setAnimatedView(Sprite.Loop.ENABLED, 2,
                Shapes.PUDDLE_SPLASH_1,
                Shapes.PUDDLE_SPLASH_2);
        isSplashing = true;
    }

    /**
     * Restores the static puddle shape after splashing; does nothing when the puddle is already static.
     */
    private void setNormalAnimation() {
        if (!isSplashing) return;
        setStaticView(Shapes.PUDDLE);
        isSplashing = false;
    }

}
