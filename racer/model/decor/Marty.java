package com.javarush.games.racer.model.decor;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.Shapes;

public class Marty extends GameObject {
    public Marty() {
        super(76, 54);
        setStaticView(Shapes.MARTY);
    }
}
