package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Mirror;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A picture of a game object: a single frame or a frame sequence, drawn pixel by pixel.
 */
public class Sprite {
    public enum Loop {
        REPEAT, ONCE
    }

    private final SpaceInvadersGame game;

    private int[][] frame;
    private int width;
    private int height;

    private List<int[][]> frames;
    private int currentFrame;
    private int frameDelay;
    private int frameTimer;
    private Loop loop;

    public Sprite(SpaceInvadersGame game) {
        this.game = game;
        this.frameDelay = 1;
    }

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

    boolean isAnimationFinished() {
        return currentFrame >= frames.size();
    }

    void setStaticView(int[][] frame) {
        this.frames = new ArrayList<>();
        this.frames.add(frame);
        this.currentFrame = 0;
        setFrame(frame);
    }

    void setAnimatedView(Loop loop, int frameDelay, int[][]... frames) {
        this.loop = loop;
        this.frameDelay = frameDelay;
        this.frames = Arrays.asList(frames);
        this.currentFrame = 0;
        setFrame(frames[frames.length - 1]);
    }

    private void setFrame(int[][] frame) {
        this.frame = frame;
        this.width = frame[0].length;
        this.height = frame.length;
    }

    boolean isPixelVisible(int x, int y) {
        return frame[y][x] != 0;
    }

    void erasePixel(int x, int y) {
        frame[y][x] = 0;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }
}
