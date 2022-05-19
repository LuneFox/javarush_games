package com.javarush.games.racer.model;

import com.javarush.engine.cell.*;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.Arrays;

public class GameObject {
    public double x;
    public double y;
    public int width;
    public int height;
    public int[][] matrix;
    protected HitBox hitBox;

    private ArrayList<int[][]> frames;
    private int currentFrame;
    private int frameCounter;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        frames = new ArrayList<>();
        currentFrame = 0;
    }

    public GameObject(double x, double y, int[][] matrix) {
        this.x = x;
        this.y = y;
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
        frames = new ArrayList<>();
        currentFrame = 0;
    }

    public void draw(RacerGame game) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int colorIndex = matrix[j][i];
                game.display.setCellColor((int) x + i, (int) y + j, Color.values()[colorIndex]);
            }
        }
    }

    public void animate(RacerGame game, int frameDelay) {
        if (frameCounter < frameDelay) {
            frameCounter++;
        } else {
            if (currentFrame < frames.size() - 1) {
                this.matrix = frames.get(++currentFrame);
            } else {
                currentFrame = 0;
                this.matrix = frames.get(currentFrame);
            }
            frameCounter = 0;
        }
        draw(game);
    }

    public void setAnimation(int[][]... frames) {
        this.frames = new ArrayList<>();
        this.frames.addAll(Arrays.asList(frames));
        frameCounter = this.frames.size() - 1;
        currentFrame = 0;
    }
}