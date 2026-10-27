package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

/** Сфера мудрости: растит змейку, но не даёт стихию. */
public class NeutralOrb extends Orb {
    /** Создаёт сферу мудрости. */
    public NeutralOrb(int x, int y) {
        super(x, y, Element.NEUTRAL, Color.PURPLE, Color.MEDIUMVIOLETRED, Sign.ORB_NEUTRAL);
    }
}
