package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

public class AirOrb extends Orb {
    public AirOrb(int x, int y) {
        super(x, y, Element.AIR, Color.LIGHTSKYBLUE, Color.LIGHTBLUE, Sign.ORB_AIR);
    }
}
