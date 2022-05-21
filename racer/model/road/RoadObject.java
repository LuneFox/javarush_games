package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.Shapes;

public abstract class RoadObject extends GameObject {
    public RoadObjectType type;
    public double speed;

    public RoadObject(RoadObjectType type) {
        this.type = type;
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

    public void move(double boost) {
        this.x -= boost;
    }
}
