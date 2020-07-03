package com.javarush.games.racer.road;

import com.javarush.games.racer.GameObject;
import com.javarush.games.racer.ShapeMatrix;

public class Arrow extends GameObject {
    public Arrow(int x, int y, int[][] matrix) {
        super(x, y, matrix);
    }

    public Arrow(int x, int y) {
        super(x, y, ShapeMatrix.YELLOW_ARROW);
    }
}
