package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class AlmightyOrb extends Orb{
    public AlmightyOrb(int x, int y) {
        super(x, y);
        element = Element.ALMIGHTY;
        color = Color.WHITE;
        backgroundColor1 = Color.ORCHID;
        backgroundColor2 = Color.MEDIUMORCHID;
        sign = Sign.getSign(Sign.ORB_ALMIGHTY);
    }

    @Override
    public void collect(Snake snake) {
        if (snake.headIsNotTouchingOrb(this)) return;

        super.collect(snake);
        snake.clearElements();
        snake.learnElement(element);
        snake.forceSwitchToElement(element);

        game.selectNextStage();
    }
}
