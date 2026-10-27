package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

/** Сфера земли. */
public class EarthOrb extends Orb {
    /** Создаёт сферу земли. */
    public EarthOrb(int x, int y) {
        super(x, y, Element.EARTH, Color.BROWN, Color.DARKRED, Sign.ORB_EARTH);
    }
}
