package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.*;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

public class Star extends GameObject {
    private static final String STAR_SIGN = "★";

    public Star(double x, double y) {
        super(x, y);
    }

    public void draw(SpaceInvadersGame game) {
        game.display.setCellValueEx((int) x, (int) y, Color.NONE, STAR_SIGN, Color.YELLOW, 100);
    }
}
