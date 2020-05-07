package com.javarush.games.moonlander;

public class Lander extends GameObject {
    private Moon moon;
    private MoonLanderGame game;
    private double speedZ = 0;
    private double speedX = 0;
    private double speedY = 0;
    private double boost = 0.01;
    private double slowdown = boost / 10;

    public Lander(MoonLanderGame game, double x, double y, Moon moon) {
        super(x, y, ShapeMatrix.LANDER);
        this.moon = moon;
        this.game = game;
    }

    public void move(boolean isSpacePressed) {
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
