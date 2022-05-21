package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.gameobjects.HitBox;

public class Energy extends RoadObject {

    public boolean isCollected;

    public Energy() {
        super(RoadObjectType.ENERGY);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 18, 9);
        isCollected = false;
    }

    @Override
    public void draw() {
        if (!isCollected) {
            super.draw();
        }
    }
}
