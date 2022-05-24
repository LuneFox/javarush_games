package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.HitBox;
import com.javarush.games.racer.model.gameobjects.Mirror;

public class Hole extends RoadObject {

    Hole() {
        super(RoadObjectType.HOLE);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 15, 8);
    }

    @Override
    public void onContact(DeLorean deLorean) {
        deLorean.cutSpeedToPercentageOfCurrent(80.0);
    }

    @Override
    public void draw() {
        super.draw(Mirror.NONE);
    }
}