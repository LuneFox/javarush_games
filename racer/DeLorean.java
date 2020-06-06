package com.javarush.games.racer;

import com.javarush.games.racer.road.RoadManager;

public class DeLorean extends GameObject {
    private Direction verticalDirection;
    private Direction horizontalDirection;
    private Animation animation;
    private double speed;
    private double acceleration;

    public DeLorean() {
        super(3, (int) (RacerGame.HEIGHT / 2 - ShapeMatrix.DELOREAN_RUN_0.length / 2 + 8), ShapeMatrix.DELOREAN_RUN_0);
        this.hitBox = new HitBox(7, 6, 20, 28);
        this.verticalDirection = Direction.NONE;
        this.horizontalDirection = Direction.NONE;
        this.speed = 0.0;
        this.acceleration = 0.0;
        animateStopped();
    }

    public void steer() {
        if (y < RoadManager.UPPER_BORDER - 8) {
            speed = (speed / 100) * 99.5;
            y = RoadManager.UPPER_BORDER - 8;
        } else if (y > RoadManager.LOWER_BORDER - height) {
            speed = (speed / 100) * 99.5;
            y = RoadManager.LOWER_BORDER - height;
        }
        switch (verticalDirection) {
            case UP:
                if (speed > 2) {
                    y -= 2;
                } else {
                    y -= speed;
                }
                break;
            case DOWN:
                if (speed > 2) {
                    y += 2;
                } else {
                    y += speed;
                }
                break;
            default:
                break;
        }
    }

    public void gas() {

        if (speed <= 0 && animation != Animation.STOPPED) {
            animateStopped();
        } else if (speed > 0 && speed < 6 && animation != Animation.RUNNING) {
            animateRunning();
        } else if (speed >= 6 && animation != Animation.GLOWING) {
            animateGlowing();
        }

        switch (horizontalDirection) {
            case RIGHT:
                acceleration = 0.05 * (1 - speed * 0.1);
                speed += acceleration;
                if (speed > 8.8) {
                    speed = 8.8;
                }
                break;
            case LEFT:
                acceleration = 0.2;
                speed -= acceleration;
                if (speed < 0) {
                    speed = 0;
                }
                break;
            case NONE:
                acceleration = 0.025;
                speed -= acceleration;
                if (speed < 0) {
                    speed = 0;
                }
            default:
                break;
        }
    }


    // ANIMATIONS

    public void animateRunning() {
        setAnimation(
                ShapeMatrix.DELOREAN_RUN_0,
                ShapeMatrix.DELOREAN_RUN_1,
                ShapeMatrix.DELOREAN_RUN_2,
                ShapeMatrix.DELOREAN_RUN_3);
        this.animation = Animation.RUNNING;
    }

    public void animateGlowing() {
        setAnimation(
                ShapeMatrix.DELOREAN_GLOW_0,
                ShapeMatrix.DELOREAN_GLOW_1,
                ShapeMatrix.DELOREAN_GLOW_2,
                ShapeMatrix.DELOREAN_GLOW_3);
        this.animation = Animation.GLOWING;
    }

    public void animateStopped() {
        setAnimation(ShapeMatrix.DELOREAN_RUN_0);
        this.animation = Animation.STOPPED;
    }

    // GETTERS AND SETTERS

    public Direction getVerticalDirection() {
        return verticalDirection;
    }

    public Direction getHorizontalDirection() {
        return horizontalDirection;
    }

    public double getSpeed() {
        return speed;
    }

    public void setVerticalDirection(Direction verticalDirection) {
        this.verticalDirection = verticalDirection;
    }

    public void setHorizontalDirection(Direction horizontalDirection) {
        this.horizontalDirection = horizontalDirection;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private enum Animation {
        STOPPED, RUNNING, GLOWING
    }
}
