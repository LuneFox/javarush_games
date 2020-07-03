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
    private Date blinkTime;
    private boolean blink;
    boolean isAlive = true;

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
        blinkTime = new Date();
    }

    void draw(Game game) {
        Date now = new Date();
        if (now.getTime() - blinkTime.getTime() > 250) {
            blink = !blink;
            blinkTime = new Date();
        }
        game.setCellValueEx(x, y, blink ? bgColor1 : bgColor2, sign, color, 90);
    }
}
