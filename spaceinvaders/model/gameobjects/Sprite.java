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
    private boolean loopAnimationEnabled = false;

    public Sprite(SpaceInvadersGame game) {
        this.game = game;
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
        if (animationNotFinished()) {
            this.matrix = frames.get(currentFrame++);
        } else if (loopAnimationEnabled) {
            currentFrame = 0;
            this.matrix = frames.get(currentFrame++);
        }
    }

    boolean animationNotFinished() {
        return currentFrame < frames.size();
    }

    void setStaticView(int[][] frame) {
        this.setMatrix(frame);
        frames = new ArrayList<>();
        frames.add(frame);
        currentFrame = 0;
    }

    void setAnimatedView(boolean loop, int[][]... frames) {
        this.loopAnimationEnabled = loop;
        setMatrix(frames[0]);
        this.frames = Arrays.asList(frames);
        currentFrame = 0;
    }

    void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
    }

    boolean pixelIsNotTransparent(int x, int y) {
        return matrix[y][x] != 0;
    }

    void erasePixel(int x, int y) {
        matrix[y][x] = 0;
    }
}
