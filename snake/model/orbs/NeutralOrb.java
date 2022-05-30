package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class NeutralOrb extends Orb {
    public NeutralOrb(int x, int y) {
        super(x, y);
        element = Element.NEUTRAL;
        color = Color.WHITE;
        backgroundColor1 = Color.PURPLE;
        backgroundColor2 = Color.MEDIUMVIOLETRED;
        sign = Sign.getSign(Sign.ORB_NEUTRAL);
    }

    @Override
    public void collect(Snake snake) {
        if (snake.headIsNotTouchingOrb(this)) return;

        super.collect(snake);
    }
}
