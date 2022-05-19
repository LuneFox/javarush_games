package com.javarush.games.racer.model;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.view.ShapeMatrix;

public class Marty extends GameObject {
    public Marty() {
        super(0, 0, ShapeMatrix.MARTY);
        this.y = RacerGame.HEIGHT - height;
        this.x = RacerGame.WIDTH - width - 5;
    }
}
