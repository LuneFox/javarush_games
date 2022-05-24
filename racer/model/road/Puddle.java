package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.HitBox;
import com.javarush.games.racer.model.gameobjects.Mirror;

public class Puddle extends RoadObject {
    private final Mirror mirror;

    Puddle() {
        super(RoadObjectType.PUDDLE);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 15, 8);
        this.mirror = (Math.random() > 0.5) ? Mirror.HORIZONTAL : Mirror.NONE;
    }

    @Override
    public void onContact(DeLorean deLorean) {
        deLorean.cutSpeedToPercentageOfCurrent(95.0);
    }

    @Override
    public void draw() {
        super.draw(mirror);
    }
}
