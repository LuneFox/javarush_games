package com.javarush.games.snake.model;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Sign;

import java.util.Date;

public class Orb extends GameObject {
    public Element element;
    private Color color;
    private Color bgColor1;
    private Color bgColor2;
    private String sign;
    private Date blinkTime;
    private boolean blink;
    public boolean isAlive = true;
    public boolean isObtained = false;

    public Orb(int x, int y, Element e) {
        super(x, y);
        this.element = e;
        switch (e) {
            case NEUTRAL:
                color = Color.WHITE;
                bgColor1 = Color.PURPLE;
                bgColor2 = Color.MEDIUMVIOLETRED;
                sign = Sign.getSign(Sign.ORB_NEUTRAL);
                break;
            case WATER:
                color = Color.WHITE;
                bgColor1 = Color.BLUE;
                bgColor2 = Color.DARKBLUE;
                sign = Sign.getSign(Sign.ORB_WATER);
                break;
            case FIRE:
                color = Color.WHITE;
                bgColor1 = Color.RED;
                bgColor2 = Color.ORANGERED;
                sign = Sign.getSign(Sign.ORB_FIRE);
                break;
            case EARTH:
                color = Color.WHITE;
                bgColor1 = Color.BROWN;
                bgColor2 = Color.DARKRED;
                sign = Sign.getSign(Sign.ORB_EARTH);
                break;
            case AIR:
                color = Color.WHITE;
                bgColor1 = Color.LIGHTSKYBLUE;
                bgColor2 = Color.LIGHTBLUE;
                sign = Sign.getSign(Sign.ORB_AIR);
                break;
            case ALMIGHTY:
                color = Color.WHITE;
                bgColor1 = Color.ORCHID;
                bgColor2 = Color.MEDIUMORCHID;
                sign = Sign.getSign(Sign.ORB_ALMIGHTY);
                break;
            default:
                break;
        }
        blinkTime = new Date();
    }

    public void draw(Game game) {
        if (new Date().getTime() - blinkTime.getTime() > 250) {
            blink = !blink;
            blinkTime = new Date();
        }
        game.setCellValueEx(x, y, blink ? bgColor1 : bgColor2, sign, color, 90);
    }

    public boolean hasAffinity(Element element) {
        return this.element == element;
    }
}
