package com.javarush.games.racer.road;

import com.javarush.games.racer.*;

public class RoadObject extends GameObject {
    public RoadObjectType type;
    public double speed;

    public RoadObject(RoadObjectType type, double x, double y) {
        super(x, y);
        this.type = type;
        this.matrix = getMatrix(type);
        this.width = matrix[0].length;
        this.height = matrix.length;
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
    private static int[][] getMatrix(RoadObjectType type) {
        switch (type) {
            case PUDDLE:
                return ShapeMatrix.PUDDLE;
            case HOLE:
                return ShapeMatrix.HOLE;
            case ENERGY:
                return ShapeMatrix.ENERGY;
            default:
                return ShapeMatrix.DELOREAN_RUN_0;
        }
    }

    /**
     * Возвращает высоту объекта.
     */
    public static int getHeight(RoadObjectType type) {
        return getMatrix(type).length;
    }

    public static int getWidth(RoadObjectType type) {
        return getMatrix(type)[0].length;
    }

}
