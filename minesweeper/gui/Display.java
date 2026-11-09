package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.Util;
import com.javarush.games.minesweeper.model.InteractiveObject;

/** Виртуальный пиксельный экран для слоёной отрисовки. */
public class Display extends InteractiveObject {
    /** Размер. */
    public static final int SIZE = 100;
    /** Матрица пикселей изображения. */
    private final Pixel[][] matrix;
    /** Включён ли чересстрочный эффект. */
    private boolean interlaceEnabled;
    /** Текущая фаза чересстрочности. */
    private boolean interlacePhase;

    /** Создаёт экземпляр Display. */
    public Display() {
        setPosition(0, 0);
        this.height = SIZE;
        this.width = SIZE;
        this.matrix = createPixelMatrix();
    }

    /** Создаёт матрицу пикселей. */
    private Pixel[][] createPixelMatrix() {
        Pixel[][] result = new Pixel[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result[y][x] = new Pixel();
            }
        }
        return result;
    }

    /** Рисует объект на экране. */
    public void draw() {
        if (interlaceEnabled) {
            drawInterlacedRows();
            switchInterlacePhase();
        } else {
            drawRows(0, 0);
        }
    }

    /** Рисует чересстрочные строки. */
    private void drawInterlacedRows() {
        if (interlacePhase) {
            drawRows(0, 1);
        } else {
            drawRows(1, 1);
        }
    }

    /** Рисует строки. */
    private void drawRows(int startRow, int skip) {
        for (int y = startRow; y < height; y += (skip + 1)) {
            for (int x = 0; x < width; x++) {
                game.setCellColor(x, y, matrix[y][x].cellColor);
            }
        }
    }

    /** Переключает фазу чересстрочности. */
    private void switchInterlacePhase() {
        interlacePhase = !interlacePhase;
    }

    /** Рисует пиксель. */
    public void drawPixel(int x, int y, Color color) {
        if (Util.isWithinScreen(x, y)) {
            this.matrix[y][x].cellColor = color;
        }
    }

    /** Включает или выключает чересстрочный эффект. */
    public void setInterlaceEnabled(boolean interlaceEnabled) {
        this.interlaceEnabled = interlaceEnabled;
    }

    /** Пиксель виртуального экрана. */
    public static class Pixel {
        /** Цвет пикселя. */
        Color cellColor;

        /** Создаёт экземпляр Pixel. */
        Pixel() {
            this.cellColor = Color.NONE;
        }
    }
}
