package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

/** Сфера огня. */
public class FireOrb extends Orb {
    /** Создаёт сферу огня. */
    public FireOrb(int x, int y) {
        super(x, y, Element.FIRE, Color.RED, Color.ORANGERED, Sign.ORB_FIRE);
    }
}
