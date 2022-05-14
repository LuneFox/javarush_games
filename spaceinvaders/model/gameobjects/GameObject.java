package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Mirror;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameObject {
    private static SpaceInvadersGame game;
    public double x;
    public double y;
    public int width;
    public int height;
    public int lastCollisionX;
    public int lastCollisionY;
    public int[][] matrix;
    public boolean isAlive;

    private List<int[][]> frames;
    private int currentFrame;
    private boolean loopAnimationEnabled = false;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.isAlive = true;
    }

    /*
     * Graphics
     */

    public void draw() {
        this.draw(Mirror.NONE);
    }

    public void draw(Mirror mirror) {
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

    public void nextFrame() {
        if (animationNotFinished()) {
            this.matrix = frames.get(currentFrame++);
        } else if (loopAnimationEnabled) {
            currentFrame = 0;
            this.matrix = frames.get(currentFrame++);
        }
    }

    public boolean isVisible() {
        return (isAlive || animationNotFinished());
    }

    private boolean animationNotFinished() {
        return currentFrame < frames.size();
    }

    public void setStaticView(int[][] viewFrame) {
        this.setMatrix(viewFrame);
        frames = new ArrayList<>();
        frames.add(viewFrame);
        currentFrame = 0;
    }

    public void setAnimatedView(boolean isLoopAnimation, int[][]... viewFrames) {
        loopAnimationEnabled = isLoopAnimation;
        setMatrix(viewFrames[0]);
        this.frames = Arrays.asList(viewFrames);
        currentFrame = 0;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
    }


    /*
     * Collisions
     */

    public boolean collidesWithAnotherObject(GameObject anotherObject, Mirror mirror) {
        // Check if any other object's pixels collide with this object's pixels, return true at first collision
        for (int x = 0; x < anotherObject.width; x++) {
            for (int y = 0; y < anotherObject.height; y++) {
                if (anotherObject.pixelIsNotTransparent(x, y)) {
                    double mirrorX = (mirror == Mirror.HORIZONTAL) ? (anotherObject.width - x) : x;
                    double checkedX = anotherObject.x + mirrorX;
                    double checkedY = anotherObject.y + y;
                    if (collidesWithPoint(checkedX, checkedY)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean collidesWithPoint(double pointX, double pointY) {
        // Check if a given point overlaps any non-transparent object's pixels
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (pixelIsNotTransparent(x, y)
                        && (int) (x + this.x) == (int) pointX
                        && (int) (y + this.y) == (int) pointY) {
                    memorizeCollisionPoint(x, y);
                    return true;
                }
            }
        }
        return false;
    }

    private void memorizeCollisionPoint(int x, int y) {
        lastCollisionX = x;
        lastCollisionY = y;
    }

    private boolean pixelIsNotTransparent(int x, int y) {
        return matrix[y][x] != 0;
    }

    public static void setGame(SpaceInvadersGame game) {
        GameObject.game = game;
    }
}