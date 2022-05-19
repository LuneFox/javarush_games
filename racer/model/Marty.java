package com.javarush.games.racer.model;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.view.Shapes;

public class Marty extends GameObject {
    public Marty() {
        super(0, 0, Shapes.MARTY);
        this.y = RacerGame.HEIGHT - height;
        this.x = RacerGame.WIDTH - width - 5;
    }
}
