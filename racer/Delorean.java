package com.javarush.games.racer;

public class Delorean extends GameObject {
    private Direction verticalDirection;
    private Direction horizontalDirection;
    private double speed;
    private double acceleration;

    public Delorean() {
        super(3, RacerGame.HEIGHT / 2 - ShapeMatrix.PLAYER.length /  + 10, ShapeMatrix.PLAYER);
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
                y -= 2;
                break;
            case DOWN:
                y += 2;
                break;
            default:
                break;
        }
    }

    public void gas() {
        switch (horizontalDirection) {
            case RIGHT:
                acceleration = 0.1;
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
                acceleration = 0.05;
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
