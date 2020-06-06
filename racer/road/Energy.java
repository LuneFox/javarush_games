package com.javarush.games.racer.road;

import com.javarush.games.racer.GameObject;
import com.javarush.games.racer.HitBox;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.ShapeMatrix;

public class Energy extends RoadObject {

    public boolean isCollected;

    public Energy(double x, double y) {
        super(RoadObjectType.ENERGY, x, y);
        this.speed = 0;
        this.hitBox = new HitBox(0, 0, 9, 18);
        isCollected = false;
    }

    @Override
    public void draw(RacerGame game) {
        if (!isCollected) {
            super.draw(game);
        }
    }
}
