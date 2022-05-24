package com.javarush.games.racer.model.road;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.gameobjects.Mirror;
import com.javarush.games.racer.view.Shapes;

public abstract class RoadObject extends GameObject {
    protected double speed;

    public RoadObject(RoadObjectType type) {
        this.setStaticView(getShape(type));
    }

    public static RoadObject create(RoadObjectType type) {
        switch (type) {
            case ENERGY:
                return new Energy();
            case HOLE:
                return new Hole();
            default:
                return new Puddle();
        }
    }

    private static int[][] getShape(RoadObjectType type) {
        switch (type) {
            case ENERGY:
                return Shapes.ENERGY;
            case HOLE:
                return Shapes.HOLE;
            default:
                return Shapes.PUDDLE;
        }
    }

    @Override
    public void draw() {
        super.draw(Mirror.NONE);
    }

    @Override
    public void draw(Mirror mirror) {
        if (this.x + (this.getWidth() / 2.0) > RacerGame.WIDTH) {
            if (game.isStopped()) return;
            startFlicker();
            draw(RacerGame.WIDTH - (this.getWidth() / 2.0), this.y, mirror);
        } else {
            stopFlicker();
            super.draw(mirror);
        }
    }

    public void move(double boost) {
        this.x -= boost;
    }

    public abstract void onContact(DeLorean deLorean);
}
