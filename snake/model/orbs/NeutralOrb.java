package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class NeutralOrb extends Orb {
    public NeutralOrb(int x, int y) {
        super(x, y, Element.NEUTRAL, Color.PURPLE, Color.MEDIUMVIOLETRED, Sign.ORB_NEUTRAL);
    }
}
