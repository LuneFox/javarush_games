package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class EarthOrb extends Orb {
    public EarthOrb(int x, int y) {
        super(x, y);
        element = Element.EARTH;
        color = Color.WHITE;
        backgroundColor1 = Color.BROWN;
        backgroundColor2 = Color.DARKRED;
        sign = Sign.getSign(Sign.ORB_EARTH);
    }
}
