package com.javarush.games.racer;

import com.javarush.games.racer.road.RoadManager;

public class Delorean extends GameObject {
    private Direction verticalDirection;
    private Direction horizontalDirection;
    private double speed;
    private double acceleration;

    public Delorean() {
        super(3, RacerGame.HEIGHT / 2 - ShapeMatrix.DELOREAN_DEFAULT.length / 2 + 8, ShapeMatrix.DELOREAN_DEFAULT);
        this.verticalDirection = Direction.NONE;
        this.horizontalDirection = Direction.NONE;
        this.speed = 0.0;
        this.acceleration = 0.0;
    }

    public void steer() {
        if (y < RoadManager.UPPER_BORDER - 8) {
            y = RoadManager.UPPER_BORDER - 8;
        } else if (y > RoadManager.LOWER_BORDER - height) {
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
}
