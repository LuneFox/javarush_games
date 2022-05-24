package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.car.EnergyPickupIcon;
import com.javarush.games.racer.model.gameobjects.HitBox;
import com.javarush.games.racer.model.gameobjects.Mirror;

public class Energy extends RoadObject {

    private boolean isCollected;

    Energy() {
        super(RoadObjectType.ENERGY);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 18, 9);
        isCollected = false;
    }

    @Override
    public void draw() {
        if (isCollected) return;
        super.draw(Mirror.NONE);
    }

    @Override
    public void onContact(DeLorean deLorean) {
        if (isCollected) return;
        if (deLorean.hasMaxEnergy()) return;

        isCollected = true;
        deLorean.addEnergy();
        deLorean.setEnergyPickUpIcon(new EnergyPickupIcon(deLorean.x + deLorean.getWidth() / 2.0, deLorean.y));
    }
}
