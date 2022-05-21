package com.javarush.games.racer.model.decor;

import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.Shapes;

public class Marty extends GameObject {
    public Marty() {
        super(0, 0);
        setStaticView(Shapes.MARTY);
        this.y = RacerGame.HEIGHT - getHeight();
        this.x = RacerGame.WIDTH - getWidth() - 5;
    }
}
