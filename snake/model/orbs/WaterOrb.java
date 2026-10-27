package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

/** Сфера воды. */
public class WaterOrb extends Orb {
    /** Создаёт сферу воды. */
    public WaterOrb(int x, int y) {
        super(x, y, Element.WATER, Color.BLUE, Color.DARKBLUE, Sign.ORB_WATER);
    }
}
