package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Mirror;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sprite {
    private final SpaceInvadersGame game;
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

    public Sprite(SpaceInvadersGame game) {
        this.game = game;
        this.nextFrameDelay = 1;
    }

    void draw(double x, double y, Mirror mirror) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = Color.values()[matrix[j][i]];
                double drawX = (mirror == Mirror.HORIZONTAL) ? (x + width - 1 - i) : (x + i);
                double drawY = (y + j);
                game.display.drawPixel((int) drawX, (int) drawY, color);
            }
        }
        nextFrame();
    }

    void nextFrame() {
        if (nextFrameTimer++ % nextFrameDelay != 0) return;

        if (animationNotFinished()) {
            this.matrix = frames.get(currentFrame++);
        } else if (loop == Loop.ENABLED) {
            currentFrame = 0;
            this.matrix = frames.get(currentFrame++);
        }
    }

    boolean animationNotFinished() {
        return currentFrame < frames.size();
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
        this.setMatrix(frames[0]);
    }

    void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        this.width = matrix[0].length;
        this.height = matrix.length;
    }

    boolean pixelIsNotTransparent(int x, int y) {
        return matrix[y][x] != 0;
    }

    void erasePixel(int x, int y) {
        matrix[y][x] = 0;
    }
}
