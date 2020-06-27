package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship extends GameObject {
    public boolean isAlive = true;

    private List<int[][]> frames;
    private int frameIndex;

    public Ship(double x, double y) {
        super(x, y);
    }

    public void setStaticView(int[][] viewFrame) {
        this.setMatrix(viewFrame);
        frames = new ArrayList<>();
        frames.add(viewFrame);
        frameIndex = 0;
    }

    public void setAnimatedView(int[][]... viewFrames) {
        setMatrix(viewFrames[0]);
        this.frames = Arrays.asList(viewFrames);
        frameIndex = 0;
    }

    public void nextFrame() {
        if (frameIndex < frames.size() - 1){
            frameIndex++;
            this.matrix = frames.get(frameIndex);
        }
    }

    @Override
    public void draw(Game game) {
        super.draw(game);
        nextFrame();
    }

    public Bullet fire() {
        return null;
    }

    public void kill() {
        isAlive = false;
    }
}
