package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.Shapes;

public class RoadMarking extends GameObject {
    static final double LENGTH = Shapes.ROAD_MARKING[0].length;

    RoadMarking(double x, double y) {
        super(x, y);
        setStaticView(Shapes.ROAD_MARKING);
    }

    boolean isOutsideScreen() {
        return x < -LENGTH;
    }
}
