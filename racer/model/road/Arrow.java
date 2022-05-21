package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.gameobjects.GameObject;

public class Arrow extends GameObject {
    public Arrow(int x, int y, int[][] matrix) {
        super(x, y);
        this.setStaticView(matrix);
    }
}
