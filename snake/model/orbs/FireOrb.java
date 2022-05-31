package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class FireOrb extends Orb {
    public FireOrb(int x, int y) {
        super(x, y);
        element = Element.FIRE;
        color = Color.WHITE;
        backgroundColor1 = Color.RED;
        backgroundColor2 = Color.ORANGERED;
        sign = Sign.getSign(Sign.ORB_FIRE);
    }

    @Override
    public void collect(Snake snake) {
        if (snake.headIsNotTouchingOrb(this)) return;

        super.collect(snake);
        snake.learnElement(element);
        snake.forceRotationToElement(element);
    }
}
