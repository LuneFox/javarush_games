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

        keepMoonInSight();
        activateMainThrottle(isSpacePressed);
    }

    private void keepMoonInSight() {
        if (moon.posX < 0) {
            moon.posX = 0;
            slowdownX = 0;
            speedX = 0;
        } else if (moon.posX > game.WIDTH - 1) {
            moon.posX = game.WIDTH - 1;
            slowdownX = 0;
            speedX = 0;
        }
        if (moon.posY < 0) {
            moon.posY = 0;
            slowdownY = 0;
            speedY = 0;
        } else if (moon.posY > game.HEIGHT - 1) {
            moon.posY = game.HEIGHT - 1;
            slowdownY = 0;
            speedY = 0;
        }
    }

    private void limitMoonDistance() {
        if (moon.radius < 1.0 || moon.radius > 48.0) {
            speedZ = 0.0;
            if (moon.radius < 1.0) {
                moon.radius = 1.0;
            } else if (moon.radius > 48.0) {
                moon.radius = 48.0;
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
        System.out.println(speedZ);
    }
}
