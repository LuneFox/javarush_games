package com.javarush.games.snake;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.enums.Element;

class Orb extends GameObject {
    private Color color;
    private Color bgColor;
    private String sign;
    Element element;
    boolean isAlive = true;

    Orb(int x, int y, Element e){
        super(x, y);
        this.element = e;
        switch (e) {
            case NEUTRAL:
                color = Color.WHITE;
                bgColor = Color.PURPLE;
                sign = Signs.neutralOrb;
                break;
            case WATER:
                color = Color.WHITE;
                bgColor = Color.BLUE;
                sign = Signs.waterOrb;
                break;
            case FIRE:
                color = Color.WHITE;
                bgColor = Color.RED;
                sign = Signs.fireOrb;
                break;
            case EARTH:
                color = Color.WHITE;
                bgColor = Color.BROWN;
                sign = Signs.earthOrb;
                break;
            case AIR:
                color = Color.WHITE;
                bgColor = Color.LIGHTSKYBLUE;
                sign = Signs.airOrb;
                break;
            case ALMIGHTY:
                color = Color.WHITE;
                bgColor = Color.ORCHID;
                sign = Signs.almightyOrb;
                break;
            default:
                break;
        }
    }

    void draw(Game game) {
        game.setCellValueEx(x, y, bgColor, sign, color, 75);
    }
}
