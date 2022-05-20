package com.javarush.games.racer.model;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sprite {
    private final RacerGame game;
    private int[][] matrix;
    int width;
    int height;

    private List<int[][]> frames;
    private int currentFrame;
    private int nextFrameDelay;
    private int nextFrameTimer;
    private Loop loop;

    public enum Loop {
        ENABLED, DISABLED
    }

    public Sprite(RacerGame game) {
        this.game = game;
        this.nextFrameDelay = 1;
    }

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

    void nextFrame() {
        if (nextFrameTimer++ % nextFrameDelay != 0) return;

        if (!isAnimationFinished()) {
            this.matrix = frames.get(currentFrame++);
        } else if (loop == Loop.ENABLED) {
            currentFrame = 0;
            this.matrix = frames.get(currentFrame++);
        }
    }

    boolean isAnimationFinished(){
        return currentFrame >= frames.size();
    }

    void setStaticView(int[][] frame) {
        this.frames = new ArrayList<>();
        this.frames.add(frame);
        this.currentFrame = 0;
        this.setMatrix(frame);
    }

    void setAnimatedView(Loop loop, int nextFrameDelay, int[][]... frames) {
        this.loop = loop;
        this.nextFrameDelay = nextFrameDelay;
        this.frames = Arrays.asList(frames);
        this.currentFrame = 0;
        int lastFrameIndex = frames.length - 1;
        this.setMatrix(frames[lastFrameIndex]);
    }

    void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        this.width = matrix[0].length;
        this.height = matrix.length;
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
