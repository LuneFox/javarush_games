package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Mirror;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Спрайт объекта: один кадр или последовательность кадров. */
public class Sprite {
    /** Режим анимации. */
    public enum Loop {
        /** Повторять анимацию по кругу. */
        REPEAT,

        /** Проиграть анимацию один раз. */
        ONCE
    }

    /** Игра для доступа к экрану. */
    private final SpaceInvadersGame game;

    /** Текущий кадр. */
    private int[][] frame;
    /** Ширина текущего кадра. */
    private int width;
    /** Высота текущего кадра. */
    private int height;

    /** Все кадры анимации. */
    private List<int[][]> frames;
    /** Индекс следующего кадра. */
    private int currentFrame;
    /** Задержка между кадрами в ходах. */
    private int frameDelay;
    /** Счётчик ходов до смены кадра. */
    private int frameTimer;
    /** Режим повторения анимации. */
    private Loop loop;

    /** Создаёт спрайт для игры. */
    public Sprite(SpaceInvadersGame game) {
        this.game = game;
        this.frameDelay = 1;
    }

    /** Рисует текущий кадр со сдвигом и отражением. */
    void draw(double x, double y, Mirror mirror) {
        showNextFrame();

        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                Color color = Color.values()[frame[row][column]];
                double pixelX = (mirror == Mirror.HORIZONTAL) ? (x + width - 1 - column) : (x + column);
                game.getScreen().setPixel((int) pixelX, (int) (y + row), color);
            }
        }
    }

    /** Переключает кадр по таймеру. */
    private void showNextFrame() {
        if (game.isPaused()) return;
        if (frameTimer++ % frameDelay != 0) return;

        if (!isAnimationFinished()) {
            frame = frames.get(currentFrame++);
        } else if (loop == Loop.REPEAT) {
            currentFrame = 0;
            frame = frames.get(currentFrame++);
        }
    }

    /** Сообщает, закончилась ли анимация. */
    boolean isAnimationFinished() {
        return currentFrame >= frames.size();
    }

    /** Делает спрайт неподвижным. */
    void setStaticView(int[][] frame) {
        this.frames = new ArrayList<>();
        this.frames.add(frame);
        this.currentFrame = 0;
        setFrame(frame);
    }

    /** Включает анимацию из кадров. */
    void setAnimatedView(Loop loop, int frameDelay, int[][]... frames) {
        this.loop = loop;
        this.frameDelay = frameDelay;
        this.frames = Arrays.asList(frames);
        this.currentFrame = 0;
        setFrame(frames[frames.length - 1]);
    }

    /** Делает кадр текущим и запоминает его размеры. */
    private void setFrame(int[][] frame) {
        this.frame = frame;
        this.width = frame[0].length;
        this.height = frame.length;
    }

    /** Проверяет, виден ли пиксель кадра. */
    boolean isPixelVisible(int x, int y) {
        return frame[y][x] != 0;
    }

    /** Стирает пиксель кадра. */
    void erasePixel(int x, int y) {
        frame[y][x] = 0;
    }

    /** Возвращает ширину кадра. */
    int getWidth() {
        return width;
    }

    /** Возвращает высоту кадра. */
    int getHeight() {
        return height;
    }
}
