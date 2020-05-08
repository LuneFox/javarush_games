package com.javarush.games.moonlander;

import com.javarush.engine.cell.Game;

import java.util.ArrayList;

public class Lander extends GameObject {
    private Moon moon;
    private MoonLanderGame game;
    private RocketFire upFire;
    private RocketFire downFire;
    private RocketFire leftFire;
    private RocketFire rightFire;
    private RocketFire mainFire;
    private double speedZ;
    private double speedX;
    private double speedY;
    private double boost;
    private double sideBoost;
    private double slowdownX;
    private double slowdownY;
    private boolean release = false;

    public Lander(MoonLanderGame game, double x, double y, Moon moon) {
        super(x, y, ShapeMatrix.LANDER);
        this.moon = moon;
        this.game = game;
        ArrayList<int[][]> downFireFrames = new ArrayList<>();
        downFireFrames.add(ShapeMatrix.FIRE_DOWN_1);
        downFireFrames.add(ShapeMatrix.FIRE_DOWN_2);
        ArrayList<int[][]> upFireFrames = new ArrayList<>();
        upFireFrames.add(ShapeMatrix.FIRE_UP_1);
        upFireFrames.add(ShapeMatrix.FIRE_UP_2);
        ArrayList<int[][]> rightFireFrames = new ArrayList<>();
        rightFireFrames.add(ShapeMatrix.FIRE_RIGHT_1);
        rightFireFrames.add(ShapeMatrix.FIRE_RIGHT_2);
        ArrayList<int[][]> leftFireFrames = new ArrayList<>();
        leftFireFrames.add(ShapeMatrix.FIRE_LEFT_1);
        leftFireFrames.add(ShapeMatrix.FIRE_LEFT_2);
        ArrayList<int[][]> mainFireFrames = new ArrayList<>();
        mainFireFrames.add(ShapeMatrix.FIRE_MAIN_1);
        mainFireFrames.add(ShapeMatrix.FIRE_MAIN_2);

        downFire = new RocketFire(downFireFrames);
        upFire = new RocketFire(upFireFrames);
        leftFire = new RocketFire(leftFireFrames);
        rightFire = new RocketFire(rightFireFrames);
        mainFire = new RocketFire(mainFireFrames);
    }

    public void move(boolean isSpacePressed,
                     boolean isLeftPressed,
                     boolean isRightPressed,
                     boolean isUpPressed,
                     boolean isDownPressed) {
        switchFire(isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isSpacePressed);
        optimizeFPS();
        activateSideThrottle(isLeftPressed, isRightPressed, isUpPressed, isDownPressed);
        keepMoonInSight();
        activateMainThrottle(isSpacePressed);
    }

    private void keepMoonInSight() {
        if (moon.posX + moon.radius < 0) {
            moon.posX = 1 - moon.radius;
            slowdownX = 0;
            speedX = 0;
        } else if (moon.posX - moon.radius > 63) {
            moon.posX = 63 + moon.radius;
            slowdownX = 0;
            speedX = 0;
        }
        if (moon.posY + moon.radius < 0) {
            moon.posY = 1 - moon.radius;
            slowdownY = 0;
            speedY = 0;
        } else if (moon.posY - moon.radius > 63) {
            moon.posY = 63 + moon.radius;
            slowdownY = 0;
            speedY = 0;
        }
    }

    private void limitMoonDistance() {
        if (moon.radius < moon.MIN_RADIUS || moon.radius > moon.MAX_RADIUS) {
            speedZ = 0.0;
            if (moon.radius < moon.MIN_RADIUS) {
                moon.radius = moon.MIN_RADIUS;
            } else if (moon.radius > moon.MAX_RADIUS) {
                moon.radius = moon.MAX_RADIUS;
            }
        }
    }

    private void activateMainThrottle(boolean isSpacePressed) {
        if (isSpacePressed) {
            speedZ -= boost;
        } else {
            speedZ += boost;
        }
        moon.radius += speedZ;
        limitMoonDistance();
    }

    private void activateSideThrottle(boolean isLeftPressed,
                                      boolean isRightPressed,
                                      boolean isUpPressed,
                                      boolean isDownPressed) {
        if (isLeftPressed) {
            speedX += sideBoost;
            moon.posX += speedX;
        } else if (isRightPressed) {
            speedX -= sideBoost;
            moon.posX += speedX;
        } else if (speedX > slowdownX) {
            speedX += slowdownX;
        } else if (speedX < -slowdownX) {
            speedX -= slowdownX;
        } else {
            speedX = 0;
        }
        moon.posX += speedX;
        game.stars.x += speedX / 8;
        game.bigStars.x += speedX / 6;
        game.earth.x += speedX / 4;
        x -= speedX / 12;


        if (isUpPressed) {
            speedY += sideBoost;
            moon.posY += speedY;
        } else if (isDownPressed) {
            speedY -= sideBoost;
            moon.posY += speedY;
        } else if (speedY > slowdownY) {
            speedY += slowdownY;
        } else if (speedY < -slowdownY) {
            speedY -= slowdownY;
        } else {
            speedY = 0;
        }
        moon.posY += speedY;
        game.stars.y += speedY / 10;
        game.bigStars.y += speedY / 6;
        game.earth.y += speedY / 4;
        y -= speedY / 12;
    }

    private void switchFire(boolean isUpPressed,
                            boolean isDownPressed,
                            boolean isLeftPressed,
                            boolean isRightPressed,
                            boolean isSpacePressed) {
        if (isDownPressed) {
            upFire.x = matrix[0].length / 2 + x - 1;
            upFire.y = y - ShapeMatrix.FIRE_DOWN_1.length;
            upFire.show();
        } else {
            upFire.hide();
        }
        if (isUpPressed) {
            downFire.x = matrix[0].length / 2 + x - 1;
            downFire.y = matrix.length + y;
            downFire.show();
        } else {
            downFire.hide();
        }
        if (isLeftPressed) {
            leftFire.x = matrix[0].length + x;
            leftFire.y = matrix.length / 2 - 1 + y;
            leftFire.show();
        } else {
            leftFire.hide();
        }
        if (isRightPressed) {
            rightFire.x = x - ShapeMatrix.FIRE_RIGHT_1[0].length;
            rightFire.y = matrix.length / 2 - 1 + y;
            rightFire.show();
        } else {
            rightFire.hide();
        }
        if (isSpacePressed) {
            mainFire.x = x;
            mainFire.y = y;
            mainFire.show();
        } else {
            mainFire.hide();
        }
    }

    @Override
    public void draw(Game game) {
        super.draw(game);
        upFire.draw(game);
        downFire.draw(game);
        leftFire.draw(game);
        rightFire.draw(game);
        mainFire.draw(game);
    }


    // TOOLS

    public void optimizeFPS() {
        if (moon.radius < 24.0) {
            game.setTurnTimer(50);
            boost = 0.02;
            sideBoost = 0.02;
            slowdownX = -boost * 0.3;
            slowdownY = -boost * 0.3;
        } else if (moon.radius > 24.0) {
            game.setTurnTimer(100);
            boost = 0.04;
            sideBoost = 0.04;
            slowdownX = -boost * 0.3;
            slowdownY = -boost * 0.3;
        } else if (moon.radius > 36.0) {
            game.setTurnTimer(200);
            boost = 0.08;
            sideBoost = 0.08;
            slowdownX = -boost * 0.3;
            slowdownY = -boost * 0.3;
        }
        if (!release) {
            boost = 0.00;
        }
    }

    public void startLanding() {
        release = true;
    }
}
