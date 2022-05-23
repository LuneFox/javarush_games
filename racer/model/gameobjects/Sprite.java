package com.javarush.games.racer.model.gameobjects;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.view.Display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sprite {
    private final Display display;
    private int[][] matrix;
    int width;
    int height;

    private List<int[][]> frames;
    private int currentFrame;
    private int nextFrameDelay;
    private int nextFrameTimer;
    private Loop loop;
    private final SpriteMask mask;

    private boolean flicker;
    private boolean flickerPhase;

    public enum Loop {
        ENABLED, DISABLED
    }

    public Sprite(Display display) {
        this.display = display;
        this.nextFrameDelay = 1;
        this.mask = new SpriteMask();
    }

    void draw(double x, double y, Mirror mirror) {
        nextFrame();
        mask.nextStep(width);

        if (flickers()) return;

        for (int i = 0; i < (width - mask.getWidth()); i++) {
            for (int j = 0; j < height; j++) {
                Color color = Color.values()[matrix[j][i]];
                double drawX = (mirror == Mirror.HORIZONTAL) ? (x + width - 1 - i) : (x + i);
                double drawY = (y + j);
                display.drawPixel((int) drawX, (int) drawY, color);
            }
        }
    }

    private boolean flickers() {
        if (flicker) {
            changeFlickerPhase();
            return flickerPhase;
        }
        return false;
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

    boolean isAnimationFinished() {
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

    public void setAnimationDelay(int nextFrameDelay) {
        this.nextFrameDelay = nextFrameDelay;
    }

    void maskIn(int step) {
        mask.startIn(step);
    }

    void maskOut(int step) {
        mask.startOut(step, width);
    }

    void startFlicker() {
        this.flicker = true;
    }

    void stopFlicker() {
        this.flicker = false;
    }

    private void changeFlickerPhase() {
        flickerPhase = !flickerPhase;
    }
}
