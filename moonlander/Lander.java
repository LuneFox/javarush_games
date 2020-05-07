package com.javarush.games.moonlander;

public class Lander extends GameObject {
    private Moon moon;
    private MoonLanderGame game;
    private double speedZ = 0;
    private double speedX = 0;
    private double speedY = 0;
    private double boost = 0.01;
    private double slowdownX = boost * 0.95;
    private double slowdownY = boost * 0.95;

    public Lander(MoonLanderGame game, double x, double y, Moon moon) {
        super(x, y, ShapeMatrix.LANDER);
        this.moon = moon;
        this.game = game;
    }

    public void move(boolean isSpacePressed,
                     boolean isLeftPressed,
                     boolean isRightPressed,
                     boolean isUpPressed,
                     boolean isDownPressed) {
        activateSideThrottle(isLeftPressed, isRightPressed, isUpPressed, isDownPressed);
        keepMoonInSight();
        activateMainThrottle(isSpacePressed);
    }

    private void keepMoonInSight() {
        if (moon.posX < 0) {
            moon.posX = 0;
            slowdownX = 0;
            speedX = 0;
        } else if (moon.posX > 63) {
            moon.posX = 63;
            slowdownX = 0;
            speedX = 0;
        }
        if (moon.posY < 0) {
            moon.posY = 0;
            slowdownY = 0;
            speedY = 0;
        } else if (moon.posY > 63) {
            moon.posY = 63;
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
            speedX += boost;
            moon.posX += speedX;
        } else if (isRightPressed) {
            speedX -= boost;
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
        game.earth.x += speedX / 4;
        x -= speedX / 12;


        if (isUpPressed) {
            speedY += boost;
            moon.posY += speedY;
        } else if (isDownPressed) {
            speedY -= boost;
            moon.posY += speedY;
        } else if (speedY > slowdownY) {
            speedY += slowdownY;
        } else if (speedY < -slowdownY) {
            speedY -= slowdownY;
        } else {
            speedY = 0;
        }
        moon.posY += speedY;
        game.stars.y += speedY / 8;
        game.earth.y += speedY / 4;
        y -= speedY / 12;
    }
}
