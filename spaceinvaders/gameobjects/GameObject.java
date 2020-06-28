package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameObject {
    public double x;
    public double y;
    public int width;
    public int height;
    public int[][] matrix;
    public boolean isAlive = true;

    private List<int[][]> frames;
    private int frameIndex;
    private boolean loopAnimation = false;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // -------- GRAPHICS

    public void draw(SpaceInvadersGame game, boolean reversed) {
        if (!reversed) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int colorIndex = matrix[j][i];
                    game.display.setCellValueEx((int) x + i, (int) y + j, Color.values()[colorIndex], "");
                }
            }
        } else {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int colorIndex = matrix[j][i];
                    game.display.setCellValueEx((int) x + (width - i - 1), (int) y + j, Color.values()[colorIndex], "");
                }
            }
        }
        nextFrame();
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
    }


    public void setStaticView(int[][] viewFrame) {
        this.setMatrix(viewFrame);
        frames = new ArrayList<>();
        frames.add(viewFrame);
        frameIndex = 0;
    }

    public void setAnimatedView(boolean isLoopAnimation, int[][]... viewFrames) {
        loopAnimation = isLoopAnimation;
        setMatrix(viewFrames[0]);
        this.frames = Arrays.asList(viewFrames);
        frameIndex = 0;
    }

    public void nextFrame() {
        frameIndex++;
        if (frameIndex < frames.size()) {
            this.matrix = frames.get(frameIndex);
        } else if (loopAnimation) {
            frameIndex = 0;
            this.matrix = frames.get(frameIndex);
        }
    }

    public boolean isVisible() {
        return (isAlive || frameIndex + 1 < frames.size());
    }


    // -------- COLLISION CHECKS

    public boolean isCollision(GameObject gameObject) {
        for (int gameObjectX = 0; gameObjectX < gameObject.width; gameObjectX++) {
            for (int gameObjectY = 0; gameObjectY < gameObject.height; gameObjectY++) {
                if (gameObject.matrix[gameObjectY][gameObjectX] > 0) {
                    if (isCollision(gameObjectX + gameObject.x, gameObjectY + gameObject.y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCollision(double x, double y) {
        for (int matrixX = 0; matrixX < width; matrixX++) {
            for (int matrixY = 0; matrixY < height; matrixY++) {
                if (matrix[matrixY][matrixX] > 0
                        && matrixX + (int) this.x == (int) x
                        && matrixY + (int) this.y == (int) y) {
                    return true;
                }
            }
        }
        return false;
    }
}