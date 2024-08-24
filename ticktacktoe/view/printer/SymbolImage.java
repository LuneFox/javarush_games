package com.javarush.games.ticktacktoe.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.ticktacktoe.view.Display;

/**
 * Изображение символа
 *
 * @author LuneFox
 */
public class SymbolImage {

    /** Экран, на котором изображение выводится */
    private static Display display;
    /** Набор цветов, которыми печатается символ */
    private static final Color[] colors = new Color[]{Color.NONE, Color.WHITE};
    /** Положение по горизонтали */
    private int x;
    /** Положение по вертикали */
    private int y;
    /** Двумерный массив-изображение */
    private final int[][] matrix;

    /**
     * Назначение экрана для вывода изображения
     *
     * @param display экран игры
     */
    public static void setDisplay(Display display) {
        SymbolImage.display = display;
    }

    SymbolImage(Symbol symbol) {
        this.matrix = symbol.getMatrix();
    }

    /**
     * Отобразить на экране
     *
     * @param x положение по горизонтали
     * @param y положение по вертикали
     */
    void draw(int x, int y) {
        setPosition(x, y);
        draw();
    }

    /**
     * Установить положение на экране (без отрисовки)
     *
     * @param x положение по горизонтали
     * @param y положение по вертикали
     */
    private void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Отобразить на экране
     */
    private void draw() {
        for (int j = 0; j < getHeight(); j++) {
            for (int i = 0; i < getWidth(); i++) {
                int pixel = matrix[j][i];
                if (isPixelTransparent(pixel)) continue;
                int drawX = x + i;
                int drawY = y + j;
                display.drawPixel(drawX, drawY, colors[pixel]);
            }
        }
    }

    /**
     * Получить высоту изображения
     *
     * @return высота символа
     */
    int getHeight(){
        return matrix.length;
    }

    /**
     * Получить ширину изображения
     *
     * @return ширина символа
     */
    int getWidth(){
        return matrix[0].length;
    }

    /**
     * Проверить, прозрачный ли пиксель
     *
     * @param pixel цвет пикселя
     * @return цвет пикселя - прозрачный
     */
    private boolean isPixelTransparent(int pixel) {
        final int TRANSPARENT = 0;
        return (pixel == TRANSPARENT || colors[pixel] == Color.NONE);
    }

    /**
     * Заменить цвет
     *
     * @param color новый цвет
     */
    void changeColor(Color color) {
        try {
            colors[1] = color;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Could not change color! " + e.getMessage());
        }
    }
}
