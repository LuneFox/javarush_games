package com.javarush.games.racer.model.road;

import com.javarush.games.racer.model.GameObject;
import com.javarush.games.racer.view.Shapes;

public class RoadObject extends GameObject {
    public RoadObjectType type;
    public double speed;

    public RoadObject(RoadObjectType type, double x, double y) {
        super(x, y);
        this.type = type;
        this.setStaticView(getShape(type));
    }

    /**
     * Метод, отвечающий за передвижение препятствия. У препятствия может быть своя скорость и дополнительная,
     * которая зависит от скорости движения игрока.
     */
    public void move(double boost) {
        this.x -= boost;
    }

    /**
     * Возвращает матрицу изображения объекта в зависимости от его типа.
     */
    private static int[][] getShape(RoadObjectType type) {
        switch (type) {
            case PUDDLE:
                return Shapes.PUDDLE;
            case HOLE:
                return Shapes.HOLE;
            case ENERGY:
                return Shapes.ENERGY;
            default:
                return Shapes.DELOREAN_RUN_0;
        }
    }

    /**
     * Возвращает высоту объекта.
     */
    public static int getHeight(RoadObjectType type) {
        return getShape(type).length;
    }

    public static int getWidth(RoadObjectType type) {
        return getShape(type)[0].length;
    }

}
