package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class AlmightyOrb extends Orb {
    public AlmightyOrb(int x, int y) {
        super(x, y, Element.ALMIGHTY, Color.ORCHID, Color.MEDIUMORCHID, Sign.ORB_ALMIGHTY);
    }

    @Override
    protected void applyEffect(Snake snake) {
        snake.clearElements();
        super.applyEffect(snake);
    }
}
