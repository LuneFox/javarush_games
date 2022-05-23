package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.HitBox;

public class Hole extends RoadObject {

    public Hole() {
        super(RoadObjectType.HOLE);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 15, 8);
    }

    @Override
    public void onContact(DeLorean deLorean) {
        deLorean.setSpeed((deLorean.getSpeed() / 100) * 80);
    }
}