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
        if (moon.posX < 0) {
            moon.posX = 0;
            slowdownX = 0;
        } else if (moon.posX > game.WIDTH - 1) {
            moon.posX = game.WIDTH - 1;
            slowdownX = 0;
        }


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
        if (moon.posY < 0) {
            moon.posY = 0;
            slowdownY = 0;
        } else if (moon.posY > game.HEIGHT - 1) {
            moon.posY = game.HEIGHT - 1;
            slowdownY = 0;
        }

        if (moon.radius >= 1 && moon.radius <= 48) {
            if (isSpacePressed) {
                if (speedZ > -0.5) {
                    speedZ -= boost;
                }
            } else {
                if (speedZ < 0.5) {
                    speedZ += boost;
                }
            }

            moon.radius += speedZ;

            if (moon.radius < 1) {
                moon.radius = 1;
                speedZ = 0;
            } else if (moon.radius > 48) {
                moon.radius = 48;
                speedZ = 0;
            }
        }
    }
}
