package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

import java.util.ArrayList;

public class Rocket extends GameObject {
    private double speedY = 0;
    private double speedX = 0;
    private double boost = 0.2;
    private double slowdown = boost / 10;
    private RocketFire downFire;
    private RocketFire leftFire;
    private RocketFire rightFire;

    public Rocket(double x, double y) {
        super(x, y, ShapeMatrix.ROCKET);
        ArrayList<int[][]> downFireFrames = new ArrayList<>();
        downFireFrames.add(ShapeMatrix.FIRE_DOWN_1);
        downFireFrames.add(ShapeMatrix.FIRE_DOWN_2);
        downFireFrames.add(ShapeMatrix.FIRE_DOWN_3);
        ArrayList<int[][]> sideFireFrames = new ArrayList<>();
        sideFireFrames.add(ShapeMatrix.FIRE_SIDE_1);
        sideFireFrames.add(ShapeMatrix.FIRE_SIDE_2);
        downFire = new RocketFire(downFireFrames);
        leftFire = new RocketFire(sideFireFrames);
        rightFire = new RocketFire(sideFireFrames);
    }

    public void move(boolean isUpPressed, boolean isLeftPressed, boolean isRightPressed) {
        if (isUpPressed) {
            speedY -= boost;
        } else {
            speedY += boost;
        }
        y += speedY;

        if (isLeftPressed) {
            speedX -= boost;
            x += speedX;
        } else if (isRightPressed) {
            speedX += boost;
            x += speedX;
        } else if (speedX > slowdown) {
            speedX -= slowdown;
        } else if (speedX < -slowdown) {
            speedX += slowdown;
        } else {
            speedX = 0;
        }
        x += speedX;
        checkBorders();
        switchFire(isUpPressed, isLeftPressed, isRightPressed);
    }

    public void land() {
        y -= 1;
    }

    public void crash() {
        this.matrix = ShapeMatrix.ROCKET_CRASH;
    }

    // CHECKS

    private void checkBorders() {
        if (x < 0) {
            x = 0;
            speedX = 0;
        } else if (x + width > MoonLanderGame.WIDTH) {
            x = MoonLanderGame.WIDTH - width;
            speedX = 0;
        }
        if (y <= 0) {
            y = 0;
            speedY = 0;
        }
    }

    public boolean isStopped() {
        return speedY < 10 * boost;
    }

    public boolean isCollision(GameObject object) {
        int transparent = Color.NONE.ordinal();

        for (int matrixX = 0; matrixX < width; matrixX++) {
            for (int matrixY = 0; matrixY < height; matrixY++) {
                int objectX = matrixX + (int) x - (int) object.x;
                int objectY = matrixY + (int) y - (int) object.y;

                if (objectX < 0 || objectX >= object.width || objectY < 0 || objectY >= object.height) {
                    continue;
                }

                if (matrix[matrixY][matrixX] != transparent && object.matrix[objectY][objectX] != transparent) {
                    return true;
                }
            }
        }
        return false;
    }

    // ANIMATIONS

    private void switchFire(boolean isUpPressed, boolean isLeftPressed, boolean isRightPressed) {
        if (isUpPressed) {
            downFire.x = matrix[0].length / 2 + x;
            downFire.y = matrix.length + y;
            downFire.show();
        } else {
            downFire.hide();
        }
        if (isLeftPressed) {
            leftFire.x = matrix[0].length + x;
            leftFire.y = matrix.length + y;
            leftFire.show();
        } else {
            leftFire.hide();
        }
        if (isRightPressed) {
            rightFire.x = x - ShapeMatrix.FIRE_SIDE_1[0].length;
            rightFire.y = matrix.length + y;
            rightFire.show();
        } else {
            rightFire.hide();
        }
    }

    @Override
    public void draw(Game game) {
        super.draw(game);
        downFire.draw(game);
        leftFire.draw(game);
        rightFire.draw(game);
    }
}
