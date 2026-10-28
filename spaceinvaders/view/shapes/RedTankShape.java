package com.javarush.games.spaceinvaders.view.shapes;

/** Красные кадры обычного танка. */
public class RedTankShape {
    /** Первый кадр движения красного танка. */
    public static final int[][] TANK_1 = recolorShape(TankShape.TANK_1);
    /** Второй кадр движения красного танка. */
    public static final int[][] TANK_2 = recolorShape(TankShape.TANK_2);
    /** Первый кадр взрыва красного танка. */
    public static final int[][] TANK_KILL_1 = recolorShape(TankShape.TANK_KILL_1);
    /** Второй кадр взрыва красного танка. */
    public static final int[][] TANK_KILL_2 = recolorShape(TankShape.TANK_KILL_2);
    /** Третий кадр взрыва красного танка. */
    public static final int[][] TANK_KILL_3 = recolorShape(TankShape.TANK_KILL_3);

    /** Запрещает создание экземпляров. */
    private RedTankShape() {
    }

    /** Перекрашивает зелёные пиксели танка в красные. */
    private static int[][] recolorShape(int[][] source) {
        int[][] result = new int[source.length][source[0].length];
        for (int row = 0; row < source.length; row++) {
            for (int column = 0; column < source[row].length; column++) {
                result[row][column] = recolorColor(source[row][column]);
            }
        }
        return result;
    }

    /** Возвращает красный аналог цвета танка. */
    private static int recolorColor(int color) {
        switch (color) {
            case 33:
                return 40; // DARKGREEN -> DARKRED
            case 6:
                return 5; // GREEN -> RED
            case 97:
                return 27; // MEDIUMSEAGREEN -> CRIMSON
            case 71:
                return 111; // LAWNGREEN -> ORANGERED
            case 68:
                return 4; // KHAKI -> ORANGE
            default:
                return color;
        }
    }
}
