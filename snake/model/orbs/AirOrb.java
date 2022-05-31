package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class AirOrb extends Orb{
    public AirOrb(int x, int y) {
        super(x, y);
        element = Element.AIR;
        color = Color.WHITE;
        backgroundColor1 = Color.LIGHTSKYBLUE;
        backgroundColor2 = Color.LIGHTBLUE;
        sign = Sign.getSign(Sign.ORB_AIR);
    }

    @Override
    public void collect(Snake snake) {
        if (snake.headIsNotTouchingOrb(this)) return;

        super.collect(snake);
        snake.learnElement(element);
        snake.forceRotationToElement(element);
    }
}
