package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.GameObject;
import com.javarush.games.racer.view.Shapes;

public class Arrow extends GameObject {
    public Arrow(int x, int y, int[][] matrix) {
        super(x, y, matrix);
    }

    public Arrow(int x, int y) {
        super(x, y, Shapes.YELLOW_ARROW);
    }
}
