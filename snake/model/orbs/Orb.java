package com.javarush.games.snake.model.orbs;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.snake.model.GameObject;
import com.javarush.games.snake.model.enums.Element;

import java.util.Date;

public abstract class Orb extends GameObject {
    protected Element element;
    protected String sign;
    protected Color color;
    protected Color backgroundColor1;
    protected Color backgroundColor2;

    protected Date blinkTimeStamp;
    private boolean blinkState;

    public boolean isAlive = true;
    public boolean isCollected = false;

    public static Orb create(int x, int y, Element element) {
        switch (element) {
            case WATER:
                return new WaterOrb(x, y);
            case FIRE:
                return new FireOrb(x, y);
            case EARTH:
                return new EarthOrb(x, y);
            case AIR:
                return new AirOrb(x, y);
            case ALMIGHTY:
                return new AlmightyOrb(x, y);
            default:
                return new NeutralOrb(x, y);
        }
    }

    public Orb(int x, int y) {
        super(x, y);
        blinkTimeStamp = new Date();
    }

    public void draw(Game game) {
        flipBlinkState();
        Color background = blinkState ? backgroundColor1 : backgroundColor2;
        game.setCellValueEx(x, y, background, sign, color, 90);
    }

    private void flipBlinkState() {
        if (new Date().getTime() - blinkTimeStamp.getTime() <= 250) return;
        blinkState = !blinkState;
        blinkTimeStamp = new Date();
    }

    public Element getElement() {
        return element;
    }
}
