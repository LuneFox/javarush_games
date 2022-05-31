package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class WaterOrb extends Orb{
    public WaterOrb(int x, int y) {
        super(x, y);
        element = Element.WATER;
        color = Color.WHITE;
        backgroundColor1 = Color.BLUE;
        backgroundColor2 = Color.DARKBLUE;
        sign = Sign.getSign(Sign.ORB_WATER);
    }

    @Override
    public void collect(Snake snake) {
        if (snake.headIsNotTouchingOrb(this)) return;

        super.collect(snake);
        snake.learnElement(element);
        snake.forceRotationToElement(element);
    }
}
