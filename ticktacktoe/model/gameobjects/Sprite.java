package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.engine.cell.Color;
import com.javarush.games.ticktacktoe.TicTacToeGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Спрайт (двумерное изображение) которое можно назначить игровому объекту.
 */
public class Sprite {

    /** Экземпляр игры */
    private final TicTacToeGame game;
    /** Матрица пикселей-цветов */
    private int[][] matrix;
    /** Ширина в пикселях */
    int width;
    /** Высота в пикселях */
    int height;

    /** Список кадров (матриц) для многокадровой анимации */
    private List<int[][]> frames;
    /** Маркер текущего кадра */
    private int currentFrame;
    /** Задержка перед отображением следующего кадра */
    private int nextFrameDelay;
    /** Счётчик времени для переключения на следующий кадр */
    private int nextFrameTimer;
    /** Зациклить анимацию? */
    private Loop loop;

    /**
     * Зацикливание анимации
     */
    public enum Loop {
        ENABLED, DISABLED
    }

    public Sprite(TicTacToeGame game) {
        this.game = game;
        this.nextFrameDelay = 1;
    }

    /**
     * Отрисовка на экране
     *
     * @param x по горизонтали
     * @param y по вертикали
     * @param mirror отражение
     */
    void draw(double x, double y, Mirror mirror) {
        nextFrame();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = Color.values()[matrix[j][i]];
                double drawX = (mirror == Mirror.HORIZONTAL) ? (x + width - 1 - i) : (x + i);
                double drawY = (y + j);
                game.getDisplay().drawPixel((int) drawX, (int) drawY, color);
            }
        }
    }

    /**
     * Переключение кадра на следующий
     */
    void nextFrame() {
        // Кадр меняется каждый раз, когда остаток от деления времени на задержку будет равен нулю
        if (nextFrameTimer++ % nextFrameDelay != 0) return;

        // Пока анимация не закончена, выбирать следующий кадр,
        // а в конце, если зацикливание включено, сбрасывать на начало
        if (!isAnimationFinished()) {
            this.matrix = frames.get(currentFrame++);
        } else if (loop == Loop.ENABLED) {
            currentFrame = 0;
            this.matrix = frames.get(currentFrame++);
        }
    }

    /**
     * Закончилась ли анимация?
     *
     * @return анимация проиграна полностью
     */
    boolean isAnimationFinished(){
        return currentFrame >= frames.size();
    }

    /**
     * Установка статичной картинки
     *
     * @param frame кадр
     */
    void setStaticView(int[][] frame) {
        this.frames = new ArrayList<>();
        this.frames.add(frame);
        this.currentFrame = 0;
        this.setMatrix(frame);
    }

    /**
     * Установка анимации
     *
     * @param loop           зацикливание
     * @param nextFrameDelay задержка перед следующим кадром
     * @param frames         ряд кадров
     */
    void setAnimatedView(Loop loop, int nextFrameDelay, int[][]... frames) {
        this.loop = loop;
        this.nextFrameDelay = nextFrameDelay;
        this.frames = Arrays.asList(frames);
        this.currentFrame = 0;
        int lastFrameIndex = frames.length - 1;
        this.setMatrix(frames[lastFrameIndex]);
    }

    /**
     * Установка матрицы и обновление значение ширины и высоты
     *
     * @param matrix двумерный массив номеров цветов
     */
    void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        this.width = matrix[0].length;
        this.height = matrix.length;
    }
}
