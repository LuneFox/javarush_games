package com.javarush.games.ticktacktoe.view;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.ticktacktoe.view.printer.SymbolImage;

/**
 * Виртуальный экран для регистрации изображения до вывода результирующего кадра.
 *
 * @author LuneFox
 */
public class Display implements Drawable {
    /** Ширина и высота экрана в пикселях */
    public static final int SIZE = 100;
    /** Игра, к которой привязан экран */
    private final Game game;
    /** Матрица пикселей */
    private final Pixel[][] matrix;

    public Display(Game game) {
        this.game = game;
        this.matrix = createPixelMatrix();
        SymbolImage.setDisplay(this);
    }

    /**
     * Проверка на принадлежность координаты экрану
     *
     * @return координата находится в пределах экрана
     */
    public static boolean isWithinScreen(int x, int y) {
        return (x >= 0) && (y >= 0) && (x < SIZE) && (y < SIZE);
    }

    /**
     * Создание матрицы из пикселей
     * @return двумерный массив пикселей
     */
    private Pixel[][] createPixelMatrix() {
        Pixel[][] result = new Pixel[SIZE][SIZE];
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                result[y][x] = new Pixel();
            }
        }
        return result;
    }

    /**
     * Рисует результирующий кадр в игре
     */
    public void draw() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                game.setCellColor(x, y, matrix[y][x].cellColor);
            }
        }
    }

    /**
     * Рисует пиксель на существующем кадре, если он не прозрачный
     *
     * @param x     x координата пикселя
     * @param y     y координата пикселя
     * @param color цвет пикселя
     */
    public void drawPixel(int x, int y, Color color) {
        if (isWithinScreen(x, y) && !isColorTransparent(color)) {
            this.matrix[y][x].cellColor = color;
        }
    }

    /**
     * Проверка цвета на прозрачность
     *
     * @param color проверяемый цвет
     * @return цвет прозрачный
     */
    private boolean isColorTransparent(Color color) {
        return color == Color.NONE;
    }

    /**
     * Пиксели, из которых составляется матрица
     */
    public static class Pixel {
        /** Цвет пикселя */
        Color cellColor;

        Pixel() {
            // По умолчанию прозрачный
            this.cellColor = Color.NONE;
        }
    }
}
