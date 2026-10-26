package com.javarush.games.racer.view.overlay;

import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.Shapes;

/**
 * Marty McFly's sprite, parked on the lower verge at cell (76, 54) and shown on the ending screen.
 */
public class Marty extends GameObject {
    /**
     * Places the sprite at x = 76, y = 54 and assigns the static {@link Shapes#MARTY} bitmap; it
     * never moves afterwards.
     */
    public Marty() {
        super(76, 54);
        setStaticView(Shapes.MARTY);
    }
}
