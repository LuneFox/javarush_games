package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.decor.EnergyPickupIcon;
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
        if (isCollected) return;
        super.draw();
    }

    @Override
    public void onContact(DeLorean deLorean) {
        if (isCollected) return;
        if (deLorean.getEnergy() >= DeLorean.MAX_ENERGY) return;

        deLorean.setEnergy(deLorean.getEnergy() + DeLorean.MAX_ENERGY / 10);
        isCollected = true;

        deLorean.setEnergyPickupIcon(new EnergyPickupIcon(deLorean.x + deLorean.getWidth() / 2.0, deLorean.y));
    }
}
