package com.javarush.games.racer.road;

import com.javarush.games.racer.*;

import java.util.List;

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

    public void move(double boost, List<RoadObject> items) {
        this.y -= boost;
    }

    /**
     * Проверяет текущий объект и объект, который пришел в качестве параметра, на пересечение их изображений
     * с учетом дистанции distance.
     * Например, если в качестве distance передать число 12, а 2 объекта расположены друг от друга на расстоянии меньшем,
     * чем 12 ячеек игрового поля, метод вернет true. В ином случае вернет false.
     */
    public boolean isCollisionWithDistance(RoadObject roadObject, int distance) {
        if ((x - distance > roadObject.x + roadObject.width) || (x + width + distance < roadObject.x)) {
            return false;
        }

        if ((y - distance > roadObject.y + roadObject.height) || (y + height + distance < roadObject.y)) {
            return false;
        }

        return true;
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
