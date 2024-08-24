package com.javarush.games.ticktacktoe.model.gameobjects.field;

import com.javarush.games.ticktacktoe.model.gameobjects.GameObject;

import java.util.Arrays;

/**
 * Стол, который рисуется позади игровой доски
 *
 * @author LuneFox
 */
public class Table extends GameObject {

    Table() {
        setStaticView(makeTableMatrix());
    }

    /**
     * Заполнить массив цветов для стола
     *
     * @return массив цветов для стола
     */
    private static int[][] makeTableMatrix() {
        int color = 45;
        int[][] result = new int[100][100];
        int[] row = new int[100];
        Arrays.fill(row, color);
        Arrays.fill(result, row);
        return result;
    }
}
