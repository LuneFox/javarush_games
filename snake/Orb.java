package com.javarush.games.snake;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.enums.Element;

import java.util.Date;

class Orb extends GameObject {
    Element element;
    private Color color;
    private Color bgColor1;
    private Color bgColor2;
    private String sign;
    private boolean blink;
    boolean isAlive = true;

    private long timeBefore;
    private long timeAfter;
    private long timeDifference;

    Orb(int x, int y, Element e) {
        super(x, y);
        this.element = e;
        switch (e) {
            case NEUTRAL:
                color = Color.WHITE;
                bgColor1 = Color.PURPLE;
                bgColor2 = Color.MEDIUMVIOLETRED;
                sign = Signs.neutralOrb;
                break;
            case WATER:
                color = Color.WHITE;
                bgColor1 = Color.BLUE;
                bgColor2 = Color.DARKBLUE;
                sign = Signs.waterOrb;
                break;
            case FIRE:
                color = Color.WHITE;
                bgColor1 = Color.RED;
                bgColor2 = Color.ORANGERED;
                sign = Signs.fireOrb;
                break;
            case EARTH:
                color = Color.WHITE;
                bgColor1 = Color.BROWN;
                bgColor2 = Color.DARKRED;
                sign = Signs.earthOrb;
                break;
            case AIR:
                color = Color.WHITE;
                bgColor1 = Color.LIGHTSKYBLUE;
                bgColor2 = Color.LIGHTBLUE;
                sign = Signs.airOrb;
                break;
            case ALMIGHTY:
                color = Color.WHITE;
                bgColor1 = Color.ORCHID;
                bgColor2 = Color.MEDIUMORCHID;
                sign = Signs.almightyOrb;
                break;
            default:
                break;
        }
        timeBefore = new Date().getTime();
    }

    void draw(Game game) {
        if (timeDifference > 500) {
            timeBefore = new Date().getTime();
        }
        timeAfter = new Date().getTime();
        timeDifference = timeAfter - timeBefore;
        blink = (timeDifference < 250);
        game.setCellValueEx(x, y, blink ? bgColor1 : bgColor2, sign, color, 75);
    }
}
