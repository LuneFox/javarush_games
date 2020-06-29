package com.javarush.games.spaceinvaders.gameobjects.item;

import com.javarush.games.spaceinvaders.gameobjects.brick.QuestionBrick;
import com.javarush.games.spaceinvaders.shapes.ObjectShape;

public class Star extends Bonus {
    public Star(double x, double y, QuestionBrick brick) {
        super(x, y, brick);
        setStaticView(ObjectShape.STAR);
    }
}
