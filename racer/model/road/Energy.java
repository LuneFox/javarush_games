package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.gameobjects.HitBox;

public class Energy extends RoadObject {

    public boolean isCollected;

    public Energy(double x, double y) {
        super(RoadObjectType.ENERGY, x, y);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 9, 18);
        isCollected = false;
    }

    @Override
    public void draw() {
        if (!isCollected) {
            super.draw();
        }
    }
}
